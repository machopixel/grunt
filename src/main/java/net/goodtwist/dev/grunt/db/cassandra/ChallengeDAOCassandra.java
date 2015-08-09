package net.goodtwist.dev.grunt.db.cassandra;

import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.core.Challenge;
import net.goodtwist.dev.grunt.db.IChallengeDAO;

import java.util.List;

/**
 * Created by Diego on 8/9/2015.
 */
public class ChallengeDAOCassandra implements IChallengeDAO{

    public ChallengeDAOCassandra() {
    }

    @Override
    public Optional<Challenge> findById (Long id){
        return null;
    }

    @Override
    public List<Challenge> findByAnyField (String creator, String participantA, String participantB, String
            characterA, String characterB){
        return null;
    }

    @Override
    public Optional<Challenge> create (Challenge challenge){
        return null;
    }
}
