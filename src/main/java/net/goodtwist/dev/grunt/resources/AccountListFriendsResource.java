package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import net.goodtwist.dev.grunt.core.ResponseEntity;
import net.goodtwist.dev.grunt.core.UserAuthentication;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.db.IUserAuthenticationDAO;
import net.goodtwist.dev.grunt.jackson.views.Views;

@Path("/api/account/friends")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountListFriendsResource {

	private final IUserAuthenticationDAO userAuthenticationDAO;

	public AccountListFriendsResource(IUserAccountDAO userAccountDAO, IUserAuthenticationDAO userAuthenticationDAO) {
		this.userAuthenticationDAO = userAuthenticationDAO;
	}

	@POST
	@UnitOfWork
	@Timed(name = "account-friends-list")
	@JsonView(Views.PublicView.class)
	public Response listFriends(@HeaderParam("Authorization") String token) {
		ResponseEntity responseEntity = new ResponseEntity();
		int status = 400;
		
		List<UserAuthentication> authList = this.userAuthenticationDAO.findByToken(token);
		
		if (authList.size() == 1){
			UserAuthentication auth = authList.get(0);
			status = 200;
			responseEntity.setContent(auth.getAccount().getUserFriends());
		} else{
			responseEntity.addErrorMessage("1");
		}
		
		return Response.status(status).entity(responseEntity).build();
	}

}