package net.goodtwist.dev.grunt.db;

import net.goodtwist.dev.grunt.core.UserAuthentication;
import com.google.common.base.Optional;

import java.util.List;

public interface IUserAuthenticationDAO {

	public List<UserAuthentication> findByToken(String token);
	public List<UserAuthentication> findByAccountId(long id);

}
