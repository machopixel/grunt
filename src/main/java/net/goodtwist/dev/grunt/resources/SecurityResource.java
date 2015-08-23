package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.common.io.BaseEncoding;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 * Created by Diego on 8/11/2015.
 */
@Path("/api/v1/security/")
public class SecurityResource {

    @Inject private IUserAccountDAO userAccountDAO;

    public SecurityResource() {
    }

    @GET
    @Path("/token")
    @Timed(name = "create-user-account")
    public Response createToken(@HeaderParam("Authorization") String authorization) {
        Response.Status status;

        String loginCredentialsDecoded = "";
        String[] loginCredentials;

        try {
            loginCredentialsDecoded = new String(BaseEncoding.base64().decode(authorization), "UTF-8");

        }catch(Exception e){
        }

        loginCredentials = loginCredentialsDecoded.split(":");


        Optional<UserAccount> userAccount = this.userAccountDAO.findByUsername(loginCredentials[0]);

        if (userAccount.isPresent()){
            if (userAccount.get().getPassword().equals(loginCredentials[1])){
                status = Response.Status.OK;

                NewCookie cookie = new NewCookie("Security", loginCredentials[0], "/", null,
                                                 NewCookie.DEFAULT_VERSION, null, NewCookie.DEFAULT_MAX_AGE, null, false, false);
                return Response.status(status).cookie(cookie).build();
            }
            status = Response.Status.UNAUTHORIZED;
        }else{
            status = Response.Status.NOT_FOUND;
        }

        return Response.status(status).build();
    }
}
