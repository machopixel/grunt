package net.goodtwist.dev.grunt.db;

import net.goodtwist.dev.grunt.core.UserAccount;
import org.hibernate.SessionFactory;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

public class UserAccountDAO extends AbstractDAO<UserAccount>{

	public UserAccountDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<UserAccount> findById(Long id) {
		return Optional.fromNullable(get(id));
	}

	public List<UserAccount> findByUsername(String username) {
		return list(namedQuery("net.goodtwist.dev.grunt.core.UserAccount.findByUsername")
								.setParameter("username", username));
	}	

	public UserAccount create(UserAccount userAccount) {
		return persist(userAccount);
	}

}
