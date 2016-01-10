package net.goodtwist.dev.grunt.core;

import com.fasterxml.jackson.annotation.JsonView;
import net.goodtwist.dev.grunt.jackson.views.Views;

public class GameLink {
    @JsonView(Views.PublicView.class)
    private String username;
    @JsonView(Views.PublicView.class)
    private int game;
    @JsonView(Views.PublicView.class)
    private String region;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

}
