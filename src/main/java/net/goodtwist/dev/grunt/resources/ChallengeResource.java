package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Optional;

import javax.inject.Inject;
import javax.ws.rs.*;
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

@Path("/api/v1/challenge/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChallengeResource {

    @Inject
    private IUserAccountDAO userAccountDAO;
    @Inject
    private IChallengeDAO challengeDAO;
    @Inject
    private ITransactionDAO transactionDAO;


    public ChallengeResource() {
    }

    @POST
    @RegistrationRequired
    @Timed(name = "create-challenge")
    @JsonView(Views.PrivateView.class)
    public Response createChallenge(Challenge challenge) {
        ResponseEntity responseEntity = new ResponseEntity();
        Status status;

        ChallengeService challengeService = new ChallengeService(userAccountDAO);
        Challenge newChallenge = challengeService.createNewChallenge(challenge);
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
    @Path("bycreator/{creator}")
    @Timed(name = "retrieve-challenge")
    @JsonView(Views.PrivateView.class)
    public Response retrieveChallengeByCreator(@PathParam("creator") String creator) {
        ResponseEntity responseEntity = new ResponseEntity();
        Status status;

        Map<UUID, Challenge> challenges = this.challengeDAO.findByCreator(creator);

        responseEntity.setContent(challenges.values().toArray());
        status = Status.OK;

        return Response.status(status).entity(responseEntity).build();
    }

    @GET
    @RegistrationRequired
    @Path("byid/{id}")
    @Timed(name = "retrieve-challenge")
    @JsonView(Views.PrivateView.class)
    public Response retrieveChallengeById(@PathParam("id") String id) {
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
    @Path("byid/{id}/accept/{username}")
    @Timed(name = "join-challenge")
    @JsonView(Views.PrivateView.class)
    public Response joinChallenge(@PathParam("id") String id,
                                  @PathParam("username") String username) {
        ResponseEntity responseEntity = new ResponseEntity();
        Status status;

        TransactionService transactionService = new TransactionService();
        ChallengeService challengeService = new ChallengeService(userAccountDAO);

        Optional<Challenge> challenge = this.challengeDAO.findById(UUID.fromString(id));

        if (challenge.isPresent()) {
            Optional<UserAccount> userAccount = this.userAccountDAO.findByUsername(username);

            if (userAccount.isPresent() && challengeService.isParticipant(challenge.get(), userAccount.get())) {

                Set<Transaction> totalTransactions = this.transactionDAO.findByUsername(username);

                if (challenge.get().getCash() > transactionService.total(totalTransactions)){
                    challengeService.accept(challenge.get(), userAccount.get());
                    this.challengeDAO.updateJoinDates(challenge.get());
                    status = Status.OK;

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























/*







Lovely x Cation
        Gyakuten Majo Saiban: Chijo na Majo ni Sabakarechau The Animation
Honoo no Haramase Motto! Hatsuiku! Karada Sokutei 2 The Animation
        Honoo no Haramase Paidol My Star Gakuen Z The Animation
Gakuen no Ikenie: Nagusami Mono to Kashita Kyonyuu Furyou Shoujo
        Buta no Gotoki Sanzoku ni Torawarete Shojo o Ubawareru Kyonyuu Himekishi & Onna Senshi: Zettai Chinpo Nanka ni Maketari Shinai!! The Animation
        Shabura Rental: Ecchi na Onee-san to no Eroero Rental Obenkyou The Animation
        Netoraserare

        */