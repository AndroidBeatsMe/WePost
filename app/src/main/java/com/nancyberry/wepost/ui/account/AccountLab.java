package com.nancyberry.wepost.ui.account;

import android.content.Context;

import com.nancyberry.wepost.support.bean.Account;

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

//        Account fakeAccount = new Account();
//        User fakeUser = new User();
//        fakeUser.setProfile_image_url("http://tp1.sinaimg.cn/5812295980/50/5746319708/0");
//        fakeUser.setScreen_name("虾米南瓜球");
//        fakeUser.setDescription("hehe");
//        fakeAccount.setUser(fakeUser);
//        AccessToken fakeToken = new AccessToken();
//        fakeToken.setAccessToken("2.003hn22GaV8NND49e6fe7d00DwTiAD");
//        fakeAccount.setAccessToken(fakeToken);
//
//        sAccountLab.addAccount(fakeAccount);

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
