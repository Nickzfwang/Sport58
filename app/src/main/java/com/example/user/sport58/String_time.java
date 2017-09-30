package com.example.user.sport58;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.TimeZone;

/**
 *處理時間字串分割&比對&計算
 */

public class String_time {
    public static int time(String adate) {
        Calendar mCal = Calendar.getInstance();
        mCal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        CharSequence os = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime());    // kk:24小時制, hh:12小時制
if(adate!=null&&!adate.matches("")) {
    int os_yaer = Integer.parseInt((String) os.subSequence(0, 4));
    int os_mon = Integer.parseInt((String) os.subSequence(5, 7));
    int os_day = Integer.parseInt((String) os.subSequence(8, 10));
    int os_hr = Integer.parseInt((String) os.subSequence(11, 13));
    int os_min = Integer.parseInt((String) os.subSequence(14, 16));
    int game_yaer = Integer.parseInt((String) adate.subSequence(0, 4));
    int game_mon = Integer.parseInt((String) adate.subSequence(5, 7));
    int game_day = Integer.parseInt((String) adate.subSequence(8, 10));
    int game_hr = Integer.parseInt((String) adate.subSequence(11, 13));
    int game_min = Integer.parseInt((String) adate.subSequence(14, 16));
    if (os_yaer <= game_yaer) {
        if (os_mon <= game_mon) {
            if (os_day <= game_day) {
                if (os_hr <= game_hr || os_day < game_day) {
                    if (os_min < game_min || os_day < game_day || os_hr < game_hr) {
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    } else {
        return 0;
    }
}
else{return 0;}
    }
}
