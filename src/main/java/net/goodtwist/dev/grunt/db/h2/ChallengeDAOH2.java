package net.goodtwist.dev.grunt.db.h2;

import java.util.List;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;

import io.dropwizard.hibernate.AbstractDAO;
import net.goodtwist.dev.grunt.core.Challenge;
import net.goodtwist.dev.grunt.db.IChallengeDAO;

public class ChallengeDAOH2 extends AbstractDAO<Challenge>  implements IChallengeDAO{

	public ChallengeDAOH2(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Optional<Challenge> findById(Long id) {
		return Optional.fromNullable(get(id));
	}

	@Override
	public List<Challenge> findByCreator(Long id) {
		return list(namedQuery("net.goodtwist.dev.grunt.core.Challenge.findByCreator")
				.setParameter("accountid", id));
	}

	@Override
	public List<Challenge> findByParticipant(Long id) {
		return list(namedQuery("net.goodtwist.dev.grunt.core.Challenge.findByParticipant")
				.setParameter("accountid", id));
	}

	@Override
	public Challenge create(Challenge challenge) {
		return persist(challenge);
	}

}
