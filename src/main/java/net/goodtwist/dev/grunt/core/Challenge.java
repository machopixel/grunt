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
@Table(name = "challenge")
@NamedQueries({
	@NamedQuery(
		name = "net.goodtwist.dev.grunt.core.Challenge.findByCreator",
		query = "SELECT c FROM Challenge c WHERE c.creatorId = :accountid"),
	@NamedQuery(
		name = "net.goodtwist.dev.grunt.core.Challenge.findByParticipant",
		query = "SELECT c FROM Challenge c WHERE c.participantAId = :accountid OR c.participantBId = :accountid")
})
public class Challenge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	@JsonView(Views.PublicView.class)
	private long id;
	@Column(name = "creator", nullable = false)
	@JsonView(Views.PublicView.class)
	private long creatorId;
	@Column(name = "participant_a", nullable = false)
	@JsonView(Views.PublicView.class)
	private long participantAId;
	@Column(name = "participant_b", nullable = false)
	@JsonView(Views.PublicView.class)
	private long participantBId;
	@Column(name = "gameid", nullable = false)
	@JsonView(Views.PublicView.class)
	private long gameId;
	
	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}

	public long getParticipantAId() {
		return participantAId;
	}

	public void setParticipantAId(long participantAId) {
		this.participantAId = participantAId;
	}

	public long getParticipantBId() {
		return participantBId;
	}

	public void setParticipantBId(long participantBId) {
		this.participantBId = participantBId;
	}

}