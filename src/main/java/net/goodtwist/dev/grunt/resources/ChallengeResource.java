package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Optional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import net.goodtwist.dev.grunt.core.Challenge;
import net.goodtwist.dev.grunt.core.ResponseEntity;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IChallengeDAO;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.jackson.views.Views;
import net.goodtwist.dev.grunt.resources.filters.RegistrationRequired;

import java.util.List;

@Path("/api/v1/challenge/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChallengeResource {

	private final IUserAccountDAO userAccountDAO;
	private final IChallengeDAO challengeDAO;

	public ChallengeResource(IUserAccountDAO userAccountDAO, IChallengeDAO challengeDAO) {
		this.userAccountDAO = userAccountDAO;
		this.challengeDAO = challengeDAO;
	}

	@POST
	@RegistrationRequired
	@Timed(name = "create-challenge")
	@JsonView(Views.PrivateView.class)
	public Response createChallenge(Challenge challenge) {
		ResponseEntity responseEntity = new ResponseEntity();
		Status status;

		Optional<UserAccount> creator = this.userAccountDAO.findByUsername(challenge.getCreator());

		Optional<UserAccount> participantA;
		if (challenge.getCreator().equals(challenge.getParticipantA())){
			participantA = creator;
		}else{
			participantA = this.userAccountDAO.findByUsername(challenge.getParticipantA());
		}

		Optional<UserAccount> participantB;
		if (challenge.getCreator().equals(challenge.getParticipantB())){
			participantB = creator;
		}else{
			participantB = this.userAccountDAO.findByUsername(challenge.getParticipantB());
		}
		
		if (creator.isPresent()){
			if (participantA.isPresent() && participantB.isPresent()){
				Optional<Challenge> newChallenge = this.challengeDAO.create(challenge);
				if (newChallenge.isPresent()){
					responseEntity.setContent(newChallenge);
					status = Status.OK;
				}else{
					status = Response.Status.NOT_ACCEPTABLE;
				}
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
	@Timed(name = "retrieve-challenge")
	@JsonView(Views.PrivateView.class)
	public Response retrieveChallenge(@QueryParam("creator") String creator,
									  @QueryParam("participantA") String participantA,
									  @QueryParam("participantB") String participantB,
									  @QueryParam("characterA") String characterA,
									  @QueryParam("characterB") String characterB) {
		ResponseEntity responseEntity = new ResponseEntity();
		Status status;

		List<Challenge> challenges = this.challengeDAO.findByAnyParticipant(creator, participantA, participantB);

		responseEntity.setContent(challenges);
		status = Status.OK;

		return Response.status(status).entity(responseEntity).build();
	}

	@GET
	@RegistrationRequired
	@Path("{id}")
	@Timed(name = "retrieve-challenge")
	@JsonView(Views.PrivateView.class)
	public Response retrieveChallenge(@PathParam("id") long id) {
		ResponseEntity responseEntity = new ResponseEntity();
		Status status;

        Optional<Challenge> challenge = this.challengeDAO.findById(id);

		if (challenge.isPresent()){
			responseEntity.setContent(challenge);
			status = Status.OK;
		}else{
			status = Response.Status.NOT_ACCEPTABLE;
		}

		return Response.status(status).entity(responseEntity).build();
	}
}