package com.example.user.sport58;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;


import com.facebook.login.LoginManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.TimeZone;

import cn.gavinliu.android.lib.scale.config.ScaleConfig;
import es.dmoral.toasty.Toasty;


public class menu extends AppCompatActivity implements Runnable {
    //變數宣告--------------------------------------------------------------------------------------------------//
    private ViewFlipper flipper;
    private int im = 0;
    private final int[] resId =new int[3];
    private FirebaseAuth mAuth;
    private  DatabaseReference databaseReference;
    private  Button logout, baseball_bn, basketball_bn, football_bn, usafootball_bn, tennis_bn, iceball_bn;
    private  Thread thr, thr3, thr4, thr5, thr6, thr7;
    private  String[] baseball = new String[50];
    private  String[] basketball = new String[50];
    private  String[] football = new String[50];
    private  String[] usafootball = new String[50];
    private  String[] tennis = new String[50];
    private  String[] iceball = new String[50];
    private static Activity mainclose;
    private String use;
    private String pass;
    private Calendar mCal;
    private CharSequence os;
    private  FirebaseUser user;
    private  String sport58_user;
    private String appVersion;
    private int once=0;
    //----------------------------------------------------------------------------------------------------------------------//
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

        setContentView(R.layout.activity_menu);


        //版本更新識別-----------------------------------------------------------------------------------------------------------------//
        PackageManager manager = this.getPackageManager();
        try { PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            appVersion = info.versionName; //版本名
        } catch (PackageManager.NameNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("version/");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("versionName").getValue().equals(appVersion)&&once==0){

                    AlertDialog.Builder terms = new AlertDialog.Builder(menu.this);
                    terms.setTitle("更新");
                    terms.setIcon(R.drawable.icon);
                    terms.setMessage(
                            "發現新版本\n"+"\n"+
                            "舊版本:"+appVersion+"\n"+
                            "新版本:"+dataSnapshot.child("versionName").getValue().toString());
                    terms.setPositiveButton("更新!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Uri uri = Uri.parse("https://drive.google.com/uc?authuser=0&id=0Byle8vgkmUROODRuNjloOFlFN3c&export=download");
                            Intent apk = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(apk);
                            once++;
                        }
                    });
                    terms.setNeutralButton("下次在更新!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            once++;
                        }
                    });
                    terms.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//-----------------------------------------------------------------------------------------------------------------------------//



        mCal = Calendar.getInstance();
        mCal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        os = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime());    // kk:24小時制, hh:12小時制
resId[0]=R.drawable.bg_2;
resId[1]= R.drawable.bg_3;
 resId[2]=R.drawable.bg_4;
        mAuth = FirebaseAuth.getInstance();
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        use = getSharedPreferences("user", MODE_PRIVATE).getString("user", "");
        pass = getSharedPreferences("user", MODE_PRIVATE).getString("pass", "");
        user = FirebaseAuth.getInstance().getCurrentUser();
        logout = (Button) findViewById(R.id.login);

        ////判斷是否登入----------------------------------------------------------------------///////////////////
        sport58_user = null;
        if (user != null) {
            sport58_user = user.getUid();
            logout.setText("登出");
        }
        if (!use.equals("") && !pass.equals("")) {
            sport58_user = use;
            logout.setText("登出");
        }

        /////-------------------------------------------------------------------------------------------/////


        setTitle("首頁");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        MobileAds.initialize(this, "ca-app-pub-1543324606848385/3517415095");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        //上線時的廣告AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mainclose = this;
        //元件宣告-------------------------------------------------------------------------------//
        flipper = (ViewFlipper) findViewById(R.id.flipper);
        Button btn1 = (Button) findViewById(R.id.login);
        baseball_bn = (Button) findViewById(R.id.btn1);
        basketball_bn = (Button) findViewById(R.id.btn2);
        football_bn = (Button) findViewById(R.id.btn3);
        usafootball_bn = (Button) findViewById(R.id.btn4);
        tennis_bn = (Button) findViewById(R.id.btn5);
        iceball_bn = (Button) findViewById(R.id.btn6);
        Button lookdata = (Button) findViewById(R.id.lookdata);
        Button my_predction = (Button) findViewById(R.id.my_prediction);
        Button web = (Button) findViewById(R.id.web);
        Button prediction = (Button) findViewById(R.id.prediction);

        btn1.setOnClickListener(dobtn1);
        web.setOnClickListener(do_web);
        lookdata.setOnClickListener(do_lookdata);
        prediction.setOnClickListener(do_prediction);
        my_predction.setOnClickListener(do_mypredction);
        baseball_bn.setOnClickListener(dobaseball_bn);
        basketball_bn.setOnClickListener(dobasketball_bn);
        football_bn.setOnClickListener(dofootball_bn);
        usafootball_bn.setOnClickListener(dousafootball_bn);
        tennis_bn.setOnClickListener(dotennis_bn);
        iceball_bn.setOnClickListener(doiceball_bn);

        //----------------------------------------------------------------------------------------//

        baseballRunnable h1 = new baseballRunnable();
        thr = new Thread(h1);
        thr.start();
        basketballRunnable h3 = new basketballRunnable();
        thr3 = new Thread(h3);
        thr3.start();
        footballRunnable h4 = new footballRunnable();
        thr4 = new Thread(h4);
        thr4.start();
        tennisRunnable h5 = new tennisRunnable();
        thr5 = new Thread(h5);
        thr5.start();
        iceballRunnable h6 = new iceballRunnable();
        thr6 = new Thread(h6);
        thr6.start();
        usafootballRunnable h7 = new usafootballRunnable();
        thr7 = new Thread(h7);
        thr7.start();

        //首頁輪播線程
        Thread threads = new Thread(menu.this);
        threads.start();
        //



    }






    //-/會員預測資料多線程載入------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    private class baseballRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data2/"+os.subSequence(0, 10)+"/棒球");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    if ((int) dataSnapshot.getChildrenCount() != 0) {
                        baseball = new String[(int) dataSnapshot.getChildrenCount()];
                        baseball[i] = "";
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
    private class basketballRunnable implements Runnable {
        public void run() {

            databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data2/"+os.subSequence(0, 10)+"/籃球");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    if ((int) dataSnapshot.getChildrenCount() != 0) {
                        basketball = new String[(int) dataSnapshot.getChildrenCount()];
                        basketball[i] = "";
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
                        football[i] = "";
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
    private class tennisRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data2/"+os.subSequence(0, 10)+"/網球");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    if (dataSnapshot.getChildrenCount() != 0) {
                        tennis = new String[(int) dataSnapshot.getChildrenCount()];
                        tennis[i] = "";
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
    private class iceballRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data2/"+os.subSequence(0, 10)+"/冰球");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;

                    if ((int) dataSnapshot.getChildrenCount() != 0) {
                        iceball = new String[(int) dataSnapshot.getChildrenCount()];
                        iceball[i] = "";
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
    private class usafootballRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("game_data2/"+os.subSequence(0, 10)+"/美式足球");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    if ((int) dataSnapshot.getChildrenCount() != 0) {
                        usafootball = new String[(int) dataSnapshot.getChildrenCount()];
                        usafootball[i] = "";
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
    //
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//




    private static ImageView getImageView(int resId) {
        ImageView image = new ImageView(menu.mainclose);
        image.setBackgroundResource(resId);
        return image;
    }

    //按鈕方法-----------------------------------------------------------------------------------------------------------------------------------------------------//
    private final Button.OnClickListener do_prediction = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

            //初始化Intent物件
            Intent intent = new Intent();
            //從MainActivity 到login
            intent.setClass(menu.this, Prediction.class);
            //開啟Activity
            startActivity(intent);
        }
    };
    private final Button.OnClickListener do_mypredction = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (sport58_user == null) {
                Toast.makeText(menu.this, "尚未登入會員!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.setClass(menu.this, My_predction.class);
                startActivity(intent);
            }

        }
    };
    private final Button.OnClickListener do_lookdata = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

            //初始化Intent物件
            Intent intent = new Intent();
            //從MainActivity 到login
            intent.setClass(menu.this, Tab_data.class);
            //開啟Activity
            startActivity(intent);
        }
    };
    private final Button.OnClickListener do_web = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://sport58.tw/index.php");
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i);
        }
    };
    //手機返回鍵設置
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 攔截返回鍵
            new AlertDialog.Builder(menu.this)
                    .setTitle("確認視窗")
                    .setMessage("確定要結束應用程式嗎?")
                    .setPositiveButton("取消",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                }
                            })
                    .setNegativeButton("確認",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                }
                            }).show();
        }
        return true;
    }
    ///

    private final Button.OnClickListener dobtn1 = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (logout.getText().toString().matches("登出")) {

                mAuth.signOut();
                LoginManager.getInstance().logOut();
                sport58_user = null;
                SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
                pref.edit()
                        .clear()
                        .apply();
                Toasty.success(menu.this, "已登出", Toast.LENGTH_SHORT, true).show();
                logout.setText("註冊/登入");
                finish();
                startActivity(new Intent(menu.this, login.class));
            } else {

                Intent intent = new Intent();
                intent.setClass(menu.this, login.class);
                startActivity(intent);
            }
        }
    };
    private final Button.OnClickListener dobasketball_bn = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

            //初始化Intent物件
            Intent intent = new Intent();
            intent.setClass(menu.this, Game_menu.class);
            intent.putExtra("ball_class", "籃球");
            intent.putExtra("game_class", basketball);
            //開啟Activity
            startActivity(intent);

        }
    };
    private final Button.OnClickListener dofootball_bn = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {


            //初始化Intent物件
            Intent intent = new Intent();
            intent.setClass(menu.this, Game_menu.class);
            intent.putExtra("ball_class", "足球");
            intent.putExtra("game_class", football);
            //開啟Activity
            startActivity(intent);



        }
    };
    private final Button.OnClickListener dousafootball_bn = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

            //初始化Intent物件
            Intent intent = new Intent();
            intent.setClass(menu.this, Game_menu.class);
            intent.putExtra("ball_class", "美式足球");
            intent.putExtra("game_class", usafootball);
            //開啟Activity
            startActivity(intent);




        }
    };
    private final Button.OnClickListener dotennis_bn = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

            //初始化Intent物件
            Intent intent = new Intent();
            intent.setClass(menu.this, Game_menu.class);
            intent.putExtra("ball_class", "網球");
            intent.putExtra("game_class", tennis);
            //開啟Activity
            startActivity(intent);




        }
    };
    private final Button.OnClickListener doiceball_bn = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

            //初始化Intent物件
            Intent intent = new Intent();
            intent.setClass(menu.this, Game_menu.class);
            intent.putExtra("ball_class", "冰球");
            intent.putExtra("game_class", iceball);
            //開啟Activity
            startActivity(intent);



        }
    };
    private final Button.OnClickListener dobaseball_bn = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

            //初始化Intent物件
            Intent intent = new Intent();
            intent.setClass(menu.this, Game_menu.class);
            intent.putExtra("ball_class", "棒球");
            intent.putExtra("game_class", baseball);
            //開啟Activity
            startActivity(intent);

        }
    };

    ///-------------------------------------------------------------------------------------------------------------------------------------------------------------------//


    ///離開後回收線程-------------------------------------------------------------------------------------------------//
    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(mainclose);
        super.onDestroy();
    }
    ///------------------------------------------------------------------------------------------------------------------//



    ///首頁圖片輪播線程----------------------------------------------------------------------------------------------------------------------------------------------------//
    private final Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                //動態導入的方式為ViewFlipper加入子View
                for (int aResId: resId) {
                    flipper.addView(getImageView(aResId));
                }
                //為ViewFlipper去添加動畫效果
                flipper.setFlipInterval(3000);
                flipper.startFlipping(); //為ViewFlipper去添加動畫效果


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

    //------------------------------------------------------------------------------------------------------------------------------------------------------//


}