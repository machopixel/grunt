package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Optional;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import net.goodtwist.dev.grunt.core.Challenge;
import net.goodtwist.dev.grunt.core.ResponseEntity;
import net.goodtwist.dev.grunt.core.Transaction;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IChallengeDAO;
import net.goodtwist.dev.grunt.db.ITransactionDAO;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;
import net.goodtwist.dev.grunt.jackson.views.Views;
import net.goodtwist.dev.grunt.resources.filters.RegistrationRequired;
import net.goodtwist.dev.grunt.services.ChallengeService;
import net.goodtwist.dev.grunt.services.TransactionService;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Path("/api/v1/challenges/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChallengeResource {

    @Inject
    private IUserAccountDAO userAccountDAO;
    @Inject
    private IChallengeDAO challengeDAO;
    @Inject
    private ITransactionDAO transactionDAO;

    @GET
    @RegistrationRequired
    @Timed(name = "get-challenges")
    @JsonView(Views.PrivateView.class)
    public Response getChallenges(@Context UserAccount requestUserAccount) {
        ResponseEntity responseEntity = new ResponseEntity();
        Status status;

        Map<UUID, Challenge> challenges = this.challengeDAO.findByCreator(requestUserAccount.getUsername());

        responseEntity.setContent(challenges.values().toArray());
        status = Status.OK;

        return Response.status(status).entity(responseEntity).build();
    }

    @POST
    @RegistrationRequired
    @Timed(name = "create-challenge")
    @JsonView(Views.PrivateView.class)
    public Response createChallenge(Challenge challenge,
                                    @Context UserAccount requestUserAccount) {
        ResponseEntity responseEntity = new ResponseEntity();
        Status status;

        ChallengeService challengeService = new ChallengeService(userAccountDAO);
        Challenge newChallenge = challengeService.createNewChallenge(challenge, requestUserAccount);
        responseEntity.setErrorMessages(challengeService.isChallengeValid(newChallenge));

        if (responseEntity.getErrorMessages().size() < 1) {
            Optional<Challenge> finalChallenge = this.challengeDAO.create(newChallenge);
            if (finalChallenge.isPresent()) {
                responseEntity.setContent(finalChallenge);
                status = Status.OK;
            } else {
                status = Response.Status.NOT_ACCEPTABLE;
            }
        } else {
            status = Response.Status.NOT_ACCEPTABLE;
        }

        return Response.status(status).entity(responseEntity).build();
    }

    @GET
    @RegistrationRequired
    @Path("{id}")
    @Timed(name = "retrieve-challenge")
    @JsonView(Views.PublicView.class)
    public Response retrieveChallengeById(@PathParam("id") String id,
                                          @Context UserAccount requestUserAccount) {
        ResponseEntity responseEntity = new ResponseEntity();
        Status status;

        Optional<Challenge> challenge = this.challengeDAO.findById(UUID.fromString(id));

        if (challenge.isPresent()) {
            responseEntity.setContent(challenge);
            status = Status.OK;
        } else {
            status = Response.Status.NOT_ACCEPTABLE;
        }

        return Response.status(status).entity(responseEntity).build();
    }

    @GET
    @RegistrationRequired
    @Path("/accept/{id}")
    @Timed(name = "join-challenge")
    @JsonView(Views.PrivateView.class)
    public Response joinChallenge(@PathParam("id") String id,
                                  @Context UserAccount requestUserAccount) {
        ResponseEntity responseEntity = new ResponseEntity();
        Status status;

        TransactionService transactionService = new TransactionService();
        ChallengeService challengeService = new ChallengeService(userAccountDAO);

        Optional<Challenge> challenge = this.challengeDAO.findById(UUID.fromString(id));

        if (challenge.isPresent()) {

            if (challengeService.isParticipant(challenge.get(), requestUserAccount)) {

                Set<Transaction> totalTransactions = this.transactionDAO.findByUsername(requestUserAccount.getUsername());

                Optional<Challenge> finalChallenge;

                if (challenge.get().getCash() > transactionService.total(totalTransactions)){
                    int acceptResult = challengeService.accept(challenge.get(), requestUserAccount);
                    if ( acceptResult == 0){
                        finalChallenge = this.challengeDAO.updateJoinDateA(challenge.get());
                    }else if (acceptResult == 1) {
                        finalChallenge = this.challengeDAO.updateJoinDateB(challenge.get());
                    }else{
                        finalChallenge = Optional.absent();
                    }

                    if (finalChallenge.isPresent()){
                        status = Status.OK;
                        responseEntity.setContent(finalChallenge.get());
                    }else{
                        status = Status.NOT_ACCEPTABLE;
                    }
                }else{
                    status = Status.NOT_ACCEPTABLE;
                }
            }else{
                status = Status.NOT_ACCEPTABLE;
            }
        } else {
            status = Status.NOT_ACCEPTABLE;
        }

        return Response.status(status).entity(responseEntity).build();
    }
}