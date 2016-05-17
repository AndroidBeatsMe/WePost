package com.nancyberry.wepost.sina.request.params;

/**
 * Created by nan.zhang on 5/16/16.
 */

import com.google.gson.annotations.SerializedName;

public class GetTimelineCommentsReqParams {

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("id")
    private Long id;
    @SerializedName("since_id")
    private Long sinceId;
    @SerializedName("max_id")
    private Long maxId;
    @SerializedName("count")
    private Integer count;
    @SerializedName("page")
    private Integer page;
    @SerializedName("filter_by_author")
    private Integer filterByAuthor;

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

    public GetTimelineCommentsReqParams withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     *
     * @return
     * The id
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Long id) {
        this.id = id;
    }

    public GetTimelineCommentsReqParams withId(Long id) {
        this.id = id;
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

    public GetTimelineCommentsReqParams withSinceId(Long sinceId) {
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

    public GetTimelineCommentsReqParams withMaxId(Long maxId) {
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

    public GetTimelineCommentsReqParams withCount(Integer count) {
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

    public GetTimelineCommentsReqParams withPage(Integer page) {
        this.page = page;
        return this;
    }

    /**
     *
     * @return
     * The filterByAuthor
     */
    public Integer getFilterByAuthor() {
        return filterByAuthor;
    }

    /**
     *
     * @param filterByAuthor
     * The filter_by_author
     */
    public void setFilterByAuthor(Integer filterByAuthor) {
        this.filterByAuthor = filterByAuthor;
    }

    public GetTimelineCommentsReqParams withFilterByAuthor(Integer filterByAuthor) {
        this.filterByAuthor = filterByAuthor;
        return this;
    }

}