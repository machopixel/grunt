package net.goodtwist.dev.grunt.core;

import com.fasterxml.jackson.annotation.JsonView;

import net.goodtwist.dev.grunt.jackson.views.Views;

import java.util.Date;
import java.util.UUID;

public class Challenge {
    @JsonView(Views.PublicView.class)
    private UUID id;
    @JsonView(Views.PublicView.class)
    private String creator;
    @JsonView(Views.PublicView.class)
    private String participantA;
    @JsonView(Views.PublicView.class)
    private String participantB;
    @JsonView(Views.PublicView.class)
    private String characterA;
    @JsonView(Views.PublicView.class)
    private String characterB;
    @JsonView(Views.PublicView.class)
    private int game;
    @JsonView(Views.PublicView.class)
    private float cash;
    @JsonView(Views.PublicView.class)
    private long endTime;
    @JsonView(Views.PublicView.class)
    private long joinDateA;
    @JsonView(Views.PublicView.class)
    private long joinDateB;

    public long getJoinDateB() {
        return joinDateB;
    }

    public void setJoinDateB(long joinDateB) {
        this.joinDateB = joinDateB;
    }

    public long getJoinDateA() {
        return joinDateA;
    }

    public void setJoinDateA(long joinDateA) {
        this.joinDateA = joinDateA;
    }

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getCharacterA() {
        return characterA;
    }

    public void setCharacterA(String characterA) {
        this.characterA = characterA;
    }

    public String getCharacterB() {
        return characterB;
    }

    public void setCharacterB(String characterB) {
        this.characterB = characterB;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getParticipantA() {
        return participantA;
    }

    public void setParticipantA(String participantA) {
        this.participantA = participantA;
    }

    public String getParticipantB() {
        return participantB;
    }

    public void setParticipantB(String participantB) {
        this.participantB = participantB;
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }
}