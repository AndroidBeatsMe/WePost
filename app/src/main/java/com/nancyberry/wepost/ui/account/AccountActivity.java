package com.nancyberry.wepost.ui.account;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nancyberry.wepost.R;
import com.nancyberry.wepost.support.bean.AccessToken;
import com.nancyberry.wepost.support.bean.Account;
import com.nancyberry.wepost.support.bean.User;
import com.nancyberry.wepost.ui.login.LoginActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nan.zhang on 3/29/16.
 */
public class AccountActivity extends ListActivity {
    public static final String TAG = AccountActivity.class.getSimpleName();
    public static final int REQUEST_LOGIN = 1;
    private List<Account> mAccountList;
    private AccountAdapter mAccountAdapter;

    public static String ACCOUNT_GET_UID_ = "https://api.weibo.com/2/account/get_uid.json";
    public static String USERS_SHOW = "https://api.weibo.com/2/users/show.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.account_page_title);
        setContentView(R.layout.activity_account);
        mAccountList = AccountLab.get(this).getAccountList();
        mAccountAdapter = new AccountAdapter(mAccountList);
        setListAdapter(mAccountAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((AccountAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LOGIN) {
            String accessTokenStr = data.getStringExtra(LoginActivity.BUNDLE_ACCESS_TOKEN);
            Long expireIn = data.getLongExtra(LoginActivity.BUNDLE_EXPIRE_IN, -1);
            AccessToken accessToken = new AccessToken();
            accessToken.setAccessToken(accessTokenStr);
            accessToken.setExpiresIn(expireIn);

            try {
                String userId = new FetchUidTask().execute(accessTokenStr).get();
                accessToken.setUserId(userId);
                String[] params = new String[2];
                params[0] = accessTokenStr;
                params[1] = userId;
                String jsonData = new FetchUserProfileTask().execute(params).get();
                User user = parseUser(jsonData);
                Account account = new Account();
                account.setAccessToken(accessToken);
                account.setUser(user);

                mAccountList.add(account);

                ((AccountAdapter) getListAdapter()).notifyDataSetChanged();

            } catch (Exception e) {

            }
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

    private class AccountAdapter extends ArrayAdapter<Account> {

        public AccountAdapter(List<Account> accountList) {
            super(AccountActivity.this, 0, accountList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = AccountActivity.this.getLayoutInflater().inflate(R.layout.item_account, null);
            }

            Account account = getItem(position);

            TextView nameTextView = (TextView) convertView.findViewById(R.id.text_name);
            TextView descTextView = (TextView) convertView.findViewById(R.id.text_desc);
            TextView tokenInfoTextView = (TextView) convertView.findViewById(R.id.text_token_info);
            ImageView avatarImage = (ImageView) convertView.findViewById(R.id.img_avatar);

            nameTextView.setText(account.getUser().getScreen_name());
            descTextView.setText(account.getUser().getDescription());
            if (!account.getAccessToken().isExpired()) {
                tokenInfoTextView.setVisibility(View.INVISIBLE);
            }

            try {
                Bitmap avatar = new ImageDownloadTask().execute(account.getUser().getProfile_image_url()).get();
                avatarImage.setImageBitmap(avatar);
            } catch (InterruptedException ie) {
                Log.e(TAG, ie.getMessage());
            } catch (ExecutionException ee) {
                Log.e(TAG, ee.getMessage());
            }

            return convertView;
        }

    }

    public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(params[0]).addHeader("Content-Type", "application/json").build();
            try {
                Response response = client.newCall(request).execute();
                InputStream is = response.body().byteStream();
                return BitmapFactory.decodeStream(is);
            } catch (IOException ie) {
                Log.e(TAG, ie.getMessage());
                return null;
            }
        }
    }

    public class FetchUidTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            HttpUrl url = new HttpUrl.Builder()
                    .scheme("https")
                    .host("api.weibo.com")
                    .addPathSegments("2/account/get_uid.json")
                    .addQueryParameter("access_token", params[0])
                    .build();
            Log.d(TAG, "get uid url = " + url.toString());

            Request request = new Request.Builder().url(url).build();

            try {
                Response response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonData);
                String uid = jsonObject.getString("uid");
                Log.d(TAG, jsonObject.toString());

                return uid;

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                return null;
            }
        }
    }


    public class FetchUserProfileTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            HttpUrl url = new HttpUrl.Builder()
                    .scheme("https")
                    .host("api.weibo.com")
                    .addPathSegments("2/users/show.json")
                    .addQueryParameter("access_token", params[0])
                    .addQueryParameter("uid", params[1])
                    .build();
            Request request = new Request.Builder().url(url).build();

            Log.d(TAG, "user show url = " + url.toString());

            try {
                Response response = client.newCall(request).execute();
                String jsonData = response.body().string();
                Log.d(TAG, new JSONObject(jsonData).toString());

                return jsonData;

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                return null;
            }
        }
    }

    public User parseUser(String jsonString) {
        User user = new User();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            user.setId(jsonObject.getString("id"));
            user.setScreen_name(jsonObject.getString("screen_name"));
            user.setDescription(jsonObject.getString("description"));
            user.setProfile_image_url(jsonObject.getString("profile_image_url"));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return user;
    }

}
