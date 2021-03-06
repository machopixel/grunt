package net.goodtwist.dev.grunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.core.Challenge;
import net.goodtwist.dev.grunt.core.ResponseEntity;
import net.goodtwist.dev.grunt.core.Transaction;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IChallengeDAO;
import net.goodtwist.dev.grunt.db.ITransactionDAO;
import net.goodtwist.dev.grunt.jackson.views.Views;
import net.goodtwist.dev.grunt.resources.filters.RegistrationRequired;
import net.goodtwist.dev.grunt.services.ChallengeService;
import net.goodtwist.dev.grunt.services.TransactionService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Diego on 9/9/2015.
 */
@Path("/api/v1/transactions/")
public class TransactionResource {

    @Inject
    private ITransactionDAO transactionDAO;

    @POST
    @RegistrationRequired
    @Timed(name = "create-transaction")
    @JsonView(Views.PrivateView.class)
    public Response createTransactions(Transaction transaction) {
        ResponseEntity responseEntity = new ResponseEntity();
        Response.Status status;

        TransactionService transactionService = new TransactionService();
        Transaction newTransaction = transactionService.createNewTransaction(transaction);
        responseEntity.setErrorMessages(transactionService.isTransactionValid(newTransaction));

        if (responseEntity.getErrorMessages().size() < 1) {
            Optional<Transaction> finalTransaction = this.transactionDAO.create(newTransaction);
            if (finalTransaction.isPresent()) {
                responseEntity.setContent(finalTransaction);
                status = Response.Status.OK;
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
    @Timed(name = "retrieve-transactions")
    @JsonView(Views.PrivateView.class)
    public Response retrieveTransactions(@Context UserAccount requestUserAccount) {
        ResponseEntity responseEntity = new ResponseEntity();
        Response.Status status;

        Set<Transaction> transactions = this.transactionDAO.findByUsername(requestUserAccount.getUsername());

        responseEntity.setContent(transactions.toArray());
        status = Response.Status.OK;

        return Response.status(status).entity(responseEntity).build();
    }
}
