package net.goodtwist.dev.grunt.db.cassandra;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.cassandra.CassandraManager;
import net.goodtwist.dev.grunt.core.Challenge;
import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IChallengeDAO;
import net.goodtwist.dev.grunt.jackson.views.Views;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

/**
 * Created by Diego on 8/9/2015.
 */
public class ChallengeDAOCassandra implements IChallengeDAO{

    private CassandraManager cassandraManager;

    public ChallengeDAOCassandra(CassandraManager newCassandraManager) {
        this.cassandraManager = newCassandraManager;
    }

    @Override
    public Optional<Challenge> findById (Long id){
        Optional result = Optional.absent();
        try{
            BuiltStatement query = select().all()
                                           .from("goodtwist", "challenge")
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
    public List<Challenge> findByAnyParticipant (String creator, String participantA, String participantB){
        return null;
    }

    @Override
    public Optional<Challenge> create(Challenge challenge){
        try{
            BuiltStatement query = QueryBuilder.insertInto("goodtwist", "challenge")
                                               .values(this.getFieldsAsArrayForChallengeTable(),
                                                       this.getValuesAsArrayForChallengeTable(challenge));
            ResultSet resultSet = cassandraManager.executeQuery(query);
            return this.findById(challenge.getId());
        }catch(Exception e){
        }
        return Optional.absent();
    }

    public Challenge handleRow(Row row){
        Challenge challenge = new Challenge();
        challenge.setId(row.getLong("id"));
        challenge.setCreator(row.getString("creator"));
        challenge.setParticipantA(row.getString("participanta"));
        challenge.setParticipantB(row.getString("participantb"));
        challenge.setCharacterA(row.getString("charactera"));
        challenge.setCharacterB(row.getString("characterb"));
        return challenge;
    }

    public Object[] getValuesAsArrayForChallengeTable(Challenge challenge){
        Object[] result = new Object[6];
        result[0] = challenge.getId();
        result[1] = challenge.getCreator();
        result[2] = challenge.getParticipantA();
        result[3] = challenge.getParticipantB();
        result[4] = challenge.getCharacterA();
        result[5] = challenge.getCharacterB();
        return result;
    }

    public String[] getFieldsAsArrayForChallengeTable(){
        String[] result = new String[6];
        result[0] = "id";
        result[1] = "creator";
        result[2] = "participanta";
        result[3] = "participantb";
        result[4] = "charactera";
        result[5] = "characterb";
        return result;
    }
}
