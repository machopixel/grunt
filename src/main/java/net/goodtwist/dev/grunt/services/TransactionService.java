package net.goodtwist.dev.grunt.services;

import net.goodtwist.dev.grunt.core.ChallengeTransaction;
import net.goodtwist.dev.grunt.core.PayPalTransaction;
import net.goodtwist.dev.grunt.core.Transaction;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


/**
 * Created by Diego on 9/5/2015.
 */
public class TransactionService {
    public TransactionService(){

    }

    public Transaction createNewTransaction(Transaction transaction){
        transaction.setId(UUID.randomUUID());

        return transaction;
    }

    public Transaction factory(String method){
        switch(method){
            case PayPalTransaction.name:
                return this.createNewTransaction(new PayPalTransaction());
            case ChallengeTransaction.name:
                return this.createNewTransaction(new PayPalTransaction());
            default:
                return null;
        }
    }

    public List<String> isTransactionValid(Transaction transaction) {
        List<String> result = new LinkedList<String>();
        return result;
    }

    public float total(Set<Transaction> transactionMap){
        float result = 0;
        for (Transaction t: transactionMap){
            if (t.isDirection()){
                result = result + t.getAmount();
            }else{
                result = result - t.getAmount();
            }
        }

        return result;
    }
}
