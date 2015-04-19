package net.goodtwist.dev.grunt.core;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import net.goodtwist.dev.grunt.jackson.views.Views;

@SuppressWarnings("serial")
@Entity
@Table(name = "user_friends")
@NamedQueries({
	@NamedQuery(
		name = "net.goodtwist.dev.grunt.core.UserFriends.listByAccountId",
		query = "SELECT u FROM UserFriends u WHERE u.accountid = :accountid")
})
public class UserFriends implements Serializable{
	@Id
	@Column(name = "account_id", nullable = false)
	@JsonView(Views.PrivateView.class)
	private long accountid;
	@Id
	@Column(name = "friend_account_id", nullable = false)
	@JsonView(Views.PrivateView.class)
	private long friendAccountId;
	
	public long getAccountid() {
		return accountid;
	}
	
	public void setAccountid(long accountid) {
		this.accountid = accountid;
	}
	
	public long getFriendAccountId() {
		return friendAccountId;
	}
	
	public void setFriendAccountId(long friendAccountId) {
		this.friendAccountId = friendAccountId;
	}
}