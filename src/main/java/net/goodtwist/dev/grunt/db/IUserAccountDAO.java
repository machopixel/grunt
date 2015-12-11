package net.goodtwist.dev.grunt.db;

import com.google.common.base.Optional;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.goodtwist.dev.grunt.core.UserAccount;

public interface IUserAccountDAO{
	Optional<UserAccount> findByUsername(String username);
	List<String> searchByUsername(String username, int limit);
	Optional<UserAccount> create(UserAccount userAccount);
	Optional<UserAccount> updateFriends(UserAccount userAccount);
}
