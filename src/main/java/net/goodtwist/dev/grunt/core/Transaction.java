package net.goodtwist.dev.grunt.core;

import com.fasterxml.jackson.annotation.JsonView;
import net.goodtwist.dev.grunt.jackson.views.Views;

import java.util.UUID;

/**
 * Created by Diego on 9/5/2015.
 */
public abstract class Transaction {
    @JsonView(Views.ServerView.class)
    private UUID id;
    @JsonView(Views.ServerView.class)
    private String username;
    @JsonView(Views.PublicView.class)
    private String method;
    @JsonView(Views.PublicView.class)
    private boolean direction;
    @JsonView(Views.ServerView.class)
    private String externalId;
    @JsonView(Views.PublicView.class)
    private int date;
    @JsonView(Views.PublicView.class)
    private float amount;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
