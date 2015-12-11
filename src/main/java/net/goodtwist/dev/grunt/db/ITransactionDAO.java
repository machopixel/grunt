package net.goodtwist.dev.grunt.db;

import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.core.Transaction;

import java.util.Set;
import java.util.UUID;

public interface ITransactionDAO {
    Optional<Transaction> findById(UUID id);
    Set<Transaction> findByUsername(String username);
    Optional<Transaction> create(Transaction transaction);
}
