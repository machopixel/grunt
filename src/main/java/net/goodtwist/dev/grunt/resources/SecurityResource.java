package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.core.ResponseEntity;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.jackson.views.Views;
import net.goodtwist.dev.grunt.services.UserAccountService;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by Diego on 8/11/2015.
 */
@Path("/api/v1/security/")
public class SecurityResource {

    private final IUserAccountDAO userAccountDAO;

    public SecurityResource(IUserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    @GET
    @Timed(name = "create-user-account")
    public Response createToken(@HeaderParam("Authorization") String authorization) {
        Response.Status status;

        String[] loginCredentials = authorization.split(":");
        Optional<UserAccount> userAccount = this.userAccountDAO.findByUsername(loginCredentials[0]);

        if (userAccount.isPresent()){
            if (userAccount.get().getPassword().equals(loginCredentials[1])){
                status = Response.Status.ACCEPTED;

                return Response.status(status).header("Authorization", "token").build();
            }
            status = Response.Status.UNAUTHORIZED;
        }else{
            status = Response.Status.NOT_FOUND;
        }

        return Response.status(status).build();
    }
}
