package net.goodtwist.dev.grunt.core;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.goodtwist.dev.grunt.jackson.views.Views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Optional;

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
    @OneToMany(mappedBy="account")
	@JsonView(Views.ServerView.class)
    private Set<UserAuthentication> userAuthentications;
    @ManyToMany
    @JoinTable(name = "user_friends", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "friend_account_id"))
	@JsonView(Views.PrivateView.class)
    private Set<UserAccount> friendsUserAccounts;
	
	public Set<UserAccount> getUserFriends() {
		return friendsUserAccounts;
	}

	public void setUserFriends(Set<UserAccount> friendsUserAccounts) {
		this.friendsUserAccounts = friendsUserAccounts;
	}

	public Set<UserAuthentication> getUserAuthentications() {
		return userAuthentications;
	}

	public void setUserAuthentications(Set<UserAuthentication> userAuthentications) {
		this.userAuthentications = userAuthentications;
	}
	
	@JsonIgnore
	public Optional<UserAuthentication> getValidUserAuthentications(){
		if (this.userAuthentications != null && this.userAuthentications.size() > 1){
			for (UserAuthentication userAuthenticationIte : this.userAuthentications) {
			    if (!userAuthenticationIte.getExpired()){
			    	Optional.of(userAuthenticationIte);
			    }
			}
		}
		return Optional.absent();
	}
	
	public void addUserAuthentications(UserAuthentication userAuthentication){
		if (this.userAuthentications == null){
			this.userAuthentications = new HashSet<UserAuthentication>();
		}
		this.userAuthentications.add(userAuthentication);
	}
	
	public void addFriendUserAccount(UserAccount friendUserAccount){
		if (this.friendsUserAccounts == null){
			this.friendsUserAccounts = new HashSet<UserAccount>();
		}
		friendsUserAccounts.add(friendUserAccount);
	}

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