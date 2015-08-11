package net.goodtwist.dev.grunt.db;

import com.google.common.base.Optional;

import java.util.List;

import net.goodtwist.dev.grunt.core.Challenge;

public interface IChallengeDAO{

	Optional<Challenge> findById(Long id);
	List<Challenge> findByAnyParticipant(String creator, String participantA, String participantB);
	Optional<Challenge> create(Challenge challenge);
}
