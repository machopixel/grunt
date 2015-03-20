package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.util.LinkedList;
import java.util.List;

import net.goodtwist.dev.grunt.core.ServerResponse;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.UserAccountDAO;
import net.goodtwist.dev.grunt.jackson.views.Views;

@Path("/api/sign-in")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SignInResource {

	private final UserAccountDAO userAccountDAO;

	public SignInResource(UserAccountDAO userAccountDAO) {
		this.userAccountDAO = userAccountDAO;
	}

	@GET
	@UnitOfWork
	@Timed(name = "get-requests")
	@JsonView(Views.UserProfile.class)
	public ServerResponse signin(@QueryParam("username") String username, @QueryParam("password") String password) {
		ServerResponse response = new ServerResponse();
		boolean success = false;
		List<String> errorMessages = new LinkedList<String>();
		
		List<UserAccount> users = userAccountDAO.findByUsername(username);
		
		if (users.size() == 1){
			UserAccount user = users.get(0);
			if (user.getPassword().equals(password)){
				response.setContent(user);
				success = true;
			}else{
				success = false;
				errorMessages.add("1");
			}
		}else if (users.size() == 0){
			success = false;
			errorMessages.add("2");
		} else{
			success = false;
			errorMessages.add("3");
		}
		
		response.setSuccess(success);
		response.setErrorMessages(errorMessages);
		
		return response;
	}

}