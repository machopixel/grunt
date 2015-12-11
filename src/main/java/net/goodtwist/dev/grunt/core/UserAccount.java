package net.goodtwist.dev.grunt.core;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
	private Set<String> friends;
	@JsonView(Views.PublicView.class)
	private boolean onlineStatus;
	@JsonView(Views.PublicView.class)
	private int membershipStatus;
	@JsonView(Views.ServerView.class)
	private Set<UUID> confirmationKey;


	public Set<UUID> getConfirmationKey() {
		if (this.confirmationKey == null){
			this.confirmationKey = new HashSet<UUID>();
		}
		return confirmationKey;
	}

	public void setConfirmationKey(Set<UUID> confirmationKey) {
		this.confirmationKey = confirmationKey;
	}

	public void addConfirmationKey(UUID confirmationKey) {
		if (this.confirmationKey == null){
			this.confirmationKey = new HashSet<UUID>();
		}
		this.confirmationKey.add(confirmationKey);
	}

	public int getMembershipStatus(){
		return this.membershipStatus;
	}

	public void setMembershipStatus(int newMembershipStatus){
		this.membershipStatus = newMembershipStatus;
	}

	public boolean getOnlineStatus(){
		return onlineStatus;
	}

	public void setOnlineStatus(boolean onlineStatus){
		this.onlineStatus = onlineStatus;
	}


	public Set<String> getFriends() {
		if (this.friends == null){
			this.friends = new HashSet<>();
		}
		return friends;
	}

	public void setFriends(Set<String> friends) {
		this.friends = friends;
	}

	public void addFriend(String friendUsername){
		if (this.friends == null){
			this.friends = new HashSet<>();
		}
		friends.add(friendUsername);
	}

	public void deleteFriend(String friendUsername){
		if (this.friends == null){
			this.friends = new HashSet<>();
		}

		this.friends.remove(friendUsername);
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