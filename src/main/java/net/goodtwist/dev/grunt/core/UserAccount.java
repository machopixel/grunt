package net.goodtwist.dev.grunt.core;

import java.util.HashSet;
import java.util.Set;

import net.goodtwist.dev.grunt.jackson.views.Views;

import com.fasterxml.jackson.annotation.JsonView;

public class UserAccount {
	@JsonView(Views.PublicView.class)
	private String username;
	@JsonView(Views.ServerView.class)
	private String password;
	@JsonView(Views.PrivateView.class)
	private String email;
    @JsonView(Views.PrivateView.class)
    private Set<UserAccount> friends;

	public Set<UserAccount> getFriends() {
		if (this.friends == null){
			this.friends = new HashSet<UserAccount>();
		}
		return friends;
	}

	public void setFriends(Set<UserAccount> friends) {
		this.friends = friends;
	}

	public void addFriend(UserAccount friendUserAccount){
		if (this.friends == null){
			this.friends = new HashSet<UserAccount>();
		}
		friends.add(friendUserAccount);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}