package com.nancyberry.wepost.sina.request.body;

/**
 * Created by nan.zhang on 4/12/16.
 */

import com.google.gson.annotations.SerializedName;

public class GetAccessTokenReqBody {

    @SerializedName("client_id")
    private String clientId;
    @SerializedName("client_secret")
    private String clientSecret;
    @SerializedName("grant_type")
    private String grantType;
    @SerializedName("code")
    private String code;
    @SerializedName("redirect_uri")
    private String redirectUri;

    /**
     * No args constructor for use in serialization
     *
     */
    public GetAccessTokenReqBody() {
    }

    /**
     *
     * @param grantType
     * @param code
     * @param redirectUri
     * @param clientSecret
     * @param clientId
     */
    public GetAccessTokenReqBody(String clientId, String clientSecret, String grantType, String code, String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.code = code;
        this.redirectUri = redirectUri;
    }

    /**
     *
     * @return
     * The clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     *
     * @param clientId
     * The client_id
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public GetAccessTokenReqBody withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     *
     * @return
     * The clientSecret
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     *
     * @param clientSecret
     * The client_secret
     */
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public GetAccessTokenReqBody withClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    /**
     *
     * @return
     * The grantType
     */
    public String getGrantType() {
        return grantType;
    }

    /**
     *
     * @param grantType
     * The grant_type
     */
    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public GetAccessTokenReqBody withGrantType(String grantType) {
        this.grantType = grantType;
        return this;
    }

    /**
     *
     * @return
     * The code
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    public GetAccessTokenReqBody withCode(String code) {
        this.code = code;
        return this;
    }

    /**
     *
     * @return
     * The redirectUri
     */
    public String getRedirectUri() {
        return redirectUri;
    }

    /**
     *
     * @param redirectUri
     * The redirect_uri
     */
    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public GetAccessTokenReqBody withRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

}