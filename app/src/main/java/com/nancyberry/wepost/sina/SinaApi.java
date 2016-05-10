package com.nancyberry.wepost.sina;

import com.nancyberry.wepost.support.model.AccessToken;
import com.nancyberry.wepost.support.model.StatusContentList;
import com.nancyberry.wepost.support.model.Uid;
import com.nancyberry.wepost.support.model.User;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by nan.zhang on 4/5/16.
 */
public interface SinaApi {

    @POST("https://api.weibo.com/oauth2/access_token")
    Observable<AccessToken> getAccessToken(@QueryMap Map<String, String> queryMap);

    @GET("account/get_uid.json")
    Observable<Uid> getUid(@Query("access_token") String accessToken);

    @GET("users/show.json")
    Observable<User> getUserInfo(@QueryMap Map<String, String> queryMap);

    @GET("statuses/friends_timeline.json")
    Observable<StatusContentList> getFriendsTimeline(@QueryMap Map<String, String> queryMap);

}
