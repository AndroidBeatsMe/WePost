package com.nancyberry.wepost.ui.account;

import android.content.Context;

import com.nancyberry.wepost.support.model.AccessToken;
import com.nancyberry.wepost.support.model.Account;
import com.nancyberry.wepost.support.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by nan.zhang on 3/29/16.
 */
public class AccountLab {
    private static final String TAG = AccountLab.class.getSimpleName();
    private static AccountLab sAccountLab;
    private Context mAppContext;
    private List<Account> mAccountList;
    
    private AccountLab(Context context) {
        mAppContext = context;
        mAccountList = new ArrayList<>();
    }

    public static AccountLab get(Context context) {
        if (sAccountLab == null) {
            sAccountLab = new AccountLab(context.getApplicationContext());
        }

        // TODO: find a better way to store accounts
        Account fakeAccount = new Account();
        User fakeUser = new User();
        fakeUser.setProfileImageUrl("http://tp1.sinaimg.cn/5812295980/50/5746319708/0");
        fakeUser.setScreenName("虾米南瓜球");
        fakeUser.setDescription("hehe");
        fakeAccount.setUser(fakeUser);
        AccessToken fakeToken = new AccessToken();
        fakeToken.setAccessTokenStr("2.003hn22Gu_WsZC293476e02b072ZTF");
        fakeToken.setExpiresIn(624579);
        fakeAccount.setAccessToken(fakeToken);
        sAccountLab.addAccount(fakeAccount);

        return sAccountLab;
    }

    public void addAccount(Account account) {
        mAccountList.add(account);
    }

    public List<Account> getAccountList() {
        return mAccountList;
    }

    public Account getAccount(UUID uuid) {
        if (uuid == null) {
            return null;
        }

        for (Account account : mAccountList) {
            if (uuid.equals(account.getId())) {
                return account;
            }
        }

        return null;
    }
}
