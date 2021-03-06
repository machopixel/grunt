package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.core.ResponseEntity;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IChallengeDAO;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.jackson.views.Views;
import net.goodtwist.dev.grunt.resources.filters.RegistrationRequired;
import net.goodtwist.dev.grunt.services.UserAccountService;

@Path("/api/v1/account/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserAccountResource {

	@Inject
	private IUserAccountDAO userAccountDAO;

	@POST
	@Timed(name = "create-user-account")
	@JsonView(Views.PrivateView.class)
	public Response createUserAccount(UserAccount userAccount) {
		ResponseEntity responseEntity = new ResponseEntity();
		Status status;

		UserAccountService userAccountService = new UserAccountService(userAccountDAO);
		UserAccount newUserAccount = userAccountService.createNewUserAccount(userAccount);
		responseEntity.setErrorMessages(userAccountService.isUserAccountValid(newUserAccount));

		if (responseEntity.getErrorMessages().size() < 1){
			Optional<UserAccount> finalUserAccount = this.userAccountDAO.create(newUserAccount);
			if (finalUserAccount.isPresent()){
				responseEntity.setContent(finalUserAccount);
				status = Status.OK;
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
	@Timed(name = "get-user-account")
	@JsonView(Views.PrivateView.class)
	public Response getUserAccount(@Context UserAccount requestUserAccount) {
		ResponseEntity responseEntity = new ResponseEntity();
		Status status;

		responseEntity.setContent(requestUserAccount);
		status = Status.OK;

		return Response.status(status).entity(responseEntity).build();
	}

	@PUT
	@RegistrationRequired
	@Timed(name = "update-user-account")
	@JsonView(Views.PrivateView.class)
	public Response updateUserAccount(UserAccount userAccount,
									@Context UserAccount requestUserAccount) {
		ResponseEntity responseEntity = new ResponseEntity();
		Status status;

		UserAccountService userAccountService = new UserAccountService(userAccountDAO);

		if (requestUserAccount.getUsername().equals(userAccount.getUsername())){
			responseEntity.setErrorMessages(userAccountService.isUserAccountValid(userAccount));

			if (responseEntity.getErrorMessages().size() < 1) {
				Optional<UserAccount> newUserAccount = this.userAccountDAO.update(userAccount);

				if (newUserAccount.isPresent()){
					status = Status.OK;
				}else{
					status = Status.INTERNAL_SERVER_ERROR;
				}
			}else{
				status = Response.Status.NOT_ACCEPTABLE;
			}
		}else{
			status = Status.UNAUTHORIZED;
		}

		return Response.status(status).entity(responseEntity).build();
	}

	@GET
	@RegistrationRequired
	@Path("{username}")
	@Timed(name = "find-user-account")
	@JsonView(Views.PublicView.class)
	public Response findUserAccount(@PathParam("username") String username,
									@Context UserAccount requestUserAccount) {
		ResponseEntity responseEntity = new ResponseEntity();
		Status status;
		Optional<UserAccount> userAccount;

		if (!requestUserAccount.getUsername().equals(username)) {
			userAccount = this.userAccountDAO.findByUsername(username);
		}else{
			userAccount = Optional.of(requestUserAccount);
		}

		if (userAccount.isPresent()){
			responseEntity.setContent(userAccount);
			status = Status.OK;
		}else{
			status = Status.NOT_FOUND;
		}

		return Response.status(status).entity(responseEntity).build();
	}
}