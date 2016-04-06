package com.nancyberry.wepost.support.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by nan.zhang on 3/29/16.
 */
public class Account implements Serializable {

    private UUID id;

    private User user;

    private AccessToken accessToken;

    public Account() {
        id = UUID.randomUUID();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
