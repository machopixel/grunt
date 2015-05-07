package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Optional;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.LinkedList;
import java.util.List;

import net.goodtwist.dev.grunt.core.ServerResponse;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.core.UserAuthentication;
import net.goodtwist.dev.grunt.core.UserFriends;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.db.IUserAuthenticationDAO;
import net.goodtwist.dev.grunt.db.IUserFriendsDAO;
import net.goodtwist.dev.grunt.jackson.views.Views;

@Path("/api/account/friends")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountListFriendsResource {

	private final IUserAccountDAO userAccountDAO;
	private final IUserAuthenticationDAO userAuthenticationDAO;
	private final IUserFriendsDAO userFriendsDAO;

	public AccountListFriendsResource(IUserAccountDAO userAccountDAO, IUserAuthenticationDAO userAuthenticationDAO, IUserFriendsDAO userFriendsDAO) {
		this.userAccountDAO = userAccountDAO;
		this.userAuthenticationDAO = userAuthenticationDAO;
		this.userFriendsDAO = userFriendsDAO;
	}

	@POST
	@UnitOfWork
	@Timed(name = "account-friends-list")
	@JsonView(Views.PublicView.class)
	public ServerResponse listFriends(@HeaderParam("token") String token) {
		ServerResponse response = new ServerResponse();
		boolean success = false;
		List<String> errorMessages = new LinkedList<String>();
		
		List<UserAuthentication> authList = this.userAuthenticationDAO.findByToken(token);
		
		if (authList.size() == 1){
			UserAuthentication auth = authList.get(0);
			List<UserFriends> friends = this.userFriendsDAO.listByAccountId(auth.getAccountId());
			List<UserAccount> friendsAccounts = new LinkedList<UserAccount>();
			
			for (UserFriends u  : friends) {
				Optional<UserAccount> userAccount = this.userAccountDAO.findById(u.getFriendAccountId());
				
				if (userAccount.isPresent()){
					friendsAccounts.add(userAccount.get());
				}
			}
				
			response.setContent(friendsAccounts);
			success = true;
		} else{
			success = false;
			errorMessages.add("3");
		}
		
		response.setSuccess(success);
		response.setErrorMessages(errorMessages);
		
		return response;
	}

}