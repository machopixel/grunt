package net.goodtwist.dev.grunt.core;

import com.fasterxml.jackson.annotation.JsonView;

import net.goodtwist.dev.grunt.jackson.views.Views;

public class UserAuthentication {
	@JsonView(Views.ServerView.class)
	private long id;
	@JsonView(Views.ServerView.class)
	private UserAccount account;
	@JsonView(Views.PrivateView.class)
	private String token;
	@JsonView(Views.ServerView.class)
	private boolean expired;
	@JsonView(Views.ServerView.class)
	private String macAddress;
	
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public UserAccount getAccount() {
		return account;
	}
	public void setAccount(UserAccount account) {
		this.account = account;
	}
	public boolean getExpired() {
		return expired;
	}
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
}