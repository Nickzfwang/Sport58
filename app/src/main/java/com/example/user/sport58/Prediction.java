package com.example.user.sport58;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.jrummyapps.android.animations.Technique;
import com.wooplr.spotlight.utils.SpotlightSequence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import cn.gavinliu.android.lib.scale.ScaleRelativeLayout;
import es.dmoral.toasty.Toasty;

public class Prediction extends AppCompatActivity implements Runnable {
    //變數宣告---------------------------------------------------------------------------------------------------------------//
    private  String[] baseball = new String[50];
    private  String[] basketball = new String[50];
    private  String[] football = new String[50];
    private  String[] usafootball = new String[50];
    private  String[] tennis = new String[50];
    private  String[] iceball = new String[50];
    private  String[] delet = new String[50];
    private static RadioGroup RG1, RG2, RG3, RG4, RG5;
    private static RadioButton L1, L2, L3, L4, L5, R1, R2, R3, R4, R5;
    private static TextView nclet, ncletpa, ncbigs, ncbigspa, nhlet, nhletpa, nhbigs, nhbigspa, sclet, scletpa, scnotletpa, scbigs, scbigspa, shlet, shletpa, shnoletpa, shbigs, shbigspa;
    private static String pr_title, pr_home, pr_away, pr_adate, pr_aclass2, pr_ni, pr_game_id, pr_nclet, pr_ncletpa, pr_ncbigs, pr_ncbigspa, pr_nhlet, pr_nhletpa, pr_nhbigs, pr_nhbigspa, pr_sclet, pr_scletpa, pr_scnotletpa, pr_scbigs, pr_scbigspa, pr_shlet, pr_shletpa, pr_shnoletpa, pr_shbigs, pr_shbigspa;
    private static int i = 0;
    private static int j = 0;
    private  String[] game = baseball;
    private static RecyclerView mList;
    private game_data contacts;
    private static boolean[] bool = new boolean[50];
    private static boolean bool2 = false;
    private static boolean bool_use = false;
    private static boolean L1_bool = false, L2_bool = false, L3_bool = false, L4_bool = false, L5_bool = false, R1_bool = false, R2_bool = false, R3_bool = false, R4_bool = false, R5_bool = false;
    private TextView title;
    private static TextView game_id;
    private static RelativeLayout tab_low, poker,recycler_Realative;
    private static String tab_ball_class;
    private static Toast toast;
    private static Activity predction;
    private static DatabaseReference databaseReference;
    private ProgressBar pb8;
    private  LinearLayoutManager layoutManager;
    private ImageView predction_nogame;
    private static Thread threads;
    private static String sport58_user;
    private  game_input.MyAdapter myAdapter;
    private   ArrayList < game_data > myDataset;
    private Calendar mCal;
    private CharSequence os;
    private  int mYear, mMonth, mDay;
    private HorizontalScrollView ballScrollView;
    private ScaleRelativeLayout select;
    //---------------------------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);
        mCal = Calendar.getInstance();
        mCal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        os = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime());    // kk:24小時制, hh:12小時制
        Toolbar mytoobar = (Toolbar) findViewById(R.id.toolbar7);
        setSupportActionBar(mytoobar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        String use = getSharedPreferences("user", MODE_PRIVATE).getString("user", "");
        String pass = getSharedPreferences("user", MODE_PRIVATE).getString("pass", "");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        predction = this;
        setTitle("預測");

        //
        //元件的宣告-------------------------------------------------------------------------------------------------------/////
        recycler_Realative=(RelativeLayout)findViewById(R.id.recycler_Realative);
        select=(ScaleRelativeLayout)findViewById(R.id.select);
        ballScrollView=(HorizontalScrollView)findViewById(R.id.ballScrollView);
        game_id = (TextView) findViewById(R.id.game_id);
        poker = (RelativeLayout) findViewById(R.id.poker);
        tab_low = (RelativeLayout) findViewById(R.id.tab_low);
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
        scnotletpa = (TextView) findViewById(R.id.snoletpa);
        scbigs = (TextView) findViewById(R.id.scbigs);
        scbigspa = (TextView) findViewById(R.id.scbigspa);
        shlet = (TextView) findViewById(R.id.shlet);
        shletpa = (TextView) findViewById(R.id.shletpa);
        shnoletpa = (TextView) findViewById(R.id.shnoletpa);
        shbigs = (TextView) findViewById(R.id.shbigs);
        shbigspa = (TextView) findViewById(R.id.shbigspa);
        predction_nogame = (ImageView) findViewById(R.id.predction_nogame);
        pb8 = (ProgressBar) findViewById(R.id.progressBar8);
        title = (TextView) findViewById(R.id.title);
        mList = (RecyclerView) findViewById(R.id.recycler_view);
        Button bn_left = (Button) findViewById(R.id.left_btn);
        Button bn_right = (Button) findViewById(R.id.right_btn);
        bn_left.setOnClickListener(do_left);
        bn_right.setOnClickListener(do_right);
        RG1 = (RadioGroup) findViewById(R.id.RG1);
        RG2 = (RadioGroup) findViewById(R.id.RG2);
        RG3 = (RadioGroup) findViewById(R.id.RG3);
        RG4 = (RadioGroup) findViewById(R.id.RG4);
        RG5 = (RadioGroup) findViewById(R.id.RG5);
        L1 = (RadioButton) findViewById(R.id.L1);
        L2 = (RadioButton) findViewById(R.id.L2);
        L3 = (RadioButton) findViewById(R.id.L3);
        L4 = (RadioButton) findViewById(R.id.L4);
        L5 = (RadioButton) findViewById(R.id.L5);
        R1 = (RadioButton) findViewById(R.id.R1);
        R2 = (RadioButton) findViewById(R.id.R2);
        R3 = (RadioButton) findViewById(R.id.R3);
        R4 = (RadioButton) findViewById(R.id.R4);
        R5 = (RadioButton) findViewById(R.id.R5);
        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);
        Button btn3 = (Button) findViewById(R.id.btn3);
        Button btn4 = (Button) findViewById(R.id.btn4);
        Button btn5 = (Button) findViewById(R.id.btn5);
        Button btn6 = (Button) findViewById(R.id.btn6);
        ////----------------------------------------------------------------------------------------------////
        ////球種選擇按鈕偵聽---------------------------------------------------------////
        btn1.setOnClickListener(do_baseball);
        btn2.setOnClickListener(do_basketball);
        btn3.setOnClickListener(do_football);
        btn4.setOnClickListener(do_usafootball);
        btn5.setOnClickListener(do_tennis);
        btn6.setOnClickListener(do_iceball);
        ///----------------------------------------------------------------////////////
        ////賽事預測取消點擊按鈕偵聽--------------------------------------////
        L1.setOnClickListener(do_L1);
        L2.setOnClickListener(do_L2);
        L3.setOnClickListener(do_L3);
        L4.setOnClickListener(do_L4);
        L5.setOnClickListener(do_L5);
        R1.setOnClickListener(do_R1);
        R2.setOnClickListener(do_R2);
        R3.setOnClickListener(do_R3);
        R4.setOnClickListener(do_R4);
        R5.setOnClickListener(do_R5);
        ///------------------------------------------------------------------////
        title.setText("請選擇球類");
        pb8.setVisibility(View.GONE);
        RG1.clearCheck();
        RG2.clearCheck();
        RG3.clearCheck();
        RG4.clearCheck();
        RG5.clearCheck();
        ////判斷是否登入----------------------------------------------------------------------///////////////////
        sport58_user = null;

        if (user != null) {
            sport58_user = user.getUid();
        }
        if (!use.equals("") && !pass.equals("")) {
            sport58_user = use;
        }

        /////-------------------------------------------------------------------------------------------/////

        //初始化Recyclerview---------------------------------------//
        myDataset = new ArrayList < > ();
        game_input.MyAdapter myAdapter = new game_input.MyAdapter(myDataset);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(predction);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(myAdapter);
       // --------------------------------------------------------------//

        //賽事多線程
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

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {


                SpotlightSequence.getInstance(Prediction.this,null)
                        .addSpotlight(ballScrollView, "球類", "選擇要預測的球類", "7")
                        .addSpotlight(select,"聯賽及盃賽", "這裡可以選擇您要預測的聯賽或盃賽", "8")
                        .addSpotlight(recycler_Realative,"預測", "這裡可以選擇您想預測的賽事", "9")
                        .addSpotlight(poker, "賠率及大小", "這裡可以選擇客隊&主隊的賠率及大小", "10")
                        .startSequence();
            }
        },100);



    }

    //-/會員預測資料多線程載入------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    //棒球線程--------------------------------------------------------------------------------------------------------------------------------////
    private class baseballRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data2/"+os.subSequence(0, 10)+"/棒球");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    if ((int) dataSnapshot.getChildrenCount() != 0) {
                        baseball = new String[(int) dataSnapshot.getChildrenCount()];

                        for (DataSnapshot ds: dataSnapshot.getChildren()) {
                            baseball[i] = ds.getValue().toString();

                            i++;
                        }
                    } else {
                        baseball[i] = " ";

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //
        }
    }
    ////--------------------------------------------------------------------------------------------------------------------------------------////
    ///籃球線程-----------------------------------------------------------------------------------------------------------------------////////////
    private class basketballRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data2/"+os.subSequence(0, 10)+"/籃球");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    if ((int) dataSnapshot.getChildrenCount() != 0) {
                        basketball = new String[(int) dataSnapshot.getChildrenCount()];

                        for (DataSnapshot ds: dataSnapshot.getChildren()) {
                            basketball[i] = ds.getValue().toString();

                            i++;
                        }
                    } else {
                        basketball[i] = " ";

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //
        }
    }
    ///----------------------------------------------------------------------------------------------------------------------------------------////
    ////足球線程---------------------------------------------------------------------------------------------------------------------------------------////
    private class footballRunnable implements Runnable {
        public void run() {
            DatabaseReference databaseReference;
            databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data2/"+os.subSequence(0, 10)+"/足球");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    if ((int) dataSnapshot.getChildrenCount() != 0) {
                        football = new String[(int) dataSnapshot.getChildrenCount()];

                        for (DataSnapshot ds: dataSnapshot.getChildren()) {
                            football[i] = ds.getValue().toString();

                            i++;
                        }
                    } else {
                        football[i] = " ";

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //

        }
    }
    ///------------------------------------------------------------------------------------------------------------------------//
    //網球線程-------------------------------------------------------------------------------------------------------------//
    private class tennisRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data2/"+os.subSequence(0, 10)+"/網球");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    if (dataSnapshot.getChildrenCount() != 0) {
                        tennis = new String[(int) dataSnapshot.getChildrenCount()];

                        for (DataSnapshot ds: dataSnapshot.getChildren()) {
                            tennis[i] = ds.getValue().toString();

                            i++;
                        }
                    } else {
                        tennis[i] = "";

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //
        }
    }
    ///冰球線程----------------------------------------------------------------------------------------------------------------------------------///
    private class iceballRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data2/"+os.subSequence(0, 10)+"/冰球");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;

                    if ((int) dataSnapshot.getChildrenCount() != 0) {
                        iceball = new String[(int) dataSnapshot.getChildrenCount()];

                        for (DataSnapshot ds: dataSnapshot.getChildren()) {
                            iceball[i] = ds.getValue().toString();

                            i++;
                        }
                    } else {
                        iceball[i] = " ";

                    }


                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
            //
        }
    }
    ///-------------------------------------------------------------------------------------------------------------------------------------------------///
    //美式足球線程----------------------------------------------------------------------------------------------------------------------------------//
    private class usafootballRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data2/"+os.subSequence(0, 10)+"/美式足球");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    if ((int) dataSnapshot.getChildrenCount() != 0) {
                        usafootball = new String[(int) dataSnapshot.getChildrenCount()];
                        for (DataSnapshot ds: dataSnapshot.getChildren()) {
                            usafootball[i] = ds.getValue().toString();
                            i++;
                        }
                    } else {
                        usafootball[i] = " ";

                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //
        }
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------//

    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    ///左選按鈕-------------------------------------------------------------------------------------------------------------//
    private final Button.OnClickListener do_left = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (game.length > 1 && !title.getText().toString().matches("請選擇球類")) {
                Technique.BOUNCE_IN_RIGHT.playOn(title);
                Technique.BOUNCE_IN_RIGHT.playOn(mList);
                if (toast != null) {
                    toast.cancel();
                } //讓訊息不要延遲
                poker.setBackgroundResource(R.drawable.poker_back);
                bool2 = false;
                game_id.setText("");
                mList.setVisibility(View.INVISIBLE);
                pb8.setVisibility(View.VISIBLE);
                predction_nogame.setVisibility(View.GONE);
                if (i > 0) {
                    i--;
                    title.setText(game[i]);
                } else {
                    i = game.length - 1;
                    title.setText(game[i]);
                }
                databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data/"+os.subSequence(0, 10)+"/"+ tab_ball_class + "/" + game[i]);
                threads = new Thread(Prediction.this);
                threads.start();
            } else {
                Technique.BOUNCE_IN_RIGHT.playOn(title);
                Technique.BOUNCE_IN_RIGHT.playOn(mList);

            }
        }
    };
    ///-------------------------------------------------------------------------------------------------------------//

    //右選按鈕----------------------------------------------------------------------------------------//
    private final Button.OnClickListener do_right = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (game.length > 1 && !title.getText().toString().matches("請選擇球類")) {
                Technique.BOUNCE_IN_LEFT.playOn(title);
                Technique.BOUNCE_IN_LEFT.playOn(mList);
                if (toast != null) {
                    toast.cancel();
                } //讓訊息不要延遲
                poker.setBackgroundResource(R.drawable.poker_back);
                bool2 = false;
                game_id.setText("");
                mList.setVisibility(View.INVISIBLE);
                pb8.setVisibility(View.VISIBLE);
                predction_nogame.setVisibility(View.GONE);
                if (i < game.length - 1) {
                    i++;
                    title.setText(game[i]);
                } else {
                    i = 0;
                    title.setText(game[i]);
                }
                databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data/"+os.subSequence(0, 10)+"/" + tab_ball_class + "/" + game[i]);
                threads = new Thread(Prediction.this);
                threads.start();
            } else {
                Technique.BOUNCE_IN_LEFT.playOn(title);
                Technique.BOUNCE_IN_LEFT.playOn(mList);
            }
        }
    };
    //------------------------------------------------------------------------------------------------------------//


    //球種按鈕------------------------------------------------------------------------------------------------//
    private final Button.OnClickListener do_baseball = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Technique.BOUNCE_IN.playOn(mList);
            if (toast != null) {
                toast.cancel();
            } //讓訊息不要延遲
            poker.setBackgroundResource(R.drawable.poker_back);
            bool2 = false;
            game_id.setText("");
            predction_nogame.setVisibility(View.GONE);
            pb8.setVisibility(View.VISIBLE);
            mList.setVisibility(View.GONE);
            i = 0;
            tab_ball_class = "棒球";
            game = baseball;
            threads = new Thread(Prediction.this);
            threads.start();
            title.setText(game[i]);
        }
    };
    private final Button.OnClickListener do_basketball = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Technique.BOUNCE_IN.playOn(mList);
            if (toast != null) {
                toast.cancel();
            } //讓訊息不要延遲
            poker.setBackgroundResource(R.drawable.poker_back);
            bool2 = false;
            game_id.setText("");
            predction_nogame.setVisibility(View.GONE);
            pb8.setVisibility(View.VISIBLE);
            mList.setVisibility(View.GONE);

            i = 0;

            tab_ball_class = "籃球";
            game = basketball;
            bool = new boolean[basketball.length];

            threads = new Thread(Prediction.this);
            threads.start();
            title.setText(game[i]);
        }
    };
    private final Button.OnClickListener do_football = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Technique.BOUNCE_IN.playOn(mList);
            if (toast != null) {
                toast.cancel();
            } //讓訊息不要延遲
            poker.setBackgroundResource(R.drawable.poker_back);
            bool2 = false;
            game_id.setText("");
            predction_nogame.setVisibility(View.GONE);
            pb8.setVisibility(View.VISIBLE);
            mList.setVisibility(View.GONE);

            i = 0;
            tab_ball_class = "足球";
            game = football;
            bool = new boolean[football.length];

            threads = new Thread(Prediction.this);
            threads.start();
            title.setText(game[i]);


        }
    };
    private final Button.OnClickListener do_usafootball = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Technique.BOUNCE_IN.playOn(mList);
            if (toast != null) {
                toast.cancel();
            } //讓訊息不要延遲
            poker.setBackgroundResource(R.drawable.poker_back);
            bool2 = false;
            game_id.setText("");
            predction_nogame.setVisibility(View.GONE);
            pb8.setVisibility(View.VISIBLE);
            mList.setVisibility(View.GONE);

            i = 0;
            tab_ball_class = "美式足球";
            game = usafootball;
            bool = new boolean[usafootball.length];

            threads = new Thread(Prediction.this);
            threads.start();
            title.setText(game[i]);


        }
    };
    private final Button.OnClickListener do_tennis = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Technique.BOUNCE_IN.playOn(mList);
            if (toast != null) {
                toast.cancel();
            } //讓訊息不要延遲
            poker.setBackgroundResource(R.drawable.poker_back);
            bool2 = false;
            game_id.setText("");
            predction_nogame.setVisibility(View.GONE);
            pb8.setVisibility(View.VISIBLE);
            mList.setVisibility(View.GONE);

            i = 0;
            tab_ball_class = "網球";
            game = tennis;
            bool = new boolean[tennis.length];

            threads = new Thread(Prediction.this);
            threads.start();
            title.setText(game[i]);

        }
    };
    private final Button.OnClickListener do_iceball = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Technique.BOUNCE_IN.playOn(mList);
            if (toast != null) {
                toast.cancel();
            } //讓訊息不要延遲
            poker.setBackgroundResource(R.drawable.poker_back);
            bool2 = false;
            game_id.setText("");
            predction_nogame.setVisibility(View.GONE);
            pb8.setVisibility(View.VISIBLE);
            mList.setVisibility(View.GONE);

            i = 0;
            tab_ball_class = "冰球";
            game = iceball;
            bool = new boolean[iceball.length];

            threads = new Thread(Prediction.this);
            threads.start();
            title.setText(game[i]);
        }
    };
    ///-------------------------------------------------------------------------------------------------------------------//

    public static class game_input {
        public static class MyAdapter extends RecyclerView.Adapter < game_input.MyAdapter.ViewHolder > {
            private final ArrayList < game_data > mData;

            public class ViewHolder extends RecyclerView.ViewHolder {

                private final TextView vs_up;
                private final TextView vs_under;
                private final TextView vs_date;
                private final TextView vs_predction;
                private final CardView card_predction;
                private final Button vs_send;
                private final Button vs_cancle;
                private final RelativeLayout card2;
                private final RelativeLayout card1;

                public ViewHolder(View v) {
                    super(v);
                    vs_up = v.findViewById(R.id.vs_up);
                    vs_under = v.findViewById(R.id.vs_under);
                    vs_date = v.findViewById(R.id.vs_date);
                    vs_predction = v.findViewById(R.id.vs_predction);
                    card_predction = v.findViewById(R.id.card_predction);
                    card1 = v.findViewById(R.id.card1);
                    card2 = v.findViewById(R.id.card2);
                    vs_send = v.findViewById(R.id.vs_send);
                    vs_cancle = v.findViewById(R.id.vs_cancle);
                    card1.setVisibility(View.GONE);
                }
            }

            public MyAdapter(ArrayList < game_data > data) {
                mData = data;
            }

            @Override
            public game_input.MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_predction, parent, false);
                return new game_input.MyAdapter.ViewHolder(v);
            }

            @Override
            public void onBindViewHolder(game_input.MyAdapter.ViewHolder holder, int position) {

            }

            @Override
            public void onBindViewHolder(final game_input.MyAdapter.ViewHolder holder, final int position, final List payloads) {
                holder.setIsRecyclable(false);
                holder.vs_up.setText(mData.get(position).getAway());
                holder.vs_under.setText(mData.get(position).getHome());
                holder.vs_date.setText(mData.get(position).getAdate());

                holder.vs_predction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        RG1.clearCheck();
                        L1_bool = false;
                        R1_bool = false;
                        RG2.clearCheck();
                        L2_bool = false;
                        R2_bool = false;
                        RG3.clearCheck();
                        L3_bool = false;
                        R3_bool = false;
                        RG4.clearCheck();
                        L4_bool = false;
                        R4_bool = false;
                        RG5.clearCheck();
                        L5_bool = false;
                        R5_bool = false;
                        RG1.clearCheck();
                        R1_bool = false;
                        L1_bool = false;
                        RG2.clearCheck();
                        R2_bool = false;
                        L2_bool = false;
                        RG3.clearCheck();
                        R3_bool = false;
                        L3_bool = false;
                        RG4.clearCheck();
                        R4_bool = false;
                        L4_bool = false;
                        RG5.clearCheck();
                        R5_bool = false;
                        L5_bool = false;

                        String time = "";
                        Calendar mCal = Calendar.getInstance();
                        mCal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                        CharSequence os = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime()); // kk:24小時制, hh:12小時制
                        for (int i = 0; i < 10; i++) {
                            time = time + os.subSequence(i, i + 1);
                        }

                        databaseReference = FirebaseDatabase.getInstance().getReference().child("member_predction/" + sport58_user + "/" + time + "/" + tab_ball_class + "/");

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot ds: dataSnapshot.getChildren()) {

                                        if (ds.child("ni").getValue().toString()!=null&&ds.child("ni").getValue().toString().matches(mData.get(position).getNi())) {
                                            bool_use = true;
                                            break;
                                        }
                                    }
                                    if (!bool_use) {
                                        if (sport58_user == null) {
                                            if (toast != null) {
                                                toast.cancel();
                                            } //讓訊息不要延遲
                                            Toasty.warning(predction, "預測賽事請先登入!", Toast.LENGTH_SHORT, true).show();

                                            bool_use = false;

                                        } else {

                                            if(String_time.time(mData.get(position).getAdate())==0&&mData.get(position).getAdate().toString()!=null)
                                            {
                                                Toasty.warning(predction, "此場賽事已過可預測時間", Toast.LENGTH_SHORT, true).show();
                                            }
                                            else{
                                                if (!bool2) {
                                                    //禁止滑動
                                                    mList.setOnTouchListener(new View.OnTouchListener() {
                                                        @Override
                                                        public boolean onTouch(View v, MotionEvent event) {
                                                            return true;
                                                        }
                                                    });
                                                    //
                                                    mList.scrollToPosition(position);
                                                    RG1.clearCheck();
                                                    RG2.clearCheck();
                                                    RG3.clearCheck();
                                                    RG4.clearCheck();
                                                    RG5.clearCheck();
                                                    bool_use = false;
                                                    bool2 = true;
                                                    game_id.setText(mData.get(position).getGameid());
                                                    Technique.BOUNCE_IN.playOn(game_id);
                                                    //禁止滑動
                                                    mList.setOnTouchListener(new View.OnTouchListener() {
                                                        @Override
                                                        public boolean onTouch(View v, MotionEvent event) {
                                                            return true;
                                                        }
                                                    });
                                                    //
                                                    //翻牌特效
                                                    Animation animation = AnimationUtils.loadAnimation(predction, R.anim.back_scale);
                                                    animation.setAnimationListener(new Animation.AnimationListener() {
                                                        @Override
                                                        public void onAnimationStart(Animation animation) {}

                                                        @Override
                                                        public void onAnimationRepeat(Animation animation) {}

                                                        @Override
                                                        public void onAnimationEnd(Animation animation) {
                                                            if (bool[position]) {
                                                                holder.card1.setBackgroundResource(R.drawable.card_front);
                                                                poker.setBackgroundResource(R.drawable.poker_back);
                                                                bool[position] = false;
                                                            } else {
                                                                holder.card1.setBackgroundResource(R.drawable.card_back);
                                                                poker.setBackgroundResource(R.drawable.poker_front);
                                                                bool[position] = true;
                                                            }

                                                            holder.card1.startAnimation(AnimationUtils.loadAnimation(predction, R.anim.front_scale));
                                                            holder.card_predction.startAnimation(AnimationUtils.loadAnimation(predction, R.anim.front_scale));
                                                            poker.startAnimation(AnimationUtils.loadAnimation(predction, R.anim.front_scale));
                                                            tab_low.startAnimation(AnimationUtils.loadAnimation(predction, R.anim.front_scale));

                                                        }
                                                    });
                                                    holder.card2.setVisibility(View.GONE);
                                                    holder.card1.setVisibility(View.VISIBLE);
                                                    tab_low.setVisibility(View.VISIBLE);
                                                    holder.card1.startAnimation(animation);
                                                    holder.card_predction.startAnimation(animation);
                                                    poker.startAnimation(animation);
                                                    tab_low.startAnimation(animation);

                                                    //

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
                                                    scnotletpa.setText(mData.get(position).getScnotletpa());
                                                    scbigs.setText(mData.get(position).getScbigs());
                                                    scbigspa.setText(mData.get(position).getScbigspa());
                                                    shlet.setText(mData.get(position).getShlet());
                                                    shletpa.setText(mData.get(position).getShletpa());
                                                    shnoletpa.setText(mData.get(position).getShnotletpa());
                                                    shbigs.setText(mData.get(position).getShbigs());
                                                    shbigspa.setText(mData.get(position).getShbigspa());

                                                } else {
                                                    if (toast != null) {
                                                        toast.cancel();
                                                    } //讓訊息不要延遲
                                                    Toasty.warning(predction, "已有賽場正在預測!", Toast.LENGTH_SHORT, true).show();

                                                }
                                            }
                                        }
                                    } else {

                                        if (toast != null) {
                                            toast.cancel();
                                        } //讓訊息不要延遲
                                        Toasty.warning(predction, "您已預測過此賽事!", Toast.LENGTH_SHORT, true).show();

                                        bool_use = false;
                                    }
                                } else {
                                    if (sport58_user == null) {
                                        if (toast != null) {
                                            toast.cancel();
                                        } //讓訊息不要延遲
                                        Toasty.warning(predction, "預測賽事請先登入!", Toast.LENGTH_SHORT, true).show();

                                        bool_use = false;
                                    } else {


                                        if(String_time.time(mData.get(position).getAdate())==0)
                                        {
                                            Toasty.warning(predction, "此場賽事已過可預測時間", Toast.LENGTH_SHORT, true).show();

                                        }
                                        else{

                                            if (!bool2) {
                                                //禁止滑動
                                                mList.setOnTouchListener(new View.OnTouchListener() {
                                                    @Override
                                                    public boolean onTouch(View v, MotionEvent event) {
                                                        return true;
                                                    }
                                                });
                                                //
                                                mList.scrollToPosition(position);
                                                RG1.clearCheck();
                                                RG2.clearCheck();
                                                RG3.clearCheck();
                                                RG4.clearCheck();
                                                RG5.clearCheck();
                                                bool_use = false;
                                                bool2 = true;
                                                game_id.setText(mData.get(position).getGameid());
                                                Technique.BOUNCE_IN.playOn(game_id);

                                                //翻牌特效
                                                Animation animation = AnimationUtils.loadAnimation(predction, R.anim.back_scale);
                                                animation.setAnimationListener(new Animation.AnimationListener() {
                                                    @Override
                                                    public void onAnimationStart(Animation animation) {}

                                                    @Override
                                                    public void onAnimationRepeat(Animation animation) {}

                                                    @Override
                                                    public void onAnimationEnd(Animation animation) {
                                                        if (bool[position]) {
                                                            holder.card1.setBackgroundResource(R.drawable.card_front);
                                                            poker.setBackgroundResource(R.drawable.poker_back);
                                                            bool[position] = false;
                                                        } else {
                                                            holder.card1.setBackgroundResource(R.drawable.card_back);
                                                            poker.setBackgroundResource(R.drawable.poker_front);
                                                            bool[position] = true;
                                                        }

                                                        holder.card1.startAnimation(AnimationUtils.loadAnimation(predction, R.anim.front_scale));
                                                        holder.card_predction.startAnimation(AnimationUtils.loadAnimation(predction, R.anim.front_scale));
                                                        poker.startAnimation(AnimationUtils.loadAnimation(predction, R.anim.front_scale));
                                                        tab_low.startAnimation(AnimationUtils.loadAnimation(predction, R.anim.front_scale));

                                                    }
                                                });
                                                holder.card2.setVisibility(View.GONE);
                                                holder.card1.setVisibility(View.VISIBLE);
                                                tab_low.setVisibility(View.VISIBLE);
                                                holder.card1.startAnimation(animation);
                                                holder.card_predction.startAnimation(animation);
                                                poker.startAnimation(animation);
                                                tab_low.startAnimation(animation);

                                                //

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
                                                scnotletpa.setText(mData.get(position).getScnotletpa());
                                                scbigs.setText(mData.get(position).getScbigs());
                                                scbigspa.setText(mData.get(position).getScbigspa());
                                                shlet.setText(mData.get(position).getShlet());
                                                shletpa.setText(mData.get(position).getShletpa());
                                                shnoletpa.setText(mData.get(position).getShnotletpa());
                                                shbigs.setText(mData.get(position).getShbigs());
                                                shbigspa.setText(mData.get(position).getShbigspa());

                                            } else {
                                                if (toast != null) {
                                                    toast.cancel();
                                                } //讓訊息不要延遲
                                                Toasty.warning(predction, "已有賽場正在預測!", Toast.LENGTH_SHORT, true).show();
                                            }

                                        }




                                    }
                                }




                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



                    }
                });

                holder.vs_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //啟動滑動
                        mList.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        //
                        game_id.setText("");
                        //翻牌特效
                        Animation animation = AnimationUtils.loadAnimation(predction, R.anim.back_scale);

                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {}

                            @Override
                            public void onAnimationRepeat(Animation animation) {}

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if (bool[position]) {
                                    holder.card1.setBackgroundResource(R.drawable.card_back);
                                    poker.setBackgroundResource(R.drawable.poker_back);
                                    bool[position] = false;
                                } else {
                                    holder.card1.setBackgroundResource(R.drawable.card_front);
                                    poker.setBackgroundResource(R.drawable.poker_front);
                                    bool[position] = true;
                                }

                                holder.card1.startAnimation(AnimationUtils.loadAnimation(predction, R.anim.front_scale));
                                holder.card_predction.startAnimation(AnimationUtils.loadAnimation(predction, R.anim.front_scale));
                                poker.startAnimation(AnimationUtils.loadAnimation(predction, R.anim.front_scale));
                                tab_low.startAnimation(AnimationUtils.loadAnimation(predction, R.anim.front_scale));
                            }
                        });
                        tab_low.setVisibility(View.GONE);
                        holder.card1.setVisibility(View.GONE);
                        holder.card2.setVisibility(View.VISIBLE);
                        holder.card1.startAnimation(animation);
                        holder.card_predction.startAnimation(animation);
                        poker.startAnimation(animation);
                        tab_low.startAnimation(animation);
                        //
                        bool2 = false;

                    }
                });

                holder.vs_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!L1.isChecked() && !L2.isChecked() && !L3.isChecked() && !L4.isChecked() && !L5.isChecked() && !R1.isChecked() && !R2.isChecked() && !R3.isChecked() && !R4.isChecked() && !R5.isChecked()) {
                            if (toast != null) {
                                toast.cancel();
                            } //讓訊息不要延遲
                            Toasty.warning(predction, "至少選一種!", Toast.LENGTH_SHORT, true).show();
                        } else {
                            //啟動滑動
                            mList.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    return false;
                                }
                            });
                            //

                            pr_title = "";
                            pr_nclet = "";
                            pr_ncletpa = "";
                            pr_ncbigs = "";
                            pr_ncbigspa = "";
                            pr_nhlet = "";
                            pr_nhletpa = "";
                            pr_nhbigs = "";
                            pr_nhbigspa = "";
                            pr_sclet = "";
                            pr_scletpa = "";
                            pr_scnotletpa = "";
                            pr_scbigs = "";
                            pr_scbigspa = "";
                            pr_shlet = "";
                            pr_shletpa = "";
                            pr_shnoletpa = "";
                            pr_shbigs = "";
                            pr_shbigspa = "";

                            pr_game_id = mData.get(position).getGameid();
                            Technique.BOUNCE_IN.playOn(game_id);
                            pr_ni = mData.get(position).getNi();
                            pr_aclass2 = mData.get(position).getAclass2();
                            pr_adate = mData.get(position).getAdate();
                            pr_home = mData.get(position).getHome();
                            pr_away = mData.get(position).getAway();

                            if (L1.isChecked()) {
                                pr_nclet = mData.get(position).getNclet();
                                pr_ncletpa = mData.get(position).getNcletpa();
                                pr_title = "國際讓分客";
                                if (!pr_nclet.matches("") && !pr_ncletpa.matches("")) {
                                    MemberPredctionData.AddData(pr_title, pr_away, pr_home, pr_adate, pr_aclass2, pr_ni, pr_game_id, tab_ball_class, sport58_user, pr_nclet, pr_ncletpa, pr_ncbigs, pr_ncbigspa, pr_nhlet, pr_nhletpa, pr_nhbigs, pr_nhbigspa, pr_sclet, pr_scletpa, pr_scnotletpa, pr_scbigs, pr_scbigspa, pr_shlet, pr_shletpa, pr_shnoletpa, pr_shbigs, pr_shbigspa);

                                    pr_title = "";
                                    pr_nclet = "";
                                    pr_ncletpa = "";
                                    pr_ncbigs = "";
                                    pr_ncbigspa = "";
                                    pr_nhlet = "";
                                    pr_nhletpa = "";
                                    pr_nhbigs = "";
                                    pr_nhbigspa = "";
                                    pr_sclet = "";
                                    pr_scletpa = "";
                                    pr_scnotletpa = "";
                                    pr_scbigs = "";
                                    pr_scbigspa = "";
                                    pr_shlet = "";
                                    pr_shletpa = "";
                                    pr_shnoletpa = "";
                                    pr_shbigs = "";
                                    pr_shbigspa = "";
                                }
                            }

                            if (R1.isChecked()) {
                                pr_nhlet = mData.get(position).getNhlet();
                                pr_nhletpa = mData.get(position).getNhletpa();
                                pr_title = "國際讓分主";
                                if (!pr_nhlet.matches("") && !pr_nhletpa.matches("")) {
                                    MemberPredctionData.AddData(pr_title, pr_away, pr_home, pr_adate, pr_aclass2, pr_ni, pr_game_id, tab_ball_class, sport58_user, pr_nclet, pr_ncletpa, pr_ncbigs, pr_ncbigspa, pr_nhlet, pr_nhletpa, pr_nhbigs, pr_nhbigspa, pr_sclet, pr_scletpa, pr_scnotletpa, pr_scbigs, pr_scbigspa, pr_shlet, pr_shletpa, pr_shnoletpa, pr_shbigs, pr_shbigspa);

                                    pr_title = "";
                                    pr_nclet = "";
                                    pr_ncletpa = "";
                                    pr_ncbigs = "";
                                    pr_ncbigspa = "";
                                    pr_nhlet = "";
                                    pr_nhletpa = "";
                                    pr_nhbigs = "";
                                    pr_nhbigspa = "";
                                    pr_sclet = "";
                                    pr_scletpa = "";
                                    pr_scnotletpa = "";
                                    pr_scbigs = "";
                                    pr_scbigspa = "";
                                    pr_shlet = "";
                                    pr_shletpa = "";
                                    pr_shnoletpa = "";
                                    pr_shbigs = "";
                                    pr_shbigspa = "";
                                }

                            }

                            if (L2.isChecked()) {
                                pr_ncbigs = mData.get(position).getNcbigs();
                                pr_ncbigspa = mData.get(position).getNcbigspa();
                                pr_title = "國際大小大";
                                if (!pr_ncbigs.matches("") && !pr_ncbigspa.matches("")) {
                                    MemberPredctionData.AddData(pr_title, pr_away, pr_home, pr_adate, pr_aclass2, pr_ni, pr_game_id, tab_ball_class, sport58_user, pr_nclet, pr_ncletpa, pr_ncbigs, pr_ncbigspa, pr_nhlet, pr_nhletpa, pr_nhbigs, pr_nhbigspa, pr_sclet, pr_scletpa, pr_scnotletpa, pr_scbigs, pr_scbigspa, pr_shlet, pr_shletpa, pr_shnoletpa, pr_shbigs, pr_shbigspa);

                                    pr_title = "";
                                    pr_nclet = "";
                                    pr_ncletpa = "";
                                    pr_ncbigs = "";
                                    pr_ncbigspa = "";
                                    pr_nhlet = "";
                                    pr_nhletpa = "";
                                    pr_nhbigs = "";
                                    pr_nhbigspa = "";
                                    pr_sclet = "";
                                    pr_scletpa = "";
                                    pr_scnotletpa = "";
                                    pr_scbigs = "";
                                    pr_scbigspa = "";
                                    pr_shlet = "";
                                    pr_shletpa = "";
                                    pr_shnoletpa = "";
                                    pr_shbigs = "";
                                    pr_shbigspa = "";
                                }

                            }

                            if (R2.isChecked()) {
                                pr_nhbigs = mData.get(position).getNhbigs();
                                pr_nhbigspa = mData.get(position).getNhbigspa();
                                pr_title = "國際大小小";
                                if (!pr_nhbigs.matches("") && !pr_nhbigspa.matches("")) {
                                    MemberPredctionData.AddData(pr_title, pr_away, pr_home, pr_adate, pr_aclass2, pr_ni, pr_game_id, tab_ball_class, sport58_user, pr_nclet, pr_ncletpa, pr_ncbigs, pr_ncbigspa, pr_nhlet, pr_nhletpa, pr_nhbigs, pr_nhbigspa, pr_sclet, pr_scletpa, pr_scnotletpa, pr_scbigs, pr_scbigspa, pr_shlet, pr_shletpa, pr_shnoletpa, pr_shbigs, pr_shbigspa);

                                    pr_title = "";
                                    pr_nclet = "";
                                    pr_ncletpa = "";
                                    pr_ncbigs = "";
                                    pr_ncbigspa = "";
                                    pr_nhlet = "";
                                    pr_nhletpa = "";
                                    pr_nhbigs = "";
                                    pr_nhbigspa = "";
                                    pr_sclet = "";
                                    pr_scletpa = "";
                                    pr_scnotletpa = "";
                                    pr_scbigs = "";
                                    pr_scbigspa = "";
                                    pr_shlet = "";
                                    pr_shletpa = "";
                                    pr_shnoletpa = "";
                                    pr_shbigs = "";
                                    pr_shbigspa = "";
                                }


                            }

                            if (L3.isChecked()) {
                                pr_sclet = mData.get(position).getSclet();
                                pr_scletpa = mData.get(position).getScletpa();
                                pr_title = "運彩讓分客";
                                if (!pr_sclet.matches("") && !pr_scletpa.matches("")) {
                                    MemberPredctionData.AddData(pr_title, pr_away, pr_home, pr_adate, pr_aclass2, pr_ni, pr_game_id, tab_ball_class, sport58_user, pr_nclet, pr_ncletpa, pr_ncbigs, pr_ncbigspa, pr_nhlet, pr_nhletpa, pr_nhbigs, pr_nhbigspa, pr_sclet, pr_scletpa, pr_scnotletpa, pr_scbigs, pr_scbigspa, pr_shlet, pr_shletpa, pr_shnoletpa, pr_shbigs, pr_shbigspa);

                                    pr_title = "";
                                    pr_nclet = "";
                                    pr_ncletpa = "";
                                    pr_ncbigs = "";
                                    pr_ncbigspa = "";
                                    pr_nhlet = "";
                                    pr_nhletpa = "";
                                    pr_nhbigs = "";
                                    pr_nhbigspa = "";
                                    pr_sclet = "";
                                    pr_scletpa = "";
                                    pr_scnotletpa = "";
                                    pr_scbigs = "";
                                    pr_scbigspa = "";
                                    pr_shlet = "";
                                    pr_shletpa = "";
                                    pr_shnoletpa = "";
                                    pr_shbigs = "";
                                    pr_shbigspa = "";

                                }
                            }

                            if (R3.isChecked()) {
                                pr_shlet = mData.get(position).getShlet();
                                pr_shletpa = mData.get(position).getShletpa();
                                pr_title = "運彩讓分主";
                                if (!pr_shlet.matches("") && !pr_shletpa.matches("")) {
                                    MemberPredctionData.AddData(pr_title, pr_away, pr_home, pr_adate, pr_aclass2, pr_ni, pr_game_id, tab_ball_class, sport58_user, pr_nclet, pr_ncletpa, pr_ncbigs, pr_ncbigspa, pr_nhlet, pr_nhletpa, pr_nhbigs, pr_nhbigspa, pr_sclet, pr_scletpa, pr_scnotletpa, pr_scbigs, pr_scbigspa, pr_shlet, pr_shletpa, pr_shnoletpa, pr_shbigs, pr_shbigspa);
                                    pr_title = "";
                                    pr_nclet = "";
                                    pr_ncletpa = "";
                                    pr_ncbigs = "";
                                    pr_ncbigspa = "";
                                    pr_nhlet = "";
                                    pr_nhletpa = "";
                                    pr_nhbigs = "";
                                    pr_nhbigspa = "";
                                    pr_sclet = "";
                                    pr_scletpa = "";
                                    pr_scnotletpa = "";
                                    pr_scbigs = "";
                                    pr_scbigspa = "";
                                    pr_shlet = "";
                                    pr_shletpa = "";
                                    pr_shnoletpa = "";
                                    pr_shbigs = "";
                                    pr_shbigspa = "";

                                }
                            }

                            if (L4.isChecked()) {
                                pr_scnotletpa = mData.get(position).getScnotletpa();
                                pr_title = "運彩不讓分客";
                                if (!pr_scnotletpa.matches("")) {
                                    MemberPredctionData.AddData(pr_title, pr_away, pr_home, pr_adate, pr_aclass2, pr_ni, pr_game_id, tab_ball_class, sport58_user, pr_nclet, pr_ncletpa, pr_ncbigs, pr_ncbigspa, pr_nhlet, pr_nhletpa, pr_nhbigs, pr_nhbigspa, pr_sclet, pr_scletpa, pr_scnotletpa, pr_scbigs, pr_scbigspa, pr_shlet, pr_shletpa, pr_shnoletpa, pr_shbigs, pr_shbigspa);

                                    pr_title = "";
                                    pr_nclet = "";
                                    pr_ncletpa = "";
                                    pr_ncbigs = "";
                                    pr_ncbigspa = "";
                                    pr_nhlet = "";
                                    pr_nhletpa = "";
                                    pr_nhbigs = "";
                                    pr_nhbigspa = "";
                                    pr_sclet = "";
                                    pr_scletpa = "";
                                    pr_scnotletpa = "";
                                    pr_scbigs = "";
                                    pr_scbigspa = "";
                                    pr_shlet = "";
                                    pr_shletpa = "";
                                    pr_shnoletpa = "";
                                    pr_shbigs = "";
                                    pr_shbigspa = "";

                                }
                            }

                            if (R4.isChecked()) {
                                pr_shnoletpa = mData.get(position).getShnotletpa();
                                pr_title = "運彩不讓分主";
                                if (!pr_shnoletpa.matches("")) {
                                    MemberPredctionData.AddData(pr_title, pr_away, pr_home, pr_adate, pr_aclass2, pr_ni, pr_game_id, tab_ball_class, sport58_user, pr_nclet, pr_ncletpa, pr_ncbigs, pr_ncbigspa, pr_nhlet, pr_nhletpa, pr_nhbigs, pr_nhbigspa, pr_sclet, pr_scletpa, pr_scnotletpa, pr_scbigs, pr_scbigspa, pr_shlet, pr_shletpa, pr_shnoletpa, pr_shbigs, pr_shbigspa);

                                    pr_title = "";
                                    pr_nclet = "";
                                    pr_ncletpa = "";
                                    pr_ncbigs = "";
                                    pr_ncbigspa = "";
                                    pr_nhlet = "";
                                    pr_nhletpa = "";
                                    pr_nhbigs = "";
                                    pr_nhbigspa = "";
                                    pr_sclet = "";
                                    pr_scletpa = "";
                                    pr_scnotletpa = "";
                                    pr_scbigs = "";
                                    pr_scbigspa = "";
                                    pr_shlet = "";
                                    pr_shletpa = "";
                                    pr_shnoletpa = "";
                                    pr_shbigs = "";
                                    pr_shbigspa = "";
                                }
                            }

                            if (L5.isChecked()) {
                                pr_scbigs = mData.get(position).getScbigs();
                                pr_scbigspa = mData.get(position).getScbigspa();
                                pr_title = "運彩大小大";
                                if (!pr_scbigs.matches("") && !pr_scbigspa.matches("")) {
                                    MemberPredctionData.AddData(pr_title, pr_away, pr_home, pr_adate, pr_aclass2, pr_ni, pr_game_id, tab_ball_class, sport58_user, pr_nclet, pr_ncletpa, pr_ncbigs, pr_ncbigspa, pr_nhlet, pr_nhletpa, pr_nhbigs, pr_nhbigspa, pr_sclet, pr_scletpa, pr_scnotletpa, pr_scbigs, pr_scbigspa, pr_shlet, pr_shletpa, pr_shnoletpa, pr_shbigs, pr_shbigspa);

                                    pr_title = "";
                                    pr_nclet = "";
                                    pr_ncletpa = "";
                                    pr_ncbigs = "";
                                    pr_ncbigspa = "";
                                    pr_nhlet = "";
                                    pr_nhletpa = "";
                                    pr_nhbigs = "";
                                    pr_nhbigspa = "";
                                    pr_sclet = "";
                                    pr_scletpa = "";
                                    pr_scnotletpa = "";
                                    pr_scbigs = "";
                                    pr_scbigspa = "";
                                    pr_shlet = "";
                                    pr_shletpa = "";
                                    pr_shnoletpa = "";
                                    pr_shbigs = "";
                                    pr_shbigspa = "";
                                }
                            }

                            if (R5.isChecked()) {
                                pr_shbigs = mData.get(position).getShbigs();
                                pr_shbigspa = mData.get(position).getShbigspa();
                                pr_title = "運彩大小小";
                                if (!pr_shbigs.matches("") && !pr_shbigspa.matches("")) {
                                    MemberPredctionData.AddData(pr_title, pr_away, pr_home, pr_adate, pr_aclass2, pr_ni, pr_game_id, tab_ball_class, sport58_user, pr_nclet, pr_ncletpa, pr_ncbigs, pr_ncbigspa, pr_nhlet, pr_nhletpa, pr_nhbigs, pr_nhbigspa, pr_sclet, pr_scletpa, pr_scnotletpa, pr_scbigs, pr_scbigspa, pr_shlet, pr_shletpa, pr_shnoletpa, pr_shbigs, pr_shbigspa);

                                    pr_title = "";
                                    pr_nclet = "";
                                    pr_ncletpa = "";
                                    pr_ncbigs = "";
                                    pr_ncbigspa = "";
                                    pr_nhlet = "";
                                    pr_nhletpa = "";
                                    pr_nhbigs = "";
                                    pr_nhbigspa = "";
                                    pr_sclet = "";
                                    pr_scletpa = "";
                                    pr_scnotletpa = "";
                                    pr_scbigs = "";
                                    pr_scbigspa = "";
                                    pr_shlet = "";
                                    pr_shletpa = "";
                                    pr_shnoletpa = "";
                                    pr_shbigs = "";
                                    pr_shbigspa = "";
                                }
                            }



                            game_id.setText("");
                            //翻牌特效
                            Animation animation = AnimationUtils.loadAnimation(predction, R.anim.back_scale);

                            animation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {}

                                @Override
                                public void onAnimationRepeat(Animation animation) {}

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    if (bool[position]) {
                                        holder.card1.setBackgroundResource(R.drawable.card_back);
                                        poker.setBackgroundResource(R.drawable.poker_back);
                                        bool[position] = false;
                                    } else {
                                        holder.card1.setBackgroundResource(R.drawable.card_front);
                                        poker.setBackgroundResource(R.drawable.poker_front);
                                        bool[position] = true;
                                    }

                                    holder.card1.startAnimation(AnimationUtils.loadAnimation(predction, R.anim.front_scale));
                                    holder.card_predction.startAnimation(AnimationUtils.loadAnimation(predction, R.anim.front_scale));
                                    poker.startAnimation(AnimationUtils.loadAnimation(predction, R.anim.front_scale));
                                    tab_low.startAnimation(AnimationUtils.loadAnimation(predction, R.anim.front_scale));
                                }
                            });
                            tab_low.setVisibility(View.GONE);
                            holder.card1.setVisibility(View.GONE);
                            holder.card2.setVisibility(View.VISIBLE);
                            holder.card1.startAnimation(animation);
                            holder.card_predction.startAnimation(animation);
                            poker.startAnimation(animation);
                            tab_low.startAnimation(animation);

                            //
                            bool2 = false;

                            if (toast != null) {
                                toast.cancel();
                            } //讓訊息不要延遲
                            Toasty.success(predction, "預測完成!", Toast.LENGTH_SHORT, true).show();
                        }

                    }
                });



            }




            @Override
            public int getItemCount() {
                return mData.size();
            }
        }

    }


    private final Handler mHandler = new Handler() {
        public void handleMessage(final android.os.Message msg) {
            if (msg.what == 1) {
                mList.setVisibility(View.GONE);
                databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data/"+os.subSequence(0, 10)+"/"+ tab_ball_class + "/" + game[i]);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        j = 0;
                        final ArrayList < game_data > myDataset = new ArrayList < > ();
                        myDataset.clear();
                        delet = new String[(int) snapshot.getChildrenCount()];
                        for (DataSnapshot ds: snapshot.getChildren()) {
                            if (ds.child("adate").getValue().toString()!=null&&String_time.time(ds.child("adate").getValue().toString()) == 1) {
                                contacts = ds.getValue(game_data.class);
                                delet[j] = ds.getKey();
                                myDataset.add(contacts);
                                j++;
                            }
                        }
                        bool = new boolean[(int) snapshot.getChildrenCount()];

                        if (myDataset.isEmpty()) {
                            title.setText("請選擇球賽");

                            predction_nogame.setVisibility(View.VISIBLE);

                            pb8.setVisibility(View.GONE);

                        } else {
                            mList.setVisibility(View.VISIBLE);
                            myAdapter = new game_input.MyAdapter(myDataset);
                            layoutManager = new LinearLayoutManager(predction);
                            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            mList.setLayoutManager(layoutManager);
                            mList.setAdapter(myAdapter);
                            predction_nogame.setVisibility(View.GONE);
                            mList.setVisibility(View.VISIBLE);
                            pb8.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

            }
            super.handleMessage(msg);
        }
    };
    @Override
    public void run() {

        Message m = mHandler.obtainMessage();
        m.what = 1;
        m.sendToTarget();

    }




    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(Prediction.this);
        super.onDestroy();
    }

    private final RadioButton.OnClickListener do_L1 = new RadioButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!L1_bool) {
                L1.setChecked(true);
                R1.setChecked(false);
                L1_bool = true;
                R1_bool = false;
            } else {
                RG1.clearCheck();
                L1_bool = false;
            }
        }
    };
    private final RadioButton.OnClickListener do_L2 = new RadioButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!L2_bool) {
                L2.setChecked(true);
                R2.setChecked(false);
                L2_bool = true;
                R2_bool = false;
            } else {
                RG2.clearCheck();
                L2_bool = false;
            }
        }
    };
    private final RadioButton.OnClickListener do_L3 = new RadioButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!L3_bool) {
                L3.setChecked(true);
                R3.setChecked(false);
                L3_bool = true;
                R3_bool = false;
            } else {
                RG3.clearCheck();
                L3_bool = false;
            }
        }
    };
    private final RadioButton.OnClickListener do_L4 = new RadioButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!L4_bool) {
                L4.setChecked(true);
                R4.setChecked(false);
                L4_bool = true;
                R4_bool = false;
            } else {
                RG4.clearCheck();
                L4_bool = false;
            }
        }
    };
    private final RadioButton.OnClickListener do_L5 = new RadioButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!L5_bool) {
                L5.setChecked(true);
                R5.setChecked(false);
                L5_bool = true;
                R5_bool = false;
            } else {
                RG5.clearCheck();
                L5_bool = false;
            }
        }
    };
    private final RadioButton.OnClickListener do_R1 = new RadioButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!R1_bool) {
                R1.setChecked(true);
                L1.setChecked(false);
                R1_bool = true;
                L1_bool = false;
            } else {
                RG1.clearCheck();
                R1_bool = false;
            }
        }
    };
    private final RadioButton.OnClickListener do_R2 = new RadioButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!R2_bool) {
                R2.setChecked(true);
                L2.setChecked(false);
                R2_bool = true;
                L2_bool = false;
            } else {
                RG2.clearCheck();
                R2_bool = false;
            }
        }
    };
    private final RadioButton.OnClickListener do_R3 = new RadioButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!R3_bool) {
                R3.setChecked(true);
                L3.setChecked(false);
                R3_bool = true;
                L3_bool = false;
            } else {
                RG3.clearCheck();
                R3_bool = false;
            }
        }
    };
    private final RadioButton.OnClickListener do_R4 = new RadioButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!R4_bool) {
                R4.setChecked(true);
                L4.setChecked(false);
                R4_bool = true;
                L4_bool = false;
            } else {
                RG4.clearCheck();
                R4_bool = false;
            }
        }
    };
    private final RadioButton.OnClickListener do_R5 = new RadioButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!R5_bool) {
                R5.setChecked(true);
                L5.setChecked(false);
                R5_bool = true;
                L5_bool = false;
            } else {
                RG5.clearCheck();
                R5_bool = false;
            }
        }
    };
}