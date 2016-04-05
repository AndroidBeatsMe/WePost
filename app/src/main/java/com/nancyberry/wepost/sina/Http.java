package com.nancyberry.wepost.sina;

import com.nancyberry.wepost.common.context.GlobalContext;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nan.zhang on 4/5/16.
 */
public class Http {
    private static volatile SinaApi sinaApi;
    private static OkHttpClient okHttpClient;
    private static Converter.Factory gsonConverterFactory;
    private static CallAdapter.Factory rxJavaCallAdapterFactory;

    public static SinaApi getSinaApi() {
        if (sinaApi == null) {
            synchronized (Http.class) {
                okHttpClient = new OkHttpClient();
                gsonConverterFactory = GsonConverterFactory.create();
                rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

                Retrofit retrofit = new Retrofit.Builder()
                        .client(okHttpClient)
                        .baseUrl(GlobalContext.getInstance().BASE_URI)
                        .addConverterFactory(gsonConverterFactory)
                        .addCallAdapterFactory(rxJavaCallAdapterFactory)
                        .build();
                sinaApi = retrofit.create(SinaApi.class);
            }
        }

        return sinaApi;
    }
}
