package com.nancyberry.wepost.sina;

import com.nancyberry.wepost.support.model.AccessToken;
import com.nancyberry.wepost.support.model.StatusContentList;
import com.nancyberry.wepost.support.model.Uid;
import com.nancyberry.wepost.support.model.User;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
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
    Observable<Uid> getUid(
            @Query("access_token") String accessToken);

    @GET("users/show.json")
    Observable<User> getUserShowByUid(
            @Query("access_token") String accessToken,
            @Query("uid") Long uid);

    @GET("users/show.json")
    Observable<User> getUserShowByName(
            @Query("access_token") String accessToken,
            @Query("screen_name") String name);

    @GET("statuses/friends_timeline.json")
    Observable<StatusContentList> getFriendsTimeline(@QueryMap Map<String, Object> request);

}
