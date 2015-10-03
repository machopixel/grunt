package net.goodtwist.dev.grunt.db;

import com.google.common.base.Optional;

import java.util.Map;
import java.util.UUID;

import net.goodtwist.dev.grunt.core.Challenge;

public interface IChallengeDAO{

	Optional<Challenge> findById(UUID id);
	Map<UUID, Challenge> findByCreator(String creator);
	Optional<Challenge> create(Challenge challenge);
	Optional<Challenge> updateJoinDateA(Challenge challenge);
	Optional<Challenge> updateJoinDateB(Challenge challenge);
}
