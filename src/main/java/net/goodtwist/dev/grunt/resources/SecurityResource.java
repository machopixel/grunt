package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.io.BaseEncoding;
import net.goodtwist.dev.grunt.core.ResponseEntity;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.external.IEmailService;
import net.goodtwist.dev.grunt.resources.filters.RegistrationRequired;
import net.goodtwist.dev.grunt.services.ErrorService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Path("/api/v1/security/")
public class SecurityResource {

    @Inject private IUserAccountDAO userAccountDAO;
    @Inject private IEmailService emailService;

    @GET
    @Path("/token")
    @Timed(name = "create-access-token")
    public Response createAccessToken(@HeaderParam("Authorization") String authorization) {
        ResponseEntity responseEntity = new ResponseEntity();
        List<String> errorMessages = new LinkedList<>();
        Response.Status status;

        String loginCredentialsDecoded = "";
        String[] loginCredentials;

        try {
            loginCredentialsDecoded = new String(BaseEncoding.base64().decode(authorization.split(" ")[1]), "UTF-8");

            if (!Strings.isNullOrEmpty(loginCredentialsDecoded)) {
                loginCredentials = loginCredentialsDecoded.split(":");

                Optional<UserAccount> userAccount = this.userAccountDAO.findByUsername(loginCredentials[0]);

                if (userAccount.isPresent()) {
                    if (userAccount.get().getPassword().equals(loginCredentials[1])) {
                        status = Response.Status.OK;

                        NewCookie cookie = new NewCookie("Security", userAccount.get().getUsername(), "/", null,
                                NewCookie.DEFAULT_VERSION, null, NewCookie.DEFAULT_MAX_AGE, null, false, false);
                        return Response.status(status).cookie(cookie).build();
                    }
                    status = Response.Status.UNAUTHORIZED;
                    errorMessages.add(ErrorService.INVALID_CREDENTIALS);
                } else {
                    status = Response.Status.UNAUTHORIZED;
                    errorMessages.add(ErrorService.INVALID_CREDENTIALS);
                }
            }else{
                status = Response.Status.UNAUTHORIZED;
                errorMessages.add(ErrorService.PLEASE_COMPLETE_FIELDS);
            }

            if (errorMessages.size() > 0) {
                responseEntity.setErrorMessages(errorMessages);
            }

        }catch(Exception e){
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }

        return Response.status(status).entity(responseEntity).build();
    }

    @PUT
    @Path("/token")
    @RegistrationRequired
    @Timed(name = "refresh-access-token")
    public Response refreshAccessToken(@Context UserAccount requestUserAccount) {
        NewCookie cookie = new NewCookie("Security", requestUserAccount.getUsername(), "/", null,
                NewCookie.DEFAULT_VERSION, null, NewCookie.DEFAULT_MAX_AGE, null, false, false);
        return Response.status(Response.Status.OK).cookie(cookie).build();
    }

    @POST
    @Path("/recover")
    @Timed(name = "refresh-access-token")
    public Response recoverUserAccount(@QueryParam("email") String email) {
        ResponseEntity responseEntity = new ResponseEntity();
        Response.Status status;

        Optional<UserAccount> userAccount = this.userAccountDAO.findByEmail(email);

        UUID uuid = UUID.randomUUID();
        userAccount.get().setConfirmationKey(uuid);

        userAccount = this.userAccountDAO.update(userAccount.get());

        if (userAccount.isPresent()){
            status = emailService.sendEmailRecoveryTemplate(userAccount.get().getEmail(), userAccount.get().getUsername(), userAccount.get().getConfirmationKey());
        }else{
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }


        return Response.status(status).entity(responseEntity).build();
    }
}
