package com.example.user.sport58;

import android.text.format.DateFormat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.TimeZone;


class MemberPredctionData {
    //寫入會員預測資料
    public static void AddData(String pr_title,String pr_away,String pr_home,String pr_adate,String pr_aclass2,String ni,String pr_game_id,String tab_ball_class,String sport58_user,String pr_nclet,String pr_ncletpa,String pr_ncbigs,String pr_ncbigspa,String pr_nhlet,String pr_nhletpa,String pr_nhbigs,String pr_nhbigspa,String pr_sclet,String pr_scletpa,String pr_scnoletpa,String pr_scbigs,String pr_scbigspa,String pr_shlet,String pr_shletpa,String pr_shnoletpa,String pr_shbigs,String pr_shbigspa)
    {  String time="";
        Calendar mCal = Calendar.getInstance();
        mCal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        CharSequence os = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime());    // kk:24小時制, hh:12小時制
        for(int i=0;i<10;i++)
        {

            time=time+os.subSequence(i,i+1);
        }
        DatabaseReference databaseReference = null;
        databaseReference= FirebaseDatabase.getInstance().getReference().child("member_predction/"+sport58_user+"/"+time+"/"+tab_ball_class+"/");
        SavePredctionData savePredctionData=new SavePredctionData(pr_title,pr_away,pr_home,pr_adate,pr_aclass2,ni,pr_game_id,sport58_user,pr_nclet, pr_ncletpa, pr_ncbigs, pr_ncbigspa, pr_nhlet, pr_nhletpa, pr_nhbigs, pr_nhbigspa, pr_sclet, pr_scletpa, pr_scnoletpa, pr_scbigs, pr_scbigspa, pr_shlet, pr_shletpa, pr_shnoletpa, pr_shbigs, pr_shbigspa);
        databaseReference.push().setValue(savePredctionData);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("member_predction_count/"+pr_adate+"/");
        SavePredctionDataCount savePredctionDataCount=new SavePredctionDataCount(sport58_user);
        databaseReference.push().setValue(savePredctionDataCount);
    }
////





}
