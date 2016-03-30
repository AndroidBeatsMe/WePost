package com.nancyberry.wepost.support.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by nan.zhang on 3/29/16.
 */
public class AccessToken implements Serializable {

    private String accessTokenStr;

    private String userId;

    private long expiresIn;

    private String appKey;

    private String appSecret;

    private long createdAt = System.currentTimeMillis();

    public AccessToken(String accessTokenStr, long expiresIn) {
        this.accessTokenStr = accessTokenStr;
        this.expiresIn = expiresIn;
    }


    public String getAccessTokenStr() {
        return accessTokenStr;
    }

    public void setAccessTokenStr(String accessTokenStr) {
        this.accessTokenStr = accessTokenStr;
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

    public static AccessToken fromJson(Object jsonValue) throws IllegalArgumentException, JSONException {
        if (!(jsonValue instanceof JSONObject)) {
            throw new IllegalArgumentException("access token should be json object!");
        }

        JSONObject jsonObject = (JSONObject) jsonValue;
        return new AccessToken(jsonObject.getString("access_token"), jsonObject.getLong("expires_in"));
    }
}
