package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;

import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.LinkedList;
import java.util.List;

import net.goodtwist.dev.grunt.core.ServerResponse;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.jackson.views.Views;

@Path("/api/account/sign-up")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountSignUpResource {

	private final IUserAccountDAO userAccountDAO;

	public AccountSignUpResource(IUserAccountDAO userAccountDAO) {
		this.userAccountDAO = userAccountDAO;
	}

	@POST
	@UnitOfWork
	@Timed(name = "sign-up")
	@JsonView(Views.PrivateView.class)
	public ServerResponse signup(@Valid UserAccount userAccount) {
		ServerResponse response = new ServerResponse();
		boolean success = false;
		List<String> errorMessages = new LinkedList<String>();
		
		List<UserAccount> users = this.userAccountDAO.findByEqualUsername(userAccount.getUsername());
		
		if (users.size() == 0){
			UserAccount newUser = this.userAccountDAO.create(userAccount);
			success = true;

			response.setContent(newUser);
		}else{
			success = false;
			errorMessages.add("1");
		}
		
		response.setSuccess(success);
		response.setErrorMessages(errorMessages);
		
		return response;
	}

}