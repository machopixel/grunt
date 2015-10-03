package net.goodtwist.dev.grunt.db.cassandra;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.cassandra.CassandraManager;
import net.goodtwist.dev.grunt.core.Transaction;
import net.goodtwist.dev.grunt.db.ITransactionDAO;
import net.goodtwist.dev.grunt.services.TransactionService;

import javax.inject.Inject;
import java.util.*;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

/**
 * Created by Diego on 9/5/2015.
 */
public class TransactionDAOCassandra implements ITransactionDAO {

    @Inject
    private CassandraManager cassandraManager;

    public TransactionDAOCassandra() {
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        Optional result = Optional.absent();
        try{
            BuiltStatement query = select().all()
                    .from("goodtwist", "transaction")
                    .where(eq("id", id));

            ResultSet resultSet = cassandraManager.executeQuery(query);
            List<Row> resultList = resultSet.all();
            if (resultList.size() == 1){
                Row row = resultList.get(0);
                result = Optional.of(handleRow(row));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }

    @Override
    public Set<Transaction> findByUsername(String username) {
        Set<Transaction> result = new HashSet<Transaction>();

        try{
            BuiltStatement query = QueryBuilder.select().all()
                    .from("goodtwist", "transaction")
                    .where(eq("username", username));
            ResultSet resultSet = cassandraManager.executeQuery(query);
            List<Row> resultList = resultSet.all();

            for (Row row:resultList){
                result.add(this.handleRow(row));
            }
        }catch(Exception e){
        }
        return result;
    }

    @Override
    public Optional<Transaction> create(Transaction transaction){
        try{
            BuiltStatement query = QueryBuilder.insertInto("goodtwist", "challenge")
                    .values(this.getFieldsAsArrayForChallengeTable(),
                            this.getValuesAsArrayForChallengeTable(transaction));
            ResultSet resultSet = cassandraManager.executeQuery(query);
            List<Row> resultList = resultSet.all();

            if (resultList.size() == 1) {
                if (resultList.get(0) != null && (resultList.get(0).getBool("[applied]") == true)) {
                    return this.findById(transaction.getId());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return Optional.absent();
    }

    public Transaction handleRow(Row row){
        TransactionService ts = new TransactionService();
        Transaction transaction = ts.factory(row.getString("method"));
        transaction.setId(row.getUUID("id"));
        transaction.setUsername(row.getString("username"));
        transaction.setMethod(row.getString("method"));
        transaction.setDirection(row.getBool("direction"));
        transaction.setExternalId(row.getString("externalid"));
        transaction.setDate(row.getInt("date"));
        return transaction;
    }

    public Object[] getValuesAsArrayForChallengeTable(Transaction transaction){
        Object[] result = new Object[7];
        result[0] = transaction.getId();
        result[1] = transaction.getUsername();
        result[2] = transaction.getMethod();
        result[3] = transaction.isDirection();
        result[4] = transaction.getExternalId();
        result[5] = transaction.getDate();
        result[6] = transaction.getAmount();
        return result;
    }

    public String[] getFieldsAsArrayForChallengeTable(){
        String[] result = new String[7];
        result[0] = "id";
        result[1] = "username";
        result[2] = "method";
        result[3] = "direction";
        result[4] = "externalid";
        result[5] = "date";
        result[6] = "amount";
        return result;
    }
}
