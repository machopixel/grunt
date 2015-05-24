package net.goodtwist.dev.grunt.db;

import net.goodtwist.dev.grunt.core.UserAuthentication;

import java.util.List;

public interface IUserAuthenticationDAO {

	public List<UserAuthentication> findByToken(String token);
	public List<UserAuthentication> findByAccountId(long id);
	public UserAuthentication create(UserAuthentication userAuthentication);
	public void disableByAccountId(long id);

}
