package com.example.user.sport58;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jrummyapps.android.animations.Technique;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import cn.gavinliu.android.lib.scale.config.ScaleConfig;


public class Game_menu extends AppCompatActivity implements Runnable {
    ///宣告變數--------------------------------------------------------------------------------------------------------------////////////////
    private  int i;
    private static Activity game_menu;
    private static DatabaseReference databaseReference;
    private ProgressBar pb3;
    private static RecyclerView card;
    private ImageView bg_game;
    private game_data contacts;
    private static TextView textdate, gameid, adate2, vs, nclet, ncletpa, ncbigs, ncbigspa, nhlet, nhletpa, nhbigs, nhbigspa, sclet, scletpa, scnoletpa, scbigs, scbigspa, shlet, shletpa, shnoletpa, shbigs, shbigspa;
    private  String ball_class;
    private   String[] game_class=new String[50];
    private Thread threads;
    private static RelativeLayout tab;
    private  SearchView search;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private  RecyclerView mList;
    private static boolean bool = false;
    private static RelativeLayout game_poker;
    private   ArrayList < game_data > myDataset;
    private  Intent intent;
    private Calendar mCal;
    private CharSequence os;
    private  int mYear, mMonth, mDay;
    private DatePickerDialog datePickerDialog;
    private  Button update;
    private Toast toast;
    //------------------------------------------------------------------------------------------//
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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

        setContentView(R.layout.activity_game_menu);

        i = 0;
        setTitle("");
        Toolbar mytoobar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoobar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        //宣告元件--------------------------------------------------------------------------------------------//
        mList = (RecyclerView) findViewById(R.id.list_view);
        game_poker = (RelativeLayout) findViewById(R.id.game_poker);
        textdate = (TextView) findViewById(R.id.textView);
        textdate.setText("");
        bg_game = (ImageView) findViewById(R.id.bg_game);
        pb3 = (ProgressBar) findViewById(R.id.progressBar3);
        Button left_bn = (Button) findViewById(R.id.left_btn);
        Button right_bn = (Button) findViewById(R.id.right_btn);
         update = (Button) findViewById(R.id.update);
        search = (SearchView) findViewById(R.id.search);
        //表格元件
        tab = (RelativeLayout) findViewById(R.id.tab);
        card = (RecyclerView) findViewById(R.id.list_view);
        Button tabbn = (Button) findViewById(R.id.tabbn);
        gameid = (TextView) findViewById(R.id.gameid);
        adate2 = (TextView) findViewById(R.id.adate2);
        vs = (TextView) findViewById(R.id.vs);
        nclet = (TextView) findViewById(R.id.nclet);
        ncletpa = (TextView) findViewById(R.id.ncletpa);
        ncbigs = (TextView) findViewById(R.id.ncbigs);
        ncbigspa = (TextView) findViewById(R.id.ncbigspa);
        nhlet = (TextView) findViewById(R.id.nhlet);
        nhletpa = (TextView) findViewById(R.id.nhletpa);
        nhbigs = (TextView) findViewById(R.id.nhbigs);
        nhbigspa = (TextView) findViewById(R.id.nhbigspa);
        sclet = (TextView) findViewById(R.id.sclet);
        scletpa = (TextView) findViewById(R.id.scletpa);
        scnoletpa = (TextView) findViewById(R.id.snoletpa);
        scbigs = (TextView) findViewById(R.id.scbigs);
        scbigspa = (TextView) findViewById(R.id.scbigspa);
        shlet = (TextView) findViewById(R.id.shlet);
        shletpa = (TextView) findViewById(R.id.shletpa);
        shnoletpa = (TextView) findViewById(R.id.shnoletpa);
        shbigs = (TextView) findViewById(R.id.shbigs);
        shbigspa = (TextView) findViewById(R.id.shbigspa);
        //----------------------------------------------------------------------------------------//

        //按鈕偵聽-------------------------------------------------------------//
        left_bn.setOnClickListener(do_left_bn);
        right_bn.setOnClickListener(do_right_bn);
        tabbn.setOnClickListener(do_close);
        update.setOnClickListener(do_date);
        search.setOnQueryTextListener(do_search);
        //--------------------------------------------------------------------------------//
        mCal = Calendar.getInstance();
        mCal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        os = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime());    // kk:24小時制, hh:12小時制
        update.setText(os.subSequence(0, 10));
        game_menu = this;
         intent = getIntent();
        myDataset = new ArrayList < > ();
       game_input.MyAdapter myAdapter = new game_input.MyAdapter(myDataset);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(game_menu);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(myAdapter);
        bg_game.setVisibility(View.GONE);
        ball_class = intent.getStringExtra("ball_class");
        game_class = intent.getStringArrayExtra("game_class");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data/"+os.subSequence(0, 10)+"/"+ ball_class + "/" + game_class[i]);
        tab.setVisibility(View.GONE);
        game_poker.setVisibility(View.GONE);
        pb3.setVisibility(View.VISIBLE);
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        String use = getSharedPreferences("user", MODE_PRIVATE).getString("user", "");
        String pass = getSharedPreferences("user", MODE_PRIVATE).getString("pass", "");
        threads = new Thread(Game_menu.this);
        threads.start();

    }
    //聯賽或杯賽搜尋------------------------------------------------------------------------------------------------------------------------------//
    private final SearchView.OnQueryTextListener do_search = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {

            String game = search.getQuery().toString();
            game_poker.setBackgroundResource(R.drawable.poker_back);
            bool = false;

            bg_game.setVisibility(View.GONE);
            pb3.setVisibility(View.VISIBLE);
            card.setVisibility(View.VISIBLE);
            tab.setVisibility(View.GONE);
            game_poker.setVisibility(View.GONE);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data/"+update.getText().toString()+"/"+ ball_class + "/" + game);
            threads = new Thread(Game_menu.this);
            threads.start();
            return false;
        }
        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };
    ///--------------------------------------------------------------------------------------------------------------------------------------------------//

    //賽事日期按鈕--------------------------------------------------------------------------------------------------------------------///////
    private final Button.OnClickListener do_date = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

            showDialog(0);
            Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);
            datePickerDialog.updateDate(mYear, mMonth, mDay);
        }
    };
    ///---------------------------------------------------------------------------------------------------------------------------////


    //卡片返回按鈕----------------------------------------------------------------------------------------------------------------------//
    private final Button.OnClickListener do_close = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            //翻牌特效
            Animation animation = AnimationUtils.loadAnimation(game_menu, R.anim.back_scale);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}
                @Override
                public void onAnimationRepeat(Animation animation) {}
                @Override
                public void onAnimationEnd(Animation animation) {
                    if (bool) {
                        game_poker.setBackgroundResource(R.drawable.poker_back);
                        bool = false;
                    } else {

                        game_poker.setBackgroundResource(R.drawable.poker_front);
                        bool = true;
                    }
                    card.startAnimation(AnimationUtils.loadAnimation(game_menu, R.anim.front_scale));
                }
            });
            card.setVisibility(View.VISIBLE);
            card.startAnimation(animation);
            tab.setVisibility(View.GONE);
            game_poker.setVisibility(View.GONE);
            //
        }
    };
    ///---------------------------------------------------------------------------------------------------------------------------------------------//

    //左邊按鈕---------------------------------------------------------------------------------------------------//
    private final Button.OnClickListener do_left_bn = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (game_class.length > 1) {
                game_poker.setBackgroundResource(R.drawable.poker_back);
                bool = false;
                tab.setVisibility(View.GONE);
                game_poker.setVisibility(View.GONE);
                pb3.setVisibility(View.VISIBLE);
                bg_game.setVisibility(View.GONE);
                Technique.BOUNCE_IN_RIGHT.playOn(textdate);
                Technique.BOUNCE_IN_RIGHT.playOn(mList);
                if (i > 0) {
                    i--;
                } else {
                    i = game_class.length - 1;
                }

                databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data/"+update.getText().toString()+"/"+ ball_class + "/" + game_class[i]);
                threads = new Thread(Game_menu.this);
                threads.start();
            } else {
                Technique.BOUNCE_IN_RIGHT.playOn(textdate);
                Technique.BOUNCE_IN_RIGHT.playOn(mList);
            }
        }
    };
    //-------------------------------------------------------------------------------------------------------------------------//
    //右邊按鈕-----------------------------------------------------------------------------------------------------//
    private final Button.OnClickListener do_right_bn = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (game_class.length > 1) {
                game_poker.setBackgroundResource(R.drawable.poker_back);
                bool = false;
                tab.setVisibility(View.GONE);
                game_poker.setVisibility(View.GONE);
                pb3.setVisibility(View.VISIBLE);
                bg_game.setVisibility(View.GONE);
                Technique.BOUNCE_IN_LEFT.playOn(textdate);
                Technique.BOUNCE_IN_LEFT.playOn(mList);

                if (i < game_class.length - 1) {
                    i++;
                } else {
                    i = 0;
                }
                databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data/"+update.getText().toString()+"/"+ ball_class + "/" + game_class[i]);
                threads = new Thread(Game_menu.this);
                threads.start();
            } else {
                Technique.BOUNCE_IN_LEFT.playOn(textdate);
                Technique.BOUNCE_IN_LEFT.playOn(mList);
            }
        }
    };
    //-----------------------------------------------------------------------------------------------------------------------//

    //離開畫面會做的事----------------------------------------------------------------------------------//
    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(Game_menu.this);
        super.onDestroy();
    }
    ///-------------------------------------------------------------------------------------------------------//

    private final Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    myDataset = new ArrayList < > ();
                    myDataset.clear();
                    bg_game.setVisibility(View.GONE);
                    card.setVisibility(View.GONE);
                    pb3.setVisibility(View.GONE);
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        contacts = ds.getValue(game_data.class);
                        myDataset.add(contacts);
                    }
                    if (myDataset.isEmpty()) {
                        bg_game.setVisibility(View.VISIBLE);
                        textdate.setText("");
                        pb3.setVisibility(View.GONE);
                    } else {
                        game_input.MyAdapter myAdapter = new game_input.MyAdapter(myDataset);
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(game_menu);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        mList.setLayoutManager(layoutManager);
                        mList.setAdapter(myAdapter);
                        bg_game.setVisibility(View.GONE);
                        card.setVisibility(View.VISIBLE);
                        pb3.setVisibility(View.GONE);
                        textdate.setText(game_class[i]);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
            super.handleMessage(msg);
        }
    };
    @Override
    public void run() {

        Message m = mHandler.obtainMessage();
        m.what = 1;
        m.sendToTarget();

    }


    public static class game_input {

        public static class MyAdapter extends RecyclerView.Adapter < MyAdapter.ViewHolder > {
            private final ArrayList < game_data > mData;

            public class ViewHolder extends RecyclerView.ViewHolder {

                public final TextView home;
                public final TextView away;
                public final TextView adate;
                public final TextView ni;
                public final Button odds;
                public final TextView star;
                public ViewHolder(View v) {
                    super(v);
                    adate = v.findViewById(R.id.time_data);
                    home = v.findViewById(R.id.home);
                    away = v.findViewById(R.id.awaytitle);
                    ni = v.findViewById(R.id.ni);
                    odds = v.findViewById(R.id.odds);
                    star = v.findViewById(R.id.star);
                }
            }

            public MyAdapter(ArrayList < game_data > data) {
                mData = data;
            }

            @Override
            public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_game_menu, parent, false);
                return new ViewHolder(v);
            }

            @Override
            public void onBindViewHolder(final ViewHolder holder, final int position) {

                if (String_time.time(mData.get(position).getAdate()) == 1) {
                    holder.star.setText("未開賽");
                } else {
                    if (mData.get(position).getStatus().matches("completed")) {
                        holder.star.setText("已完賽");
                    } else {
                        holder.star.setText("已開賽");
                    }
                }
                holder.home.setText("(主)" + mData.get(position).getHome());
                holder.away.setText("(客)" + mData.get(position).getAway());
                holder.ni.setText(mData.get(position).getGameid());
                holder.adate.setText(mData.get(position).getAdate());
                holder.odds.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        game_poker.setVisibility(View.VISIBLE);
                        tab.setVisibility(View.VISIBLE);
                        card.setVisibility(View.GONE);
                        //翻牌特效
                        Animation animation = AnimationUtils.loadAnimation(game_menu, R.anim.back_scale);

                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {}

                            @Override
                            public void onAnimationRepeat(Animation animation) {}

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if (bool) {

                                    game_poker.setBackgroundResource(R.drawable.poker_back);
                                    bool = false;
                                } else {

                                    game_poker.setBackgroundResource(R.drawable.poker_front);
                                    bool = true;
                                }
                                //通过AnimationUtils得到动画配置文件(/res/anim/front_scale.xml),然后在把动画交给ImageView

                                game_poker.startAnimation(AnimationUtils.loadAnimation(game_menu, R.anim.front_scale));
                                tab.startAnimation(AnimationUtils.loadAnimation(game_menu, R.anim.front_scale));

                            }
                        });
                        game_poker.startAnimation(animation);
                        tab.startAnimation(animation);

                        //
                        gameid.setText(mData.get(position).getGameid());
                        adate2.setText(mData.get(position).getAdate());
                        vs.setText("(客) " + mData.get(position).getAway() + " " + "v.s" + " " + mData.get(position).getHome() + " (主)");
                        nclet.setText(mData.get(position).getNclet());
                        ncletpa.setText(mData.get(position).getNcletpa());
                        ncbigs.setText(mData.get(position).getNcbigs());
                        ncbigspa.setText(mData.get(position).getNcbigspa());
                        nhlet.setText(mData.get(position).getNhlet());
                        nhletpa.setText(mData.get(position).getNhletpa());
                        nhbigs.setText(mData.get(position).getNhbigs());
                        nhbigspa.setText(mData.get(position).getNhbigspa());
                        sclet.setText(mData.get(position).getSclet());
                        scletpa.setText(mData.get(position).getScletpa());
                        scnoletpa.setText(mData.get(position).getScnotletpa());
                        scbigs.setText(mData.get(position).getScbigs());
                        scbigspa.setText(mData.get(position).getScbigspa());
                        shlet.setText(mData.get(position).getShlet());
                        shletpa.setText(mData.get(position).getShletpa());
                        shnoletpa.setText(mData.get(position).getShnotletpa());
                        shbigs.setText(mData.get(position).getShbigs());
                        shbigspa.setText(mData.get(position).getShbigspa());

                    }
                });

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
                bool = false;
                databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data2/" + "/" +setDateFormat(year, month, day)+"/"+ball_class);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int j = 0;
                        if ((int) dataSnapshot.getChildrenCount() != 0) {
                            game_class = new String[(int) dataSnapshot.getChildrenCount()];
                            for (DataSnapshot ds: dataSnapshot.getChildren()) {
                                game_class[j]="";
                                game_class[j] = ds.getValue().toString();
                                j++;
                            }
                            update.setText(setDateFormat(mYear, mMonth, mDay));
                            Technique.BOUNCE.playOn(textdate);
                            Technique.BOUNCE_IN_UP.playOn(mList);
                            i=0;
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data/"+update.getText().toString()+"/"+ ball_class + "/" + game_class[i]);
                            threads = new Thread(Game_menu.this);
                            threads.start();

                        } else {
                            update.setText(setDateFormat(mYear, mMonth, mDay));
                            game_class[j] = " ";
                            i=0;
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data/"+update.getText().toString()+"/"+ ball_class + "/" + game_class[i]);
                            threads = new Thread(Game_menu.this);
                            threads.start();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


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