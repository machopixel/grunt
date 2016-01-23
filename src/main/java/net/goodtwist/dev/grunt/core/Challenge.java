package net.goodtwist.dev.grunt.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String participanta;
    @JsonView(Views.PublicView.class)
    private String participantb;
    @JsonView(Views.PublicView.class)
    private String charactera;
    @JsonView(Views.PublicView.class)
    private String characterb;
    @JsonView(Views.PublicView.class)
    private int game;
    @JsonView(Views.PublicView.class)
    private float cash;
    @JsonView(Views.PublicView.class)
    private long endtime;
    @JsonView(Views.PublicView.class)
    private long joindatea;
    @JsonView(Views.PublicView.class)
    private long joindateb;

    @JsonIgnore
    public long getJoinDateB() {
        return joindateb;
    }

    @JsonIgnore
    public void setJoinDateB(long joinDateB) {
        this.joindateb = joinDateB;
    }

    @JsonIgnore
    public long getJoinDateA() {
        return joindatea;
    }

    @JsonIgnore
    public void setJoinDateA(long joinDateA) {
        this.joindatea = joinDateA;
    }

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }

    @JsonIgnore
    public long getEndTime() {
        return endtime;
    }

    @JsonIgnore
    public void setEndTime(long endTime) {
        this.endtime = endTime;
    }

    @JsonIgnore
    public String getCharacterA() {
        return charactera;
    }

    @JsonIgnore
    public void setCharacterA(String characterA) {
        this.charactera = characterA;
    }

    @JsonIgnore
    public String getCharacterB() {
        return characterb;
    }

    @JsonIgnore
    public void setCharacterB(String characterB) {
        this.characterb = characterB;
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

    @JsonIgnore
    public String getParticipantA() {
        return participanta;
    }

    @JsonIgnore
    public void setParticipantA(String participantA) {
        this.participanta = participantA;
    }

    @JsonIgnore
    public String getParticipantB() {
        return participantb;
    }

    @JsonIgnore
    public void setParticipantB(String participantB) {
        this.participantb = participantB;
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }
}