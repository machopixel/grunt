package net.goodtwist.dev.grunt.db;

import com.google.common.base.Optional;

import java.util.List;

import net.goodtwist.dev.grunt.core.Challenge;

public interface IChallengeDAO{

	public Optional<Challenge> findById(Long id);
	public List<Challenge> findByCreator(Long id);
	public List<Challenge> findByParticipant(Long id);
	public Challenge create(Challenge challenge);
}
