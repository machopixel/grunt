package net.goodtwist.dev.grunt.db;

import java.util.List;

import net.goodtwist.dev.grunt.core.UserFriends;

public interface IUserFriendsDAO{

	public List<UserFriends> listByAccountId(Long id);
}
