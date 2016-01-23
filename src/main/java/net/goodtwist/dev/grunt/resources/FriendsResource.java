package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.core.ResponseEntity;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.jackson.views.Views;
import net.goodtwist.dev.grunt.resources.filters.RegistrationRequired;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/api/v1/friends/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FriendsResource {

    @Inject
    private IUserAccountDAO userAccountDAO;

    @POST
    @RegistrationRequired
    @Timed(name = "add-friend")
    @Path("{username}")
    @JsonView(Views.PublicView.class)
    public Response addFriend(@PathParam("username") String username,
                              @Context UserAccount requestUserAccount) {
        ResponseEntity responseEntity = new ResponseEntity();
        Response.Status status;
        Optional<UserAccount> friendUserAccount;
        Optional<UserAccount> resultUserAccount = Optional.absent();

        if (!requestUserAccount.getUsername().equals(username)) {
            friendUserAccount = this.userAccountDAO.findByUsername(username);
            if (friendUserAccount.isPresent()) {
                requestUserAccount.addFriend(friendUserAccount.get().getUsername());
                resultUserAccount = this.userAccountDAO.updateFriends(requestUserAccount);
            }
        }

        if (resultUserAccount.isPresent()){
            responseEntity.setContent(resultUserAccount.get().getFriends());
            status = Response.Status.OK;
        } else{
            status = Response.Status.NOT_FOUND;
        }

        return Response.status(status).entity(responseEntity).build();
    }

    @DELETE
    @RegistrationRequired
    @Timed(name = "delete-friend")
    @Path("{username}")
    @JsonView(Views.PublicView.class)
    public Response deleteFriend(@PathParam("username") String username,
                                 @Context UserAccount requestUserAccount) {
        ResponseEntity responseEntity = new ResponseEntity();
        Response.Status status;
        Optional<UserAccount> friendUserAccount;
        Optional<UserAccount> resultUserAccount = Optional.absent();

        if (!requestUserAccount.getUsername().equals(username)) {
            friendUserAccount = this.userAccountDAO.findByUsername(username);
            if (friendUserAccount.isPresent()) {
                requestUserAccount.deleteFriend(friendUserAccount.get().getUsername());
                resultUserAccount = this.userAccountDAO.updateFriends(requestUserAccount);
            }
        }

        if (resultUserAccount.isPresent()){
            responseEntity.setContent(resultUserAccount.get().getFriends());
            status = Response.Status.OK;
        } else{
            status = Response.Status.NOT_FOUND;
        }

        return Response.status(status).entity(responseEntity).build();
    }

    @GET
    @RegistrationRequired
    @Timed(name = "get-friends")
    @JsonView(Views.PrivateView.class)
    public Response getFriends(@Context UserAccount requestUserAccount) {
        ResponseEntity responseEntity = new ResponseEntity();
        Response.Status status;

        List<UserAccount> friends = this.userAccountDAO.getFriends(requestUserAccount.getFriends());

        responseEntity.setContent(friends.toArray());
        status = Response.Status.OK;

        return Response.status(status).entity(responseEntity).build();
    }
}
