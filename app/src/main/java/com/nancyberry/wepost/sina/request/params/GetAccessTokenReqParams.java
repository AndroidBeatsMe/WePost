package com.nancyberry.wepost.sina.request.params;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nan.zhang on 5/10/16.
 */
public class GetAccessTokenReqParams {

    @SerializedName("client_id")
    private String clientId;
    @SerializedName("client_secret")
    private String clientSecret;
    @SerializedName("code")
    private String code;
    @SerializedName("grant_type")
    private String grantType;
    @SerializedName("redirect_uri")
    private String redirectUri;

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

    public GetAccessTokenReqParams withClientId(String clientId) {
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

    public GetAccessTokenReqParams withClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
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

    public GetAccessTokenReqParams withCode(String code) {
        this.code = code;
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

    public GetAccessTokenReqParams withGrantType(String grantType) {
        this.grantType = grantType;
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

    public GetAccessTokenReqParams withRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

}