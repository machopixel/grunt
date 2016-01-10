package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;
import net.goodtwist.dev.grunt.core.ResponseEntity;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.jackson.views.Views;
import net.goodtwist.dev.grunt.resources.filters.RegistrationRequired;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/v1/games/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameLinkResource {

    @GET
    @RegistrationRequired
    @Timed(name = "get-user-account")
    @JsonView(Views.PrivateView.class)
    public Response getGameLinks(@Context UserAccount requestUserAccount) {
        ResponseEntity responseEntity = new ResponseEntity();
        Response.Status status;

        responseEntity.setContent(requestUserAccount);
        status = Response.Status.OK;

        return Response.status(status).entity(responseEntity).build();
    }

    @DELETE
    @RegistrationRequired
    @Timed(name = "get-user-account")
    @JsonView(Views.PrivateView.class)
    public Response deleteGameLink(@Context UserAccount requestUserAccount) {
        ResponseEntity responseEntity = new ResponseEntity();
        Response.Status status;

        responseEntity.setContent(requestUserAccount);
        status = Response.Status.OK;

        return Response.status(status).entity(responseEntity).build();
    }

    @POST
    @RegistrationRequired
    @Timed(name = "get-user-account")
    @JsonView(Views.PrivateView.class)
    public Response createGameLink(@Context UserAccount requestUserAccount) {
        ResponseEntity responseEntity = new ResponseEntity();
        Response.Status status;

        responseEntity.setContent(requestUserAccount);
        status = Response.Status.OK;

        return Response.status(status).entity(responseEntity).build();
    }
}
