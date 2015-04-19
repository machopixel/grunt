package net.goodtwist.dev.grunt.db.h2;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.hibernate.SessionFactory;

import net.goodtwist.dev.grunt.core.UserFriends;
import net.goodtwist.dev.grunt.db.IUserFriendsDAO;

public class UserFriendsDAOH2 extends AbstractDAO<UserFriends>  implements IUserFriendsDAO{

	public UserFriendsDAOH2(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public List<UserFriends> listByAccountId(Long id) {
		return list(namedQuery("net.goodtwist.dev.grunt.core.UserFriends.listByAccountId")
				.setParameter("accountid", id));
	}

}
