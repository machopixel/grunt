package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.core.ResponseEntity;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.jackson.views.Views;
import net.goodtwist.dev.grunt.resources.filters.RegistrationRequired;
import net.goodtwist.dev.grunt.services.UserAccountService;

@Path("/api/v1/account/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserAccountResource {

	private final IUserAccountDAO userAccountDAO;

	public UserAccountResource(IUserAccountDAO userAccountDAO) {
		this.userAccountDAO = userAccountDAO;
	}

	@POST
	@Timed(name = "create-user-account")
	@JsonView(Views.PrivateView.class)
	public Response createUserAccount(UserAccount userAccount) {
		ResponseEntity responseEntity = new ResponseEntity();
		Status status;

		UserAccountService userAccountService = new UserAccountService(userAccountDAO);
		
		if (userAccountService.isNewUserAccountValid(userAccount)){
			Optional<UserAccount> newUserAccount = this.userAccountDAO.create(userAccount);
			if (newUserAccount.isPresent()){
				responseEntity.setContent(newUserAccount);
				status = Status.ACCEPTED;
			}else{
				status = Response.Status.NOT_ACCEPTABLE;
			}
		}else{
			status = Response.Status.NOT_ACCEPTABLE;
		}
		
		return Response.status(status).entity(responseEntity).build();
	}

	@GET
    @RegistrationRequired
	@Path("{username}")
	@Timed(name = "retrieve-user-account")
	@JsonView(Views.PrivateView.class)
	public Response retrieveUserAccount(@PathParam("username") String username) {
		ResponseEntity responseEntity = new ResponseEntity();
		Status status;

		Optional<UserAccount> userAccount = this.userAccountDAO.findByUsername(username);

		if (userAccount.isPresent()){
			responseEntity.setContent(userAccount);
			status = Status.ACCEPTED;
		}else{
			status = Status.NOT_FOUND;
		}

		return Response.status(status).entity(responseEntity).build();
	}


	@GET
    @RegistrationRequired
	@Path("{username}/friends")
	@Timed(name = "retrieve-friends-list")
	@JsonView(Views.PublicView.class)
	public Response getFriendsList(@PathParam("username") String username) {
		ResponseEntity responseEntity = new ResponseEntity();
		Status status;

		Optional<UserAccount> userAccount = this.userAccountDAO.findByUsername(username);

		if (userAccount.isPresent()){
			responseEntity.setContent(userAccount.get().getFriends());
			status = Status.OK;
		} else{
			status = Status.NOT_FOUND;
		}

		return Response.status(status).entity(responseEntity).build();
	}
}