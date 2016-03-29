package com.nancyberry.wepost.support.bean;

import java.io.Serializable;

/**
 * Created by nan.zhang on 3/29/16.
 */
public class AccessToken implements Serializable {

    private String accessToken;

    private String userId;

    private long expiresIn;

    private String appKey;

    private String appSecret;

    private long createdAt = System.currentTimeMillis();


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - createdAt >= expiresIn * 1000;
    }
}
