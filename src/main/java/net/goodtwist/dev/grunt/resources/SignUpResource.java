package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.util.LinkedList;
import java.util.List;

import net.goodtwist.dev.grunt.core.ServerResponse;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.UserAccountDAO;

@Path("/api/sign-in")
@Produces(MediaType.APPLICATION_JSON)
public class SignUpResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(SignInResource.class);

	private final UserAccountDAO userAccountDAO;

	public SignUpResource(UserAccountDAO userAccountDAO) {
		this.userAccountDAO = userAccountDAO;
	}

	@POST
	@UnitOfWork
	@Timed(name = "get-requests")
	public ServerResponse signup(
			@QueryParam("username") String username,
			@QueryParam("password") String password,
			@QueryParam("email") String email) {
		ServerResponse response = new ServerResponse();
		boolean success = false;
		List<String> errorMessages = new LinkedList<String>();
		
		List<UserAccount> users = userAccountDAO.findByUsername(username);
		
		if (users.size() == 0){
			UserAccount user = new UserAccount();
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail(email);
			
			user = userAccountDAO.create(user);
			success = true;

			response.setContent(user);
		}else{
			success = false;
			errorMessages.add("1");
		}
		
		response.setSuccess(success);
		response.setErrorMessages(errorMessages);
		
		return response;
	}

}