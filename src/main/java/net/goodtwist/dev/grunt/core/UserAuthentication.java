package net.goodtwist.dev.grunt.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
		query = "SELECT u FROM UserAuthentication u WHERE u.account.id = :accountid"),
	@NamedQuery(
		name = "net.goodtwist.dev.grunt.core.UserAuthentication.disableByAccountId",
		query = "UPDATE UserAuthentication u SET u.expired = false WHERE u.account.id = :accountid")
})
public class UserAuthentication {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.ServerView.class)
	private long id; 
	@ManyToOne
    @JoinColumn(name="account_id")
	@JsonView(Views.ServerView.class)
	private UserAccount account;
	@Column(name = "token", nullable = false)
	@JsonView(Views.PrivateView.class)
	private String token;
	@Column(name = "expired", nullable = false)
	@JsonView(Views.ServerView.class)
	private boolean expired;
	@Column(name = "mac_address", nullable = false)
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