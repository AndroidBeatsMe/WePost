package com.nancyberry.wepost.ui.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nancyberry.wepost.R;
import com.nancyberry.wepost.common.util.HttpUtils;
import com.nancyberry.wepost.sina.Http;
import com.nancyberry.wepost.sina.request.params.GetUserInfoReqParams;
import com.nancyberry.wepost.support.model.AccessToken;
import com.nancyberry.wepost.support.model.Account;
import com.nancyberry.wepost.support.model.Uid;
import com.nancyberry.wepost.support.model.User;
import com.nancyberry.wepost.ui.login.LoginActivity;
import com.nancyberry.wepost.ui.timeline.FriendTimelineActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by nan.zhang on 3/29/16.
 */
public class AccountActivity extends Activity {
    public static final String TAG = AccountActivity.class.getSimpleName();
    public static final int REQUEST_LOGIN = 1;
    private List<Account> mAccountList;
    private AccountAdapter mAccountAdapter;
    private Subscription mSubscription;

    @Bind(R.id.account_listview)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.account_page_title);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        mAccountList = AccountLab.get(this).getAccountList();
        mAccountAdapter = new AccountAdapter(mAccountList);
        mListView.setAdapter(mAccountAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Account account = (Account) parent.getItemAtPosition(position);
                FriendTimelineActivity.actionStart(AccountActivity.this, account);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((AccountAdapter) mListView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LOGIN) {
            final AccessToken accessToken = (AccessToken) data.getSerializableExtra(LoginActivity.BUNDLE_ACCESS_TOKEN);

            mSubscription = Http.getSinaApi()
                    .getUid(accessToken.getValue())
                    .flatMap(new Func1<Uid, Observable<User>>() {
                        @Override
                        public Observable<User> call(Uid uid) {
                            GetUserInfoReqParams params = new GetUserInfoReqParams()
                                    .withAccessToken(accessToken.getValue())
                                    .withUid(uid.getValue());
                            return Http.getSinaApi().getUserInfo(HttpUtils.pojoToMap(params));
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<User>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, e.getMessage());
                        }

                        @Override
                        public void onNext(User user) {
                            Log.d(TAG, "onNext");
                            Account account = new Account();
                            account.setAccessToken(accessToken);
                            account.setUser(user);

                            mAccountList.add(account);

                            ((AccountAdapter) mListView.getAdapter()).notifyDataSetChanged();
                        }
                    });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add_account:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, REQUEST_LOGIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class AccountAdapter extends ArrayAdapter<Account> {

        public AccountAdapter(List<Account> accountList) {
            super(AccountActivity.this, 0, accountList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Account account = getItem(position);
            View view;
            ViewHolder viewHolder;

            if (convertView == null) {
                view = AccountActivity.this.getLayoutInflater().inflate(R.layout.item_account, null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.name.setText(account.getUser().getScreenName());
            viewHolder.desc.setText(account.getUser().getDescription());
            if (!account.getAccessToken().isExpired()) {
                viewHolder.tokenInfo.setVisibility(View.INVISIBLE);
            }

            Glide.with(AccountActivity.this)
                    .load(account.getUser().getProfileImageUrl())
                    .centerCrop()
                    .placeholder(R.drawable.spinner_default_holo_light_am)
                    .crossFade()
                    .into(viewHolder.avatar);

            return view;
        }

        class ViewHolder {
            @Bind(R.id.text_name)
            TextView name;
            @Bind(R.id.text_desc)
            TextView desc;
            @Bind(R.id.text_token_info)
            TextView tokenInfo;
            @Bind(R.id.img_avatar)
            ImageView avatar;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }
}
