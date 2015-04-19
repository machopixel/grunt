package net.goodtwist.dev.grunt.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import net.goodtwist.dev.grunt.jackson.views.Views;

@Entity
@Table(name = "user_authentication")
@NamedQueries({
	@NamedQuery(
		name = "net.goodtwist.dev.grunt.core.UserAuthentication.findByToken",
		query = "SELECT u FROM UserAuthentication u WHERE u.token = :token"),
	@NamedQuery(
		name = "net.goodtwist.dev.grunt.core.UserAuthentication.findByAccountId",
		query = "SELECT u FROM UserAuthentication u WHERE u.accountid = :accountid")
})
public class UserAuthentication {
	@Id
	@Column(name = "account_id", nullable = false)
	@JsonView(Views.ServerView.class)
	private long accountId;
	@Column(name = "token", nullable = false)
	@JsonView(Views.ServerView.class)
	private String token;
	@Column(name = "mac_address", nullable = false)
	@JsonView(Views.ServerView.class)
	private String macAddress;
	
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}