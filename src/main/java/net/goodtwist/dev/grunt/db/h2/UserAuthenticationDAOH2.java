package net.goodtwist.dev.grunt.db.h2;

import net.goodtwist.dev.grunt.core.UserAuthentication;
import net.goodtwist.dev.grunt.db.IUserAuthenticationDAO;

import com.google.common.base.Optional;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.hibernate.SessionFactory;

public class UserAuthenticationDAOH2 extends AbstractDAO<UserAuthentication> implements IUserAuthenticationDAO{

	public UserAuthenticationDAOH2(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public List<UserAuthentication> findByToken(String token) {
		return list(namedQuery("net.goodtwist.dev.grunt.core.UserAuthentication.findByToken")
				.setParameter("token", token));
	}	

	@Override
	public List<UserAuthentication> findByAccountId(long id){
		return list(namedQuery("net.goodtwist.dev.grunt.core.UserAuthentication.findByAccountId")
				.setParameter("accountid", id));
	}

}
