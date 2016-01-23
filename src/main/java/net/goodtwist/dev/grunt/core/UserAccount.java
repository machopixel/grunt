package net.goodtwist.dev.grunt.core;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	private List<String> friends;
	@JsonView(Views.PublicView.class)
	private boolean onlinestatus;
	@JsonView(Views.PublicView.class)
	private int membershipstatus;
	@JsonView(Views.ServerView.class)
	private UUID confirmationKey;


	public UUID getConfirmationKey() {
		return confirmationKey;
	}

	public void setConfirmationKey(UUID confirmationKey) {
		this.confirmationKey = confirmationKey;
	}
	@JsonIgnore
	public int getMembershipStatus(){
		return this.membershipstatus;
	}
	@JsonIgnore
	public void setMembershipStatus(int newMembershipStatus){
		this.membershipstatus = newMembershipStatus;
	}
	@JsonIgnore
	public boolean getOnlineStatus(){
		return onlinestatus;
	}
	@JsonIgnore
	public void setOnlineStatus(boolean onlineStatus){
		this.onlinestatus = onlineStatus;
	}

	public List<String> getFriends() {
		if (this.friends == null){
			this.friends = new ArrayList<>();
		}
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	public void addFriend(String friendUsername){
		if (this.friends == null){
			this.friends = new ArrayList<>();
		}
		friends.add(friendUsername);
	}

	public void deleteFriend(String friendUsername){
		if (this.friends == null){
			this.friends = new ArrayList<>();
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