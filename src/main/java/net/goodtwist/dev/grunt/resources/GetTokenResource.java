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
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

import net.goodtwist.dev.grunt.core.ResponseEntity;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.core.UserAuthentication;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.db.IUserAuthenticationDAO;
import net.goodtwist.dev.grunt.jackson.views.Views;

@Path("/api/security/get-token")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GetTokenResource {

	private final IUserAccountDAO userAccountDAO;
	private final IUserAuthenticationDAO userAuthenticationDAO;

	public GetTokenResource(IUserAccountDAO userAccountDAO, IUserAuthenticationDAO userAuthenticationDAO) {
		this.userAccountDAO = userAccountDAO;
		this.userAuthenticationDAO = userAuthenticationDAO;
	}

	@POST
	@UnitOfWork
	@Timed(name = "get-token")
	@JsonView(Views.PrivateView.class)
	public Response signin(@Valid UserAccount userAccount) {
		ResponseEntity responseEntity = new ResponseEntity();
		int	status = 400;
		
		List<UserAccount> users = this.userAccountDAO.findByEqualUsername(userAccount.getUsername());
		
		
		if (users.size() == 1){
			UserAccount user = users.get(0);
			
			if (user.getPassword().equals(userAccount.getPassword())){
				this.userAuthenticationDAO.disableByAccountId(user.getId());
				
				UUID uuid = UUID.randomUUID();
				
				UserAuthentication userAuthentication = new UserAuthentication();
				userAuthentication.setAccount(user);
				userAuthentication.setToken(uuid.toString());
				userAuthentication.setExpired(false);
				this.userAuthenticationDAO.create(userAuthentication);
				user.addUserAuthentications(userAuthentication);
				
				responseEntity.setContent(userAuthentication);
				status = 200;
			}else{
				responseEntity.addErrorMessage("1");
			}
		}else if (users.size() == 0){
			responseEntity.addErrorMessage("2");
		} else{
			responseEntity.addErrorMessage("3");
		}
		
		return Response.status(status).entity(responseEntity).build();
	}

}