package com.nancyberry.wepost.sina.request.params;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nan.zhang on 4/11/16.
 */
public class GetFriendsTimelineReqParams {

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("since_id")
    private Long sinceId;
    @SerializedName("max_id")
    private Long maxId;
    @SerializedName("count")
    private Integer count;
    @SerializedName("page")
    private Integer page;
    @SerializedName("base_app")
    private Integer baseApp;
    @SerializedName("feature")
    private Integer feature;
    @SerializedName("trim_user")
    private Integer trimUser;

    /**
     * No args constructor for use in serialization
     *
     */
    public GetFriendsTimelineReqParams() {
    }

    /**
     *
     * @param sinceId
     * @param count
     * @param accessToken
     * @param page
     * @param trimUser
     * @param feature
     * @param baseApp
     * @param maxId
     */
    public GetFriendsTimelineReqParams(String accessToken, Long sinceId, Long maxId, Integer count, Integer page, Integer baseApp, Integer feature, Integer trimUser) {
        this.accessToken = accessToken;
        this.sinceId = sinceId;
        this.maxId = maxId;
        this.count = count;
        this.page = page;
        this.baseApp = baseApp;
        this.feature = feature;
        this.trimUser = trimUser;
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

    public GetFriendsTimelineReqParams withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     *
     * @return
     * The sinceId
     */
    public Long getSinceId() {
        return sinceId;
    }

    /**
     *
     * @param sinceId
     * The since_id
     */
    public void setSinceId(Long sinceId) {
        this.sinceId = sinceId;
    }

    public GetFriendsTimelineReqParams withSinceId(Long sinceId) {
        this.sinceId = sinceId;
        return this;
    }

    /**
     *
     * @return
     * The maxId
     */
    public Long getMaxId() {
        return maxId;
    }

    /**
     *
     * @param maxId
     * The max_id
     */
    public void setMaxId(Long maxId) {
        this.maxId = maxId;
    }

    public GetFriendsTimelineReqParams withMaxId(Long maxId) {
        this.maxId = maxId;
        return this;
    }

    /**
     *
     * @return
     * The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    public GetFriendsTimelineReqParams withCount(Integer count) {
        this.count = count;
        return this;
    }

    /**
     *
     * @return
     * The page
     */
    public Integer getPage() {
        return page;
    }

    /**
     *
     * @param page
     * The page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    public GetFriendsTimelineReqParams withPage(Integer page) {
        this.page = page;
        return this;
    }

    /**
     *
     * @return
     * The baseApp
     */
    public Integer getBaseApp() {
        return baseApp;
    }

    /**
     *
     * @param baseApp
     * The base_app
     */
    public void setBaseApp(Integer baseApp) {
        this.baseApp = baseApp;
    }

    public GetFriendsTimelineReqParams withBaseApp(Integer baseApp) {
        this.baseApp = baseApp;
        return this;
    }

    /**
     *
     * @return
     * The feature
     */
    public Integer getFeature() {
        return feature;
    }

    /**
     *
     * @param feature
     * The feature
     */
    public void setFeature(Integer feature) {
        this.feature = feature;
    }

    public GetFriendsTimelineReqParams withFeature(Integer feature) {
        this.feature = feature;
        return this;
    }

    /**
     *
     * @return
     * The trimUser
     */
    public Integer getTrimUser() {
        return trimUser;
    }

    /**
     *
     * @param trimUser
     * The trim_user
     */
    public void setTrimUser(Integer trimUser) {
        this.trimUser = trimUser;
    }

    public GetFriendsTimelineReqParams withTrimUser(Integer trimUser) {
        this.trimUser = trimUser;
        return this;
    }

}
