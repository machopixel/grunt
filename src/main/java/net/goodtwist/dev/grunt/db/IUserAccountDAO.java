package net.goodtwist.dev.grunt.db;

import com.google.common.base.Optional;

import java.util.List;

import net.goodtwist.dev.grunt.core.UserAccount;

public interface IUserAccountDAO{
	Optional<UserAccount> findByUsername(String username);
	List<UserAccount> searchByUsername(String username, int limit);

	Optional<UserAccount> create(UserAccount userAccount);
	Optional<UserAccount> updateFriends(UserAccount userAccount);

	List<UserAccount> getFriends(List<String> friends);
}
