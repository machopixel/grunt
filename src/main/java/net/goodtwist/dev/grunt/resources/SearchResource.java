package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;
import net.goodtwist.dev.grunt.core.ResponseEntity;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.jackson.views.Views;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by USUARIO on 27/12/2015.
 */
@Path("/api/v1/search/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SearchResource {

    @Inject
    private IUserAccountDAO userAccountDAO;

    @GET
    @Timed(name = "search-user-accounts")
    @Path("{username}")
    @JsonView(Views.PublicView.class)
    public Response searchUserAccounts(@PathParam("username") String username) {
        ResponseEntity responseEntity = new ResponseEntity();
        Response.Status status;

        List<UserAccount> userAccounts = userAccountDAO.searchByUsername(username, 5);

        if (userAccounts.size() > 0) {
            responseEntity.setContent(userAccounts);
            status = Response.Status.OK;
        } else{
            status = Response.Status.NOT_FOUND;
        }

        return Response.status(status).entity(responseEntity).build();
    }


}
