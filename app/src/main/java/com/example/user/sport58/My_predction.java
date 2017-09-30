package com.example.user.sport58;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jrummyapps.android.animations.Technique;
import com.wooplr.spotlight.utils.SpotlightSequence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import cn.gavinliu.android.lib.scale.config.ScaleConfig;

public class My_predction extends AppCompatActivity implements Runnable {
    private  String sport58_user;
    private  DatabaseReference databaseReference;
    private  String[] baseball = new String[50];
    private  String[] basketball = new String[50];
    private  String[] football = new String[50];
    private  String[] usafootball = new String[50];
    private  String[] tennis = new String[50];
    private  String[] iceball = new String[50];
    private  String[] delet_baseball = new String[50];
    private  String[] delet_basketball = new String[50];
    private  String[] delet_football = new String[50];
    private  String[] delet_usafootball = new String[50];
    private  String[] delet_tennis = new String[50];
    private  String[] delet_iceball = new String[50];
    private  ImageView img_top, img_line, img_end, my_predction_nogame;
    private  TextView textview_top, textview_end;
    private  Button baseball_bn, basketball_bn, football_bn, tennis_bn, iceball_bn, usafootball_bn;
    private member_predction contacts;
    private  LinearLayoutManager layoutManager;
    private  predction_input.MyAdapter myAdapter;
    private  Activity my_predction;
    private  RecyclerView mList;
    private  RelativeLayout btngrop, predction_tab;
    private  int mYear, mMonth, mDay;
    private DatePickerDialog datePickerDialog;
    private  String ball;
    private  String time = "";
    private   ArrayList < member_predction > myDataset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //自適屏布局始量化
        ScaleConfig.create(this,
                1080, // Design Width
                1920, // Design Height
                3,    // Design Density
                3,    // Design FontScale
                ScaleConfig.DIMENS_UNIT_DP);
        setContentView(R.layout.activity_my_predction);

        Toolbar mytoobar = (Toolbar) findViewById(R.id.toolbar9);
        setSupportActionBar(mytoobar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        my_predction = this;
        setTitle("我的預測");
        btngrop = (RelativeLayout) findViewById(R.id.btngrop);
        mList = (RecyclerView) findViewById(R.id.predction_recyclerview);
        predction_tab = (RelativeLayout) findViewById(R.id.predction_tab);
        my_predction_nogame = (ImageView) findViewById(R.id.my_predction_nogame);
        img_top = (ImageView) findViewById(R.id.img_top);
        img_line = (ImageView) findViewById(R.id.img_line);
        img_end = (ImageView) findViewById(R.id.img_end);
        textview_top = (TextView) findViewById(R.id.textView19);
        textview_end = (TextView) findViewById(R.id.textView30);
        baseball_bn = (Button) findViewById(R.id.baseball_bn);
        basketball_bn = (Button) findViewById(R.id.basketball_bn);
        football_bn = (Button) findViewById(R.id.football_bn);
        tennis_bn = (Button) findViewById(R.id.tennis_bn);
        iceball_bn = (Button) findViewById(R.id.iceball_bn);
        usafootball_bn = (Button) findViewById(R.id.usafootball_bn);
        img_top.setOnClickListener(do_date);
        baseball_bn.setOnClickListener(do_baseball);
        basketball_bn.setOnClickListener(do_basketball);
        football_bn.setOnClickListener(do_football);
        tennis_bn.setOnClickListener(do_tennis);
        iceball_bn.setOnClickListener(do_iceball);
        usafootball_bn.setOnClickListener(do_usafootball);
        img_end.setOnClickListener(do_back);
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        String use = getSharedPreferences("user", MODE_PRIVATE).getString("user", "");
        String pass = getSharedPreferences("user", MODE_PRIVATE).getString("pass", "");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        my_predction_nogame.setVisibility(View.GONE);
        img_end.setVisibility(View.GONE);
        textview_end.setVisibility(View.GONE);

        //初始化Recyclerview
        myDataset = new ArrayList < > ();
        predction_input.MyAdapter myAdapter = new predction_input.MyAdapter(myDataset);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(my_predction);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(myAdapter);
        //

        boardRunnable h2 = new boardRunnable();
        Thread thr2 = new Thread(h2);
        thr2.start();
        if (user != null) {
            sport58_user = user.getUid();
        }
        if (!use.equals("") && !pass.equals("")) {
            sport58_user = use;
        }

        //多線程
        baseballRunnable h1 = new baseballRunnable();
        Thread thr = new Thread(h1);
        thr.start();
        basketballRunnable h3 = new basketballRunnable();
        Thread thr3 = new Thread(h3);
        thr3.start();
        footballRunnable h4 = new footballRunnable();
        Thread thr4 = new Thread(h4);
        thr4.start();
        tennisRunnable h5 = new tennisRunnable();
        Thread thr5 = new Thread(h5);
        thr5.start();
        iceballRunnable h6 = new iceballRunnable();
        Thread thr6 = new Thread(h6);
        thr6.start();
        usafootballRunnable h7 = new usafootballRunnable();
        Thread thr7 = new Thread(h7);
        thr7.start();
        //



    }

    //按鈕方法-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    private final ImageView.OnClickListener do_date = new Button.OnClickListener() {
        public void onClick(View view) {
            if (textview_top.getText().toString().matches("我的預測")) {} else {
                showDialog(0);
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog.updateDate(mYear, mMonth, mDay);
            }
        }
    };

    private final Button.OnClickListener do_baseball = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

            time = "";
            Calendar mCal = Calendar.getInstance();
            mCal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            CharSequence os = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime()); // kk:24小時制, hh:12小時制
            for (int i = 0; i < 10; i++) {

                time = time + os.subSequence(i, i + 1);
            }
            ball = "棒球";
            databaseReference = FirebaseDatabase.getInstance().getReference().child("member_predction/" + sport58_user + "/" + time + "/" + ball);
            textview_top.setText(time);
            img_line.setVisibility(View.GONE);
            btngrop.setVisibility(View.GONE);
            img_end.setVisibility(View.VISIBLE);
            textview_end.setVisibility(View.VISIBLE);
            Technique.BOUNCE_IN_UP.playOn(textview_top);
            Technique.BOUNCE_IN_UP.playOn(predction_tab);
            Technique.BOUNCE_IN_UP.playOn(img_end);
            Technique.BOUNCE_IN_UP.playOn(textview_end);
            my_predctionRunnable game = new my_predctionRunnable();
            Thread thr = new Thread(game);
            thr.start();
        }
    };

    private final Button.OnClickListener do_basketball = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {



            time = "";
            Calendar mCal = Calendar.getInstance();
            mCal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            CharSequence os = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime()); // kk:24小時制, hh:12小時制
            for (int i = 0; i < 10; i++) {
                time = time + os.subSequence(i, i + 1);
            }
            ball = "籃球";
            databaseReference = FirebaseDatabase.getInstance().getReference().child("member_predction/" + sport58_user + "/" + time + "/" + ball);
            textview_top.setText(time);
            img_line.setVisibility(View.GONE);
            btngrop.setVisibility(View.GONE);

            img_end.setVisibility(View.VISIBLE);
            textview_end.setVisibility(View.VISIBLE);
            Technique.BOUNCE_IN_UP.playOn(textview_top);
            Technique.BOUNCE_IN_UP.playOn(predction_tab);
            Technique.BOUNCE_IN_UP.playOn(img_end);
            Technique.BOUNCE_IN_UP.playOn(textview_end);
            my_predctionRunnable game = new my_predctionRunnable();
            Thread thr = new Thread(game);
            thr.start();

        }
    };
    private final Button.OnClickListener do_football = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {


            time = "";
            Calendar mCal = Calendar.getInstance();
            mCal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            CharSequence os = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime()); // kk:24小時制, hh:12小時制
            for (int i = 0; i < 10; i++) {

                time = time + os.subSequence(i, i + 1);
            }
            ball = "足球";
            databaseReference = FirebaseDatabase.getInstance().getReference().child("member_predction/" + sport58_user + "/" + time + "/" + ball);
            textview_top.setText(time);
            img_line.setVisibility(View.GONE);
            btngrop.setVisibility(View.GONE);

            img_end.setVisibility(View.VISIBLE);
            textview_end.setVisibility(View.VISIBLE);
            Technique.BOUNCE_IN_UP.playOn(textview_top);
            Technique.BOUNCE_IN_UP.playOn(predction_tab);
            Technique.BOUNCE_IN_UP.playOn(img_end);
            Technique.BOUNCE_IN_UP.playOn(textview_end);

            my_predctionRunnable game = new my_predctionRunnable();
            Thread thr = new Thread(game);
            thr.start();

        }
    };
    private final Button.OnClickListener do_tennis = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {


            time = "";
            Calendar mCal = Calendar.getInstance();
            mCal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            CharSequence os = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime()); // kk:24小時制, hh:12小時制
            for (int i = 0; i < 10; i++) {

                time = time + os.subSequence(i, i + 1);
            }
            ball = "網球";
            databaseReference = FirebaseDatabase.getInstance().getReference().child("member_predction/" + sport58_user + "/" + time + "/" + ball);
            textview_top.setText(time);
            img_line.setVisibility(View.GONE);
            btngrop.setVisibility(View.GONE);

            img_end.setVisibility(View.VISIBLE);
            textview_end.setVisibility(View.VISIBLE);
            Technique.BOUNCE_IN_UP.playOn(textview_top);
            Technique.BOUNCE_IN_UP.playOn(predction_tab);
            Technique.BOUNCE_IN_UP.playOn(img_end);
            Technique.BOUNCE_IN_UP.playOn(textview_end);
            my_predctionRunnable game = new my_predctionRunnable();
            Thread thr = new Thread(game);
            thr.start();

        }
    };
    private final Button.OnClickListener do_iceball = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {


            time = "";
            Calendar mCal = Calendar.getInstance();
            mCal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            CharSequence os = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime()); // kk:24小時制, hh:12小時制
            for (int i = 0; i < 10; i++) {

                time = time + os.subSequence(i, i + 1);
            }
            ball = "冰球";
            databaseReference = FirebaseDatabase.getInstance().getReference().child("member_predction/" + sport58_user + "/" + time + "/" + ball);
            textview_top.setText(time);
            img_line.setVisibility(View.GONE);
            btngrop.setVisibility(View.GONE);

            img_end.setVisibility(View.VISIBLE);
            textview_end.setVisibility(View.VISIBLE);
            Technique.BOUNCE_IN_UP.playOn(textview_top);
            Technique.BOUNCE_IN_UP.playOn(predction_tab);
            Technique.BOUNCE_IN_UP.playOn(img_end);
            Technique.BOUNCE_IN_UP.playOn(textview_end);
            my_predctionRunnable game = new my_predctionRunnable();
            Thread thr = new Thread(game);
            thr.start();

        }
    };
    private final Button.OnClickListener do_usafootball = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {



            time = "";
            Calendar mCal = Calendar.getInstance();
            mCal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            CharSequence os = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime()); // kk:24小時制, hh:12小時制
            for (int i = 0; i < 10; i++) {
                time = time + os.subSequence(i, i + 1);
            }
            ball = "美式足球";
            databaseReference = FirebaseDatabase.getInstance().getReference().child("member_predction/" + sport58_user + "/" + time + "/" + ball);
            textview_top.setText(time);
            img_line.setVisibility(View.GONE);
            btngrop.setVisibility(View.GONE);

            img_end.setVisibility(View.VISIBLE);
            textview_end.setVisibility(View.VISIBLE);
            Technique.BOUNCE_IN_UP.playOn(textview_top);
            Technique.BOUNCE_IN_UP.playOn(predction_tab);
            Technique.BOUNCE_IN_UP.playOn(img_end);
            Technique.BOUNCE_IN_UP.playOn(textview_end);
            my_predctionRunnable game = new my_predctionRunnable();
            Thread thr = new Thread(game);
            thr.start();

        }
    };
    private final ImageView.OnClickListener do_back = new ImageView.OnClickListener() {
        @Override
        public void onClick(View view) {



            textview_top.setText("我的預測");
            textview_end.setVisibility(View.GONE);
            img_end.setVisibility(View.GONE);
            my_predction_nogame.setVisibility(View.GONE);
            mList.setVisibility(View.GONE);
            img_line.setVisibility(View.VISIBLE);
            btngrop.setVisibility(View.VISIBLE);
            Technique.BOUNCE_IN_DOWN.playOn(img_line);
            Technique.BOUNCE_IN_DOWN.playOn(textview_top);
            Technique.BOUNCE_IN_DOWN.playOn(predction_tab);
        }
    };

    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    //離開後回收線程-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(My_predction.this);
        super.onDestroy();
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    //UI線程-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    @Override
    public void run() {

    }
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    //-/會員預測資料多線程載入------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    private class baseballRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("member_predction/" + sport58_user + "/" + time + "棒球");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    if ((int) dataSnapshot.getChildrenCount() != 0) {
                        baseball = new String[(int) dataSnapshot.getChildrenCount()];
                        delet_baseball = new String[(int) dataSnapshot.getChildrenCount()];
                        for (DataSnapshot ds: dataSnapshot.getChildren()) {
                            baseball[i] = ds.getValue().toString();
                            delet_baseball[i] = ds.getKey();
                            i++;
                        }
                    } else {
                        baseball[i] = " ";
                        delet_baseball[i] = "";
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //
        }
    }
    private class basketballRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("member_predction/" + sport58_user + "/" + time + "籃球");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    if ((int) dataSnapshot.getChildrenCount() != 0) {
                        basketball = new String[(int) dataSnapshot.getChildrenCount()];
                        delet_basketball = new String[(int) dataSnapshot.getChildrenCount()];
                        for (DataSnapshot ds: dataSnapshot.getChildren()) {
                            basketball[i] = ds.getValue().toString();
                            delet_basketball[i] = ds.getKey();
                            i++;
                        }
                    } else {
                        basketball[i] = " ";
                        delet_basketball[i] = "";
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //
        }
    }
    private class footballRunnable implements Runnable {
        public void run() {
            DatabaseReference databaseReference;
            databaseReference = FirebaseDatabase.getInstance().getReference().child("member_predction/" + sport58_user + "/" + time + "足球");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    if ((int) dataSnapshot.getChildrenCount() != 0) {
                        football = new String[(int) dataSnapshot.getChildrenCount()];
                        delet_football = new String[(int) dataSnapshot.getChildrenCount()];
                        for (DataSnapshot ds: dataSnapshot.getChildren()) {
                            football[i] = ds.getValue().toString();
                            delet_football[i] = ds.getKey();
                            i++;
                        }
                    } else {
                        football[i] = " ";
                        delet_football[i] = "";
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //

        }
    }
    private class tennisRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("member_predction/" + sport58_user + "/" + time + "網球");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    if (dataSnapshot.getChildrenCount() != 0) {
                        tennis = new String[(int) dataSnapshot.getChildrenCount()];
                        delet_tennis = new String[(int) dataSnapshot.getChildrenCount()];
                        for (DataSnapshot ds: dataSnapshot.getChildren()) {
                            tennis[i] = ds.getValue().toString();
                            delet_tennis[i] = ds.getKey();
                            i++;
                        }
                    } else {
                        tennis[i] = "";
                        delet_tennis[i] = "";
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //
        }
    }
    private class iceballRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("member_predction/" + sport58_user + "/" + time + "冰球");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;

                    if ((int) dataSnapshot.getChildrenCount() != 0) {
                        iceball = new String[(int) dataSnapshot.getChildrenCount()];
                        delet_iceball = new String[(int) dataSnapshot.getChildrenCount()];
                        for (DataSnapshot ds: dataSnapshot.getChildren()) {
                            iceball[i] = ds.getValue().toString();
                            delet_iceball[i] = ds.getKey();
                            i++;
                        }
                    } else {
                        iceball[i] = " ";
                        delet_iceball[i] = "";
                    }


                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
            //
        }
    }
    private class usafootballRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("member_predction/" + sport58_user + "/" + time + "美式足球");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    if ((int) dataSnapshot.getChildrenCount() != 0) {
                        usafootball = new String[(int) dataSnapshot.getChildrenCount()];
                        delet_usafootball = new String[(int) dataSnapshot.getChildrenCount()];
                        for (DataSnapshot ds: dataSnapshot.getChildren()) {
                            usafootball[i] = ds.getValue().toString();
                            delet_usafootball[i] = ds.getKey();
                            i++;
                        }
                    } else {
                        usafootball[i] = " ";
                        delet_usafootball[i] = "";
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //
        }
    }
    //
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    //-布告欄動畫線程------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    private class boardRunnable implements Runnable {
        public void run() {
            Message m = mHandler.obtainMessage();
            m.what = 1;
            m.sendToTarget();
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    //-布告欄動畫線程-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    private final Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                Technique.STAND_UP.playOn(img_top);
                Technique.STAND_UP.playOn(textview_top);
                Technique.BOUNCE_IN_LEFT.playOn(baseball_bn);
                Technique.BOUNCE_IN_RIGHT.playOn(basketball_bn);
                Technique.BOUNCE_IN_LEFT.playOn(football_bn);
                Technique.BOUNCE_IN_RIGHT.playOn(tennis_bn);
                Technique.BOUNCE_IN_LEFT.playOn(iceball_bn);
                Technique.BOUNCE_IN_RIGHT.playOn(usafootball_bn);
            }
            super.handleMessage(msg);
        }
    };
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    private class my_predctionRunnable implements Runnable {
        public void run() {

            databaseReference.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    final ArrayList < member_predction > myDataset = new ArrayList < > ();
                    myDataset.clear();
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        contacts = ds.getValue(member_predction.class);
                        myDataset.add(contacts);
                    }

                    if (myDataset.isEmpty()) {
                        my_predction_nogame.setVisibility(View.VISIBLE);
                        mList.setVisibility(View.GONE);
                        Technique.BOUNCE_IN_UP.playOn(my_predction_nogame);
                    } else {
                        mList.setVisibility(View.VISIBLE);
                        myAdapter = new predction_input.MyAdapter(myDataset);
                        layoutManager = new LinearLayoutManager(my_predction);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        mList.setLayoutManager(layoutManager);
                        mList.setAdapter(myAdapter);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }
    }


    public static class predction_input {
        public static class MyAdapter extends RecyclerView.Adapter < predction_input.MyAdapter.ViewHolder > {
            private final ArrayList < member_predction > mData;

            public class ViewHolder extends RecyclerView.ViewHolder {
                private final TextView pr_top_title;
                private final TextView pr_mid_vs;
                private final TextView pr_end_pa;


                public ViewHolder(View v) {
                    super(v);
                    pr_top_title = v.findViewById(R.id.pr_top_title);
                    pr_mid_vs = v.findViewById(R.id.pr_mid_vs);
                    pr_end_pa = v.findViewById(R.id.pr_end_pa);
                }
            }

            public MyAdapter(ArrayList < member_predction > data) {
                mData = data;
            }

            @Override
            public predction_input.MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_my_predction, parent, false);
                return new MyAdapter.ViewHolder(v);
            }
            @Override
            public void onBindViewHolder(predction_input.MyAdapter.ViewHolder holder, int position) {

            }
            @Override
            public void onBindViewHolder(final predction_input.MyAdapter.ViewHolder holder, final int position, final List payloads) {
                holder.setIsRecyclable(false);
                holder.pr_top_title.setText(mData.get(position).getPr_game_id());
                holder.pr_mid_vs.setText(mData.get(position).getPr_away() + " vs " + mData.get(position).getPr_home());

                if (mData.get(position).getPr_title().matches("國際讓分客")) {
                    holder.pr_end_pa.setText("國際讓分  " + mData.get(position).getPr_away() + "  " + mData.get(position).getPr_nclet() + "  " + mData.get(position).getPr_ncletpa());
                } else {
                    if (mData.get(position).getPr_title().matches("國際讓分主")) {
                        holder.pr_end_pa.setText("國際讓分  " + mData.get(position).getPr_home() + "  " + mData.get(position).getPr_nhlet() + "  " + mData.get(position).getPr_nhletpa());
                    } else {
                        if (mData.get(position).getPr_title().matches("國際大小大")) {
                            holder.pr_end_pa.setText("國際大小  大 " + mData.get(position).getPr_ncbigs() + "  " + mData.get(position).getPr_ncbigspa());
                        } else {
                            if (mData.get(position).getPr_title().matches("國際大小小")) {
                                holder.pr_end_pa.setText("國際大小  小 " + mData.get(position).getPr_nhbigs() + "  " + mData.get(position).getPr_nhbigspa());
                            } else {
                                if (mData.get(position).getPr_title().matches("運彩讓分客")) {
                                    holder.pr_end_pa.setText("運彩讓分  " + mData.get(position).getPr_away() + "  " + mData.get(position).getPr_sclet() + "  " + mData.get(position).getPr_scletpa());
                                } else {
                                    if (mData.get(position).getPr_title().matches("運彩讓分主")) {
                                        holder.pr_end_pa.setText("運彩讓分  " + mData.get(position).getPr_home() + "  " + mData.get(position).getPr_shlet() + "  " + mData.get(position).getPr_shletpa());
                                    } else {
                                        if (mData.get(position).getPr_title().matches("運彩不讓分客")) {
                                            holder.pr_end_pa.setText("運彩不讓分  " + mData.get(position).getPr_away() + "  " + mData.get(position).getPr_scnoletpa());
                                        } else {
                                            if (mData.get(position).getPr_title().matches("運彩不讓分主")) {
                                                holder.pr_end_pa.setText("運彩不讓分  " + mData.get(position).getPr_home() + "  " + mData.get(position).getPr_shnoletpa());
                                            } else {
                                                if (mData.get(position).getPr_title().matches("運彩大小大")) {
                                                    holder.pr_end_pa.setText("運彩大小  大 " + mData.get(position).getPr_scbigs() + "  " + mData.get(position).getPr_scbigspa());
                                                } else {
                                                    holder.pr_end_pa.setText("運彩大小  小 " + mData.get(position).getPr_shbigs() + "  " + mData.get(position).getPr_shbigspa());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }



            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        }

    }
    ///日期選擇-----------------------------------------------------------------------------------------------------------------------------------------------------//
    @Override
    protected Dialog onCreateDialog(int id) {
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month,
                                  int day) {
                mYear = year;
                mMonth = month;
                mDay = day;
                databaseReference = FirebaseDatabase.getInstance().getReference().child("member_predction/" + sport58_user + "/" + setDateFormat(year, month, day) + "/" + ball);
                textview_top.setText(setDateFormat(year, month, day));
                img_line.setVisibility(View.GONE);
                btngrop.setVisibility(View.GONE);
                my_predction_nogame.setVisibility(View.GONE);
                img_end.setVisibility(View.VISIBLE);
                textview_end.setVisibility(View.VISIBLE);
                Technique.BOUNCE_IN_UP.playOn(textview_top);
                Technique.BOUNCE_IN_UP.playOn(predction_tab);
                Technique.BOUNCE_IN_UP.playOn(img_end);
                Technique.BOUNCE_IN_UP.playOn(textview_end);
                my_predctionRunnable game = new my_predctionRunnable();
                Thread thr = new Thread(game);
                thr.start();
            }

        }, mYear, mMonth, mDay);

        return datePickerDialog;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------//
    //日期補零------------------------------------------------------------------------------------------------------//
    private String setDateFormat(int year, int monthOfYear, int dayOfMonth) {
        if ((monthOfYear + 1) < 10 && dayOfMonth < 10) {
            return String.valueOf(year) + "-0" + String.valueOf(monthOfYear + 1) + "-0" + String.valueOf(dayOfMonth);
        } else {
            if ((monthOfYear + 1) >= 10 && dayOfMonth >= 10) {
                return String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);
            } else {
                if ((monthOfYear + 1) <= 10 && dayOfMonth >= 10) {
                    return String.valueOf(year) + "-0" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);
                } else {
                    return String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-0" + String.valueOf(dayOfMonth);
                }
            }
        }
    }
    //---------------------------------------------------------------------------------------------------------//
}