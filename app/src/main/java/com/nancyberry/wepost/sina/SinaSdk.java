package com.nancyberry.wepost.sina;

import android.util.Log;

import com.nancyberry.wepost.common.concurrent.TaskManager;
import com.nancyberry.wepost.common.network.HttpServiceCall;
import com.nancyberry.wepost.common.network.JsonServiceCall;
import com.nancyberry.wepost.common.network.PlusRequest;
import com.nancyberry.wepost.common.setting.SettingUtils;
import com.nancyberry.wepost.support.bean.AccessToken;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by nan.zhang on 3/30/16.
 */
public class SinaSdk {

    public static final String TAG = SinaSdk.class.getSimpleName();

    private AccessToken mAccessToken;

    private SinaSdk(AccessToken accessToken) {
        mAccessToken = accessToken;
    }

    public static SinaSdk getInstance(AccessToken accessToken) {
        return new SinaSdk(accessToken);
    }

    private String getClientId() {
        return SettingUtils.getStringSetting("client_id");
    }

    private String getClientSecret() {
        return SettingUtils.getStringSetting("client_secret");
    }

    private String getRedirectUri() {
        return SettingUtils.getStringSetting("redirect_uri");
    }

    public AccessToken fetchAccessToken(String code) {
        PlusRequest request = new PlusRequest(SettingUtils.getSetting("access_token").getExtras().get("base_url").getValue(), SettingUtils.getSetting("access_token").getValue());

        request.addPostParam("client_id", getClientId());
        request.addPostParam("client_secret", getClientSecret());
        request.addPostParam("grant_type", "authorization_code");
        request.addPostParam("code", code);
        request.addPostParam("redirect_uri", getRedirectUri());

        Log.v(TAG, request.toString());

        Future<AccessToken> future = new JsonServiceCall<AccessToken>(request,
                new HttpServiceCall.IExecutor() {
                    @Override
                    public void execute(Runnable task) {
                        TaskManager.run(task);
                    }
                }) {
            @Override
            protected AccessToken processJson(Object jsonValue) throws Exception {
                return AccessToken.fromJson(jsonValue);
            }
        }.queue();

        try {
            return future.get();
        } catch (InterruptedException ie) {
            Log.e(TAG, ie.getMessage());
        } catch (ExecutionException ee) {
            Log.e(TAG, ee.getMessage());
        }

        return null;
    }
}
