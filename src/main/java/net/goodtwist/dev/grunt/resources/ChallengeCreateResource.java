package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Optional;

import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.LinkedList;
import java.util.List;

import net.goodtwist.dev.grunt.core.Challenge;
import net.goodtwist.dev.grunt.core.ServerResponse;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IChallengeDAO;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.jackson.views.Views;

@Path("/api/challenge/create")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChallengeCreateResource {

	private final IUserAccountDAO userAccountDAO;
	private final IChallengeDAO challengeDAO;

	public ChallengeCreateResource(IUserAccountDAO userAccountDAO, IChallengeDAO challengeDAO) {
		this.userAccountDAO = userAccountDAO;
		this.challengeDAO = challengeDAO;
	}

	@POST
	@UnitOfWork
	@Timed(name = "create-challenge")
	@JsonView(Views.PrivateView.class)
	public ServerResponse createChallenge(@HeaderParam("token") String token, @Valid Challenge challenge) {
		ServerResponse response = new ServerResponse();
		boolean success = false;
		List<String> errorMessages = new LinkedList<String>();
		
		Optional<UserAccount> creator = this.userAccountDAO.findById(challenge.getCreatorId());
		Optional<UserAccount> participantA = this.userAccountDAO.findById(challenge.getParticipantAId());
		Optional<UserAccount> participantB = this.userAccountDAO.findById(challenge.getParticipantBId());
		
		if (creator.isPresent()){
			if (participantA.isPresent() && participantB.isPresent()){
				this.challengeDAO.create(challenge);
			}else{
				success = false;
				errorMessages.add("2");
			}
		}else{
			success = false;
			errorMessages.add("1");
		}
		
		response.setSuccess(success);
		response.setErrorMessages(errorMessages);
		
		return response;
	}

}