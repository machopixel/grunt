package net.goodtwist.dev.grunt.db.mock;

import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.core.ChallengeTransaction;
import net.goodtwist.dev.grunt.core.Transaction;
import net.goodtwist.dev.grunt.db.ITransactionDAO;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by USUARIO on 15/12/2015.
 */
public class TransactionDAOMock implements ITransactionDAO{

    Transaction newTransaction;

    public TransactionDAOMock() {
        newTransaction = new ChallengeTransaction();

        newTransaction.setId(UUID.randomUUID());
        newTransaction.setAmount(1.3f);
        newTransaction.setDate(Instant.now().minus(Duration.ofHours(1)).toEpochMilli());
        newTransaction.setDirection(false);
        newTransaction.setExternalId(UUID.randomUUID().toString());
        newTransaction.setMethod("PAYPAL");
        newTransaction.setUsername("machopixel");
        newTransaction.setCurrency("GBP");
    }



    @Override
    public Optional<Transaction> findById(UUID id) {
        return Optional.of(newTransaction);
    }

    @Override
    public Set<Transaction> findByUsername(String username) {
        Set<Transaction> result = new HashSet<>();

        result.add(newTransaction);

        return result;
    }

    @Override
    public Optional<Transaction> create(Transaction transaction) {
        return Optional.of(newTransaction);
    }
}
