package net.goodtwist.dev.grunt.db.h2;

import com.google.common.base.Optional;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import net.goodtwist.dev.grunt.core.UserAccount;
import net.goodtwist.dev.grunt.db.IUserAccountDAO;

import org.hibernate.SessionFactory;

public class UserAccountDAOH2 extends AbstractDAO<UserAccount> implements IUserAccountDAO{

	public UserAccountDAOH2(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Optional<UserAccount> findById(Long id) {
		return Optional.fromNullable(get(id));
	}

	@Override
	public List<UserAccount> findByEqualUsername(String username) {
		return list(namedQuery("net.goodtwist.dev.grunt.core.UserAccount.findByEqualUsername")
								.setParameter("username", username));
	}	

	@Override
	public UserAccount create(UserAccount userAccount) {
		return persist(userAccount);
	}

	@Override
	public List<UserAccount> findByLikeUsername(String username, int limit) {
		return list(namedQuery("net.goodtwist.dev.grunt.core.UserAccount.findByLikeUsername")
				.setParameter("username", username)
				.setParameter("limit", limit));
	}

}
