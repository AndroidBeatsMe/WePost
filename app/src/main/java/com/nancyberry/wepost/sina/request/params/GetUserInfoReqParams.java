package com.nancyberry.wepost.sina.request.params;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nan.zhang on 4/12/16.
 */
public class GetUserInfoReqParams {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("uid")
    private Long uid;
    @SerializedName("screen_name")
    private String screenName;

    /**
     * No args constructor for use in serialization
     *
     */
    public GetUserInfoReqParams() {
    }

    /**
     *
     * @param uid
     * @param accessToken
     * @param screenName
     */
    public GetUserInfoReqParams(String accessToken, Long uid, String screenName) {
        this.accessToken = accessToken;
        this.uid = uid;
        this.screenName = screenName;
    }

    /**
     *
     * @return
     * The accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     *
     * @param accessToken
     * The access_token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public GetUserInfoReqParams withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     *
     * @return
     * The uid
     */
    public Long getUid() {
        return uid;
    }

    /**
     *
     * @param uid
     * The uid
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    public GetUserInfoReqParams withUid(Long uid) {
        this.uid = uid;
        return this;
    }

    /**
     *
     * @return
     * The screenName
     */
    public String getScreenName() {
        return screenName;
    }

    /**
     *
     * @param screenName
     * The screen_name
     */
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public GetUserInfoReqParams withScreenName(String screenName) {
        this.screenName = screenName;
        return this;
    }

}
