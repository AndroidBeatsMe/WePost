package com.nancyberry.wepost.sina;

import com.nancyberry.wepost.support.bean.AccessToken;
import com.nancyberry.wepost.support.bean.User;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by nan.zhang on 4/5/16.
 */
public interface SinaApi {

    @FormUrlEncoded
    @POST("https://api.weibo.com/oauth2/access_token")
    Observable<AccessToken> getAccessToken(
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("grant_type") String grant_type,
            @Field("code") String code,
            @Field("redirect_uri") String redirect_uri);

    @GET("account/get_uid.json")
    Observable<ResponseBody> getUid(
            @Query("access_token") String accessToken);

    @GET("users/show.json")
    Observable<User> getUserShow(
            @Query("access_token") String accessToken,
            @Query("uid") String uid);

}
