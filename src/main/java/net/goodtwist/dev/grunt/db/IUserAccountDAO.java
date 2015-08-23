package net.goodtwist.dev.grunt.db;

import com.google.common.base.Optional;

import java.util.Map;
import java.util.Set;

import net.goodtwist.dev.grunt.core.UserAccount;

public interface IUserAccountDAO{
	Optional<UserAccount> findByUsername(String username);
	Map<String, UserAccount> findMultipleByUsernames(String[] usernames);
	Optional<UserAccount> create(UserAccount userAccount);
	Set<UserAccount> getFriends(Set<String> friends);
}
