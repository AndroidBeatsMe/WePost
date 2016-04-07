package com.nancyberry.wepost.common.util;

import android.content.Context;
import android.content.res.Resources;

import com.nancyberry.wepost.R;
import com.nancyberry.wepost.common.context.GlobalContext;
import com.nancyberry.wepost.support.model.User;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by nan.zhang on 4/7/16.
 */
public class StringUtils {
//    @SuppressWarnings("deprecation")
    public static String formatDate(String time) {
        Context context = GlobalContext.getInstance();
        Resources res = context.getResources();

        StringBuffer buffer = new StringBuffer();

        Calendar createCal = Calendar.getInstance();
        createCal.setTimeInMillis(Date.parse(time));
        Calendar currentcal = Calendar.getInstance();
        currentcal.setTimeInMillis(System.currentTimeMillis());

        long diffTime = (currentcal.getTimeInMillis() - createCal.getTimeInMillis()) / 1000;

        // 同一月
        if (currentcal.get(Calendar.MONTH) == createCal.get(Calendar.MONTH)) {
            // 同一天
            if (currentcal.get(Calendar.DAY_OF_MONTH) == createCal.get(Calendar.DAY_OF_MONTH)) {
                if (diffTime < 3600 && diffTime >= 60) {
                    buffer.append((diffTime / 60) + res.getString(R.string.msg_few_minutes_ago));
                } else if (diffTime < 60) {
                    buffer.append(res.getString(R.string.msg_now));
                } else {
                    buffer.append(res.getString(R.string.msg_today)).append(" ").append(DateUtils.formatDate(createCal.getTimeInMillis(), "HH:mm"));
                }
            }
            // 前一天
            else if (currentcal.get(Calendar.DAY_OF_MONTH) - createCal.get(Calendar.DAY_OF_MONTH) == 1) {
                buffer.append(res.getString(R.string.msg_yesterday)).append(" ").append(DateUtils.formatDate(createCal.getTimeInMillis(), "HH:mm"));
            }
        }

        if (buffer.length() == 0) {
            buffer.append(DateUtils.formatDate(createCal.getTimeInMillis(), "MM-dd HH:mm"));
        }

        String timeStr = buffer.toString();
        if (currentcal.get(Calendar.YEAR) != createCal.get(Calendar.YEAR)) {
            timeStr = createCal.get(Calendar.YEAR) + " " + timeStr;
        }
        return timeStr;
    }


    public static String getGender(User user) {
        Resources res = GlobalContext.getInstance().getResources();
        if (user != null) {
            if ("m".equals(user.getGender())) {
                return res.getString(R.string.msg_male);
            } else if ("f".equals(user.getGender())) {
                return res.getString(R.string.msg_female);
            } else if ("n".equals(user.getGender())) {
                return res.getString(R.string.msg_gender_unknow);
            }
        }
        return "";
    }
}
