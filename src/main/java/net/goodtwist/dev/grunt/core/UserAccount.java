package net.goodtwist.dev.grunt.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import net.goodtwist.dev.grunt.jackson.views.Views;

@Entity
@Table(name = "user_account")
@NamedQueries({
	@NamedQuery(
		name = "net.goodtwist.dev.grunt.core.UserAccount.findByEqualUsername",
		query = "SELECT u FROM UserAccount u WHERE u.username = :username"),
	@NamedQuery(
		name = "net.goodtwist.dev.grunt.core.UserAccount.findByLikeUsername",
		query = "SELECT u FROM UserAccount u WHERE u.username LIKE :username")
})
public class UserAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.PublicView.class)
	private long id;
	@Column(name = "username", nullable = false)
	@JsonView(Views.PublicView.class)
	private String username;
	@Column(name = "password", nullable = false)
	@JsonView(Views.ServerView.class)
	private String password;
	@Column(name = "email", nullable = false)
	@JsonView(Views.PrivateView.class)
	private String email;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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