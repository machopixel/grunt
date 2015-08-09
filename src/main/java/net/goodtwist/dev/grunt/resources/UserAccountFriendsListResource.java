package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.util.List;

import com.google.common.base.Optional;

import net.goodtwist.dev.grunt.core.ResponseEntity;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.jackson.views.Views;

@Path("/api/v1/account/{username}/friends")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserAccountFriendsListResource {

	private final IUserAccountDAO userAccountDAO;

	public UserAccountFriendsListResource(IUserAccountDAO userAccountDAO) {
		this.userAccountDAO = userAccountDAO;
	}

	@GET
	@Timed(name = "retrieve-friends-list")
	@JsonView(Views.PublicView.class)
	public Response getFriendsList(@PathParam("username") String username) {
		ResponseEntity responseEntity = new ResponseEntity();
		Status status;

		Optional<UserAccount> userAccount = this.userAccountDAO.findByUsername(username);

		if (userAccount.isPresent()){
			List<UserAccount> userAccountFriendsList = this.userAccountDAO.findFriendsByUsername(username, 0, -1);
			responseEntity.setContent(userAccountFriendsList);
			status = Status.OK;
		} else{
			status = Status.NOT_FOUND;
		}
		
		return Response.status(status).entity(responseEntity).build();
	}

}