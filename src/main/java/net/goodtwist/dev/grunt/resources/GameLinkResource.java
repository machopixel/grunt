package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.core.Gamelink;
import net.goodtwist.dev.grunt.core.ResponseEntity;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IGamelinkDAO;
import net.goodtwist.dev.grunt.jackson.views.Views;
import net.goodtwist.dev.grunt.resources.filters.RegistrationRequired;
import net.goodtwist.dev.grunt.services.GamelinkService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/api/v1/games/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GamelinkResource {

    @Inject
    private IGamelinkDAO gameLinkDAO;

    @GET
    @RegistrationRequired
    @Timed(name = "get-user-account")
    @JsonView(Views.PrivateView.class)
    public Response getGamelinks(@Context UserAccount requestUserAccount) {
        ResponseEntity responseEntity = new ResponseEntity();
        Response.Status status;

        Set<Gamelink> gamelinks = this.gameLinkDAO.getGamelinks(requestUserAccount.getUsername());

        responseEntity.setContent(gamelinks.toArray());
        status = Response.Status.OK;

        return Response.status(status).entity(responseEntity).build();
    }

    @DELETE
    @RegistrationRequired
    @Timed(name = "get-user-account")
    @JsonView(Views.PrivateView.class)
    public Response deleteGamelink(@Context UserAccount requestUserAccount) {
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
    public Response createGamelink(Gamelink gamelink,
                                   @Context UserAccount requestUserAccount) {
        ResponseEntity responseEntity = new ResponseEntity();
        Response.Status status;

        GamelinkService gamelinkService = new GamelinkService();
        responseEntity.setErrorMessages(gamelinkService.isGamelinkValid(gamelink));

        if (responseEntity.getErrorMessages().size() < 1){
            Optional<Gamelink> finalUserAccount = this.gameLinkDAO.create(gamelink);
            if (finalUserAccount.isPresent()){
                responseEntity.setContent(finalUserAccount);
                status = Response.Status.OK;
            }else{
                status = Response.Status.NOT_ACCEPTABLE;
            }
        }else{
            status = Response.Status.NOT_ACCEPTABLE;
        }

        return Response.status(status).entity(responseEntity).build();
    }
}
