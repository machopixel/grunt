package net.goodtwist.dev.grunt.db.mock;

import com.google.common.base.Optional;
import net.goodtwist.dev.grunt.core.Challenge;
import net.goodtwist.dev.grunt.db.IChallengeDAO;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by USUARIO on 15/12/2015.
 */
public class ChallengeDAOMock implements IChallengeDAO {

    Challenge newChallenge;

    public ChallengeDAOMock(){
        newChallenge = new Challenge();

        newChallenge.setCash(1.5f);
        newChallenge.setCharacterA("blodra");
        newChallenge.setCharacterB("supersopas");
        newChallenge.setCreator("machopixel");
        newChallenge.setEndTime(Instant.now().plus(Duration.ofDays(1)).toEpochMilli());
        newChallenge.setGame(0);
        newChallenge.setId(UUID.randomUUID());
        newChallenge.setJoinDateA(Instant.now().minus(Duration.ofHours(2)).toEpochMilli());
        newChallenge.setJoinDateB(Instant.now().minus(Duration.ofHours(1)).toEpochMilli());
        newChallenge.setParticipantA("machopixel");
        newChallenge.setParticipantA("sup3rs0pas");
    }

    @Override
    public Optional<Challenge> findById(UUID id) {
        return Optional.of(newChallenge);
    }

    @Override
    public Map<UUID, Challenge> findByCreator(String creator) {
        Map<UUID, Challenge> result = new HashMap<>();

        result.put(UUID.randomUUID(), newChallenge);

        return result;
    }

    @Override
    public Optional<Challenge> create(Challenge challenge) {
        return Optional.of(newChallenge);
    }

    @Override
    public Optional<Challenge> updateJoinDateA(Challenge challenge) {
        return Optional.of(newChallenge);
    }

    @Override
    public Optional<Challenge> updateJoinDateB(Challenge challenge) {
        return Optional.of(newChallenge);
    }
}
