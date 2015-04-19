package net.goodtwist.dev.grunt.db;

import com.google.common.base.Optional;

import java.util.List;

import net.goodtwist.dev.grunt.core.UserAccount;

public interface IUserAccountDAO{

	public Optional<UserAccount> findById(Long id);
	public List<UserAccount> findByEqualUsername(String username);
	public List<UserAccount> findByLikeUsername(String username, int limit);
	public UserAccount create(UserAccount userAccount);
}
