package com.nancyberry.wepost.ui.timeline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.nancyberry.wepost.sina.Http;
import com.nancyberry.wepost.support.model.Account;
import com.nancyberry.wepost.support.model.StatusContent;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by nan.zhang on 4/5/16.
 */
public class FriendTimelineActivity extends Activity {
    public static final String TAG = FriendTimelineActivity.class.getSimpleName();
    public static final String BUNDLE_ACCOUNT = "account";
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        Account account = (Account) getIntent().getSerializableExtra(BUNDLE_ACCOUNT);

        mSubscription = Http.getSinaApi()
                .getFriendsTimeline(account.getAccessToken().getAccessTokenStr(), 20)
                .flatMap(new Func1<StatusContent.StatusContentList, Observable<StatusContent>>() {
                    @Override
                    public Observable<StatusContent> call(StatusContent.StatusContentList statusContentList) {
                        return Observable.from(statusContentList.getStatusContentList());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StatusContent>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(StatusContent statusContent) {
                        Log.d(TAG, "onNext");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();
    }

    public static void actionStart(Context context, Account account) {
        Intent intent = new Intent(context, FriendTimelineActivity.class);
        intent.putExtra(BUNDLE_ACCOUNT, account);
        context.startActivity(intent);
    }

}
