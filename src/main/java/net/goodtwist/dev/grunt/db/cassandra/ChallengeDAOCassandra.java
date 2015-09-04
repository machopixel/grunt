package net.goodtwist.dev.grunt.db.cassandra;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.cassandra.CassandraManager;
import net.goodtwist.dev.grunt.core.Challenge;
import net.goodtwist.dev.grunt.db.IChallengeDAO;

import javax.inject.Inject;
import java.util.*;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

/**
 * Created by Diego on 8/9/2015.
 */
public class ChallengeDAOCassandra implements IChallengeDAO{

    @Inject private CassandraManager cassandraManager;

    public ChallengeDAOCassandra() {
    }

    @Override
    public Optional<Challenge> findById (UUID id){
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
    public Map<UUID, Challenge> findByCreator (String creator){
        Map<UUID, Challenge> result = new HashMap<UUID, Challenge>();

        try{
            BuiltStatement query = QueryBuilder.select().all()
                                               .from("goodtwist", "challenge")
                                               .where(eq("creator", creator));
            ResultSet resultSet = cassandraManager.executeQuery(query);
            List<Row> resultList = resultSet.all();

            for (Row row:resultList){
                result.put(row.getUUID("id"), this.handleRow(row));
            }
        }catch(Exception e){
        }
        return result;
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
            e.printStackTrace();
        }
        return Optional.absent();
    }

    public Challenge handleRow(Row row){
        Challenge challenge = new Challenge();
        challenge.setId(row.getUUID("id"));
        challenge.setCreator(row.getString("creator"));
        challenge.setParticipantA(row.getString("participanta"));
        challenge.setParticipantB(row.getString("participantb"));
        challenge.setCharacterA(row.getString("charactera"));
        challenge.setCharacterB(row.getString("characterb"));
        challenge.setCash(row.getFloat("cash"));
        challenge.setEndTime(row.getInt("endtime"));
        return challenge;
    }

    public Object[] getValuesAsArrayForChallengeTable(Challenge challenge){
        Object[] result = new Object[9];
        result[0] = challenge.getId();
        result[1] = challenge.getCreator();
        result[2] = challenge.getParticipantA();
        result[3] = challenge.getParticipantB();
        result[4] = challenge.getCharacterA();
        result[5] = challenge.getCharacterB();
        result[6] = challenge.getCash();
        result[7] = challenge.getEndTime();
        result[8] = challenge.getGame();
        return result;
    }

    public String[] getFieldsAsArrayForChallengeTable(){
        String[] result = new String[9];
        result[0] = "id";
        result[1] = "creator";
        result[2] = "participanta";
        result[3] = "participantb";
        result[4] = "charactera";
        result[5] = "characterb";
        result[6] = "cash";
        result[7] = "endtime";
        result[8] = "game";
        return result;
    }
}
