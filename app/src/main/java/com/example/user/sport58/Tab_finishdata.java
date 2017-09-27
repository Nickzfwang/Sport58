package com.example.user.sport58;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jrummyapps.android.animations.Technique;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;


public class Tab_finishdata extends Fragment {

    private  int i = 0;
    private  int j = 0;
    private  String[] baseball = new String[50];
    private  String[] basketball = new String[50];
    private  String[] football = new String[50];
    private  String[] usafootball = new String[50];
    private  String[] tennis = new String[50];
    private  String[] iceball = new String[50];
    private String[] game = baseball;
    private RecyclerView mList;
    private gameover_data contacts;
    private  Activity tab;
    private TextView title;
    private String tab_ball_class;
    private ProgressBar ps6;
    private DatabaseReference databaseReference;
    private ImageView bg_game;
    private  Activity tab_finish;
    private  Toast toast;
    private    ArrayList < gameover_data > myDataset;
    private Calendar mCal;
    private CharSequence os;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_finishdata, container, false);
        mCal = Calendar.getInstance();
        mCal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        os = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime());    // kk:24小時制, hh:12小時制
        bg_game = rootView.findViewById(R.id.imageView2);
        title = rootView.findViewById(R.id.title);
        mList = rootView.findViewById(R.id.list_view2);
        Button btn1 = rootView.findViewById(R.id.btn1);
        Button btn2 = rootView.findViewById(R.id.btn2);
        Button btn3 = rootView.findViewById(R.id.btn3);
        Button btn4 = rootView.findViewById(R.id.btn4);
        Button btn5 = rootView.findViewById(R.id.btn5);
        Button btn6 = rootView.findViewById(R.id.btn6);
        Button left_bn = rootView.findViewById(R.id.left_btn);
        Button right_bn = rootView.findViewById(R.id.right_btn);
        btn1.setOnClickListener(do_baseball);
        btn2.setOnClickListener(do_basketball);
        btn3.setOnClickListener(do_football);
        btn4.setOnClickListener(do_usafootball);
        btn5.setOnClickListener(do_tennis);
        btn6.setOnClickListener(do_iceball);
        left_bn.setOnClickListener(do_left_bn);
        right_bn.setOnClickListener(do_right_bn);
        ps6 = rootView.findViewById(R.id.progressBar6);
        ps6.setVisibility(View.GONE);
        bg_game.setVisibility(View.VISIBLE);
        title.setText("請選擇球類");

        //初始化Recyclerview---------------------------------------//
        myDataset = new ArrayList < > ();
        baseball_input.MyAdapter myAdapter = new baseball_input.MyAdapter(myDataset);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(tab);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(myAdapter);
        basketball_input.MyAdapter myAdapter2 = new basketball_input.MyAdapter(myDataset);
        final LinearLayoutManager layoutManager2 = new LinearLayoutManager(tab);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager2);
        mList.setAdapter(myAdapter2);
        football_input.MyAdapter myAdapter3 = new football_input.MyAdapter(myDataset);
        final LinearLayoutManager layoutManager3 = new LinearLayoutManager(tab);
        layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager3);
        mList.setAdapter(myAdapter3);
        tennis_input.MyAdapter myAdapter4 = new tennis_input.MyAdapter(myDataset);
        final LinearLayoutManager layoutManager4 = new LinearLayoutManager(tab);
        layoutManager4.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager4);
        mList.setAdapter(myAdapter4);
        usafootball_input.MyAdapter myAdapter5 = new usafootball_input.MyAdapter(myDataset);
        final LinearLayoutManager layoutManager5 = new LinearLayoutManager(tab);
        layoutManager5.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager5);
        mList.setAdapter(myAdapter5);
       iceball_input.MyAdapter myAdapter6 = new iceball_input.MyAdapter(myDataset);
        final LinearLayoutManager layoutManager6 = new LinearLayoutManager(tab);
        layoutManager6.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager6);
        mList.setAdapter(myAdapter6);
      //-------------------------------------------------------------------//


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
        return rootView;
    }

    //-/多線程載入------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    private class baseballRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("gameover_data2/"+os.subSequence(0, 10)+"/棒球");
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
    private class basketballRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("gameover_data2/"+os.subSequence(0, 10)+"/籃球");
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
    private class footballRunnable implements Runnable {
        public void run() {
            DatabaseReference databaseReference;
            databaseReference = FirebaseDatabase.getInstance().getReference().child("gameover_data2/"+os.subSequence(0, 10)+"/足球");
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
    private class tennisRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("gameover_data2/"+os.subSequence(0, 10)+"/網球");
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
    private class iceballRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("gameover_data2/"+os.subSequence(0, 10)+"/冰球");
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
    private class usafootballRunnable implements Runnable {
        public void run() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("gameover_data2/"+os.subSequence(0, 10)+"/美式足球");
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
    //
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//


    private final Button.OnClickListener do_baseball = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Technique.BOUNCE_IN.playOn(mList);
            j = 0; //要載入的球類表格編號
            i = 0;
            tab_ball_class = "棒球";
            game = baseball;
            title.setText(game[i]);
            bg_game.setVisibility(View.GONE);
            ps6.setVisibility(View.VISIBLE);
            mList.setVisibility(View.GONE);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("gameover_data/"+os.subSequence(0, 10)+"/" + tab_ball_class + "/" + game[i]);
            databaseReference.addValueEventListener(new ValueEventListener() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    // do some stuff once
                     myDataset = new ArrayList < > ();
                    myDataset.clear();
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        contacts = ds.getValue(gameover_data.class);
                        myDataset.add(contacts);

                    }

                    if (myDataset.isEmpty()) {
                        bg_game.setVisibility(View.VISIBLE);

                        ps6.setVisibility(View.GONE);

                    } else {
                        mList.setVisibility(View.VISIBLE);
                        baseball_input.MyAdapter myAdapter = new baseball_input.MyAdapter(myDataset);
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(tab);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        mList.setLayoutManager(layoutManager);
                        mList.setAdapter(myAdapter);
                        ps6.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

        }
    };
    private final Button.OnClickListener do_basketball = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Technique.BOUNCE_IN.playOn(mList);
            j = 1; //要載入的球類表格編號
            i = 0;

            tab_ball_class = "籃球";
            game = basketball;
            title.setText(game[i]);
            bg_game.setVisibility(View.GONE);
            ps6.setVisibility(View.VISIBLE);
            mList.setVisibility(View.GONE);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("gameover_data/"+os.subSequence(0, 10)+"/" + tab_ball_class + "/" + game[i]);
            databaseReference.addValueEventListener(new ValueEventListener() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    // do some stuff once
                     myDataset = new ArrayList < > ();
                    myDataset.clear();
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        contacts = ds.getValue(gameover_data.class);
                        myDataset.add(contacts);
                    }

                    if (myDataset.isEmpty()) {
                        bg_game.setVisibility(View.VISIBLE);

                        ps6.setVisibility(View.GONE);

                    } else {
                        mList.setVisibility(View.VISIBLE);
                        basketball_input.MyAdapter myAdapter = new basketball_input.MyAdapter(myDataset);
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(tab);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        mList.setLayoutManager(layoutManager);
                        mList.setAdapter(myAdapter);
                        ps6.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

        }
    };
    private final Button.OnClickListener do_football = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Technique.BOUNCE_IN.playOn(mList);
            j = 2; //要載入的球類表格編號
            i = 0;
            tab_ball_class = "足球";
            game = football;
            title.setText(game[i]);
            bg_game.setVisibility(View.GONE);
            ps6.setVisibility(View.VISIBLE);
            mList.setVisibility(View.GONE);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("gameover_data/"+os.subSequence(0, 10)+"/"+ tab_ball_class + "/" + game[i]);
            databaseReference.addValueEventListener(new ValueEventListener() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    // do some stuff once
                     myDataset = new ArrayList < > ();
                    myDataset.clear();
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        contacts = ds.getValue(gameover_data.class);
                        myDataset.add(contacts);

                    }

                    if (myDataset.isEmpty()) {
                        bg_game.setVisibility(View.VISIBLE);

                        ps6.setVisibility(View.GONE);

                    } else {
                        mList.setVisibility(View.VISIBLE);
                        football_input.MyAdapter myAdapter = new football_input.MyAdapter(myDataset);
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(tab);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        mList.setLayoutManager(layoutManager);
                        mList.setAdapter(myAdapter);
                        ps6.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

        }
    };
    private final Button.OnClickListener do_usafootball = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Technique.BOUNCE_IN.playOn(mList);
            j = 3; //要載入的球類表格編號
            i = 0;

            tab_ball_class = "美式足球";
            game = usafootball;
            title.setText(game[i]);
            bg_game.setVisibility(View.GONE);
            ps6.setVisibility(View.VISIBLE);
            mList.setVisibility(View.GONE);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("gameover_data/"+os.subSequence(0, 10)+"/"+ tab_ball_class + "/" + game[i]);
            databaseReference.addValueEventListener(new ValueEventListener() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    // do some stuff once
                     myDataset = new ArrayList < > ();
                    myDataset.clear();
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        contacts = ds.getValue(gameover_data.class);
                        myDataset.add(contacts);

                    }

                    if (myDataset.isEmpty()) {
                        bg_game.setVisibility(View.VISIBLE);


                        ps6.setVisibility(View.GONE);

                    } else {
                        mList.setVisibility(View.VISIBLE);
                        usafootball_input.MyAdapter myAdapter = new usafootball_input.MyAdapter(myDataset);
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(tab);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        mList.setLayoutManager(layoutManager);
                        mList.setAdapter(myAdapter);
                        ps6.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

        }
    };
    private final Button.OnClickListener do_tennis = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Technique.BOUNCE_IN.playOn(mList);

            j = 4; //要載入的球類表格編號
            i = 0;
            tab_ball_class = "網球";
            game = tennis;
            title.setText(game[i]);
            bg_game.setVisibility(View.GONE);
            ps6.setVisibility(View.VISIBLE);
            mList.setVisibility(View.GONE);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("gameover_data/"+os.subSequence(0, 10)+"/"+ tab_ball_class + "/" + game[i]);
            databaseReference.addValueEventListener(new ValueEventListener() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                     myDataset = new ArrayList < > ();
                    myDataset.clear();
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        contacts = ds.getValue(gameover_data.class);
                        myDataset.add(contacts);

                    }

                    if (myDataset.isEmpty()) {
                        bg_game.setVisibility(View.VISIBLE);


                        ps6.setVisibility(View.GONE);

                    } else {
                        mList.setVisibility(View.VISIBLE);
                        tennis_input.MyAdapter myAdapter = new tennis_input.MyAdapter(myDataset);
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(tab);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        mList.setLayoutManager(layoutManager);
                        mList.setAdapter(myAdapter);
                        ps6.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

        }
    };
    private final Button.OnClickListener do_iceball = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Technique.BOUNCE_IN.playOn(mList);
            j = 5; //要載入的球類表格編號
            i = 0;
            tab_ball_class = "冰球";
            game = iceball;
            title.setText(game[i]);
            bg_game.setVisibility(View.GONE);
            ps6.setVisibility(View.VISIBLE);
            mList.setVisibility(View.GONE);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("gameover_data/"+os.subSequence(0, 10)+"/"+ tab_ball_class + "/" + game[i]);
            databaseReference.addValueEventListener(new ValueEventListener() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    myDataset = new ArrayList < > ();
                    myDataset.clear();
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        contacts = ds.getValue(gameover_data.class);
                        myDataset.add(contacts);

                    }

                    if (myDataset.isEmpty()) {
                        bg_game.setVisibility(View.VISIBLE);

                        ps6.setVisibility(View.GONE);

                    } else {
                        mList.setVisibility(View.VISIBLE);
                        iceball_input.MyAdapter myAdapter = new iceball_input.MyAdapter(myDataset);
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(tab);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        mList.setLayoutManager(layoutManager);
                        mList.setAdapter(myAdapter);
                        ps6.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }
    };
    private final Button.OnClickListener do_left_bn = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (game.length > 1) {
                Technique.BOUNCE_IN_RIGHT.playOn(title);
                Technique.BOUNCE_IN_RIGHT.playOn(mList);
                bg_game.setVisibility(View.GONE);
                ps6.setVisibility(View.VISIBLE);
                mList.setVisibility(View.GONE);
                if (title.getText().toString().matches("請選擇球類")) {
                    ps6.setVisibility(View.GONE);
                    mList.setVisibility(View.VISIBLE);
                    bg_game.setVisibility(View.VISIBLE);
                    title.setText("請選擇球類");
                } else {
                    if (i > 0) {
                        i--;
                        title.setText(game[i]);
                    } else {
                        i = game.length - 1;
                        title.setText(game[i]);
                    }

                    databaseReference = FirebaseDatabase.getInstance().getReference().child("gameover_data/"+os.subSequence(0, 10)+"/"  + tab_ball_class + "/" + game[i]);
                    databaseReference.addValueEventListener(new ValueEventListener() {

                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            myDataset = new ArrayList < > ();
                            myDataset.clear();
                            for (DataSnapshot ds: snapshot.getChildren()) {
                                contacts = ds.getValue(gameover_data.class);
                                myDataset.add(contacts);

                            }

                            if (myDataset.isEmpty()) {
                                bg_game.setVisibility(View.VISIBLE);

                                ps6.setVisibility(View.GONE);

                            } else {
                                mList.setVisibility(View.VISIBLE);
                                switch (j) {
                                    case 0:
                                        baseball_input.MyAdapter myAdapter = new baseball_input.MyAdapter(myDataset);
                                        final LinearLayoutManager layoutManager = new LinearLayoutManager(tab);
                                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                        mList.setLayoutManager(layoutManager);
                                        mList.setAdapter(myAdapter);
                                        ps6.setVisibility(View.GONE);
                                        break;
                                    case 1:
                                        basketball_input.MyAdapter myAdapter1 = new basketball_input.MyAdapter(myDataset);
                                        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(tab);
                                        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                                        mList.setLayoutManager(layoutManager1);
                                        mList.setAdapter(myAdapter1);
                                        ps6.setVisibility(View.GONE);
                                        break;
                                    case 2:
                                        football_input.MyAdapter myAdapter2 = new football_input.MyAdapter(myDataset);
                                        final LinearLayoutManager layoutManager2 = new LinearLayoutManager(tab);
                                        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
                                        mList.setLayoutManager(layoutManager2);
                                        mList.setAdapter(myAdapter2);
                                        ps6.setVisibility(View.GONE);
                                        break;
                                    case 3:
                                        usafootball_input.MyAdapter myAdapter3 = new usafootball_input.MyAdapter(myDataset);
                                        final LinearLayoutManager layoutManager3 = new LinearLayoutManager(tab);
                                        layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
                                        mList.setLayoutManager(layoutManager3);
                                        mList.setAdapter(myAdapter3);
                                        ps6.setVisibility(View.GONE);
                                        break;
                                    case 4:
                                        tennis_input.MyAdapter myAdapter4 = new tennis_input.MyAdapter(myDataset);
                                        final LinearLayoutManager layoutManager4 = new LinearLayoutManager(tab);
                                        layoutManager4.setOrientation(LinearLayoutManager.VERTICAL);
                                        mList.setLayoutManager(layoutManager4);
                                        mList.setAdapter(myAdapter4);
                                        ps6.setVisibility(View.GONE);
                                        break;
                                    case 5:
                                        iceball_input.MyAdapter myAdapter5 = new iceball_input.MyAdapter(myDataset);
                                        final LinearLayoutManager layoutManager5 = new LinearLayoutManager(tab);
                                        layoutManager5.setOrientation(LinearLayoutManager.VERTICAL);
                                        mList.setLayoutManager(layoutManager5);
                                        mList.setAdapter(myAdapter5);
                                        ps6.setVisibility(View.GONE);
                                        break;
                                }


                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });

                }
            } else {
                Technique.BOUNCE_IN_RIGHT.playOn(title);
                Technique.BOUNCE_IN_RIGHT.playOn(mList);
            }
        }
    };

    private final Button.OnClickListener do_right_bn = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (game.length > 1) {
                Technique.BOUNCE_IN_LEFT.playOn(title);
                Technique.BOUNCE_IN_LEFT.playOn(mList);
                ps6.setVisibility(View.VISIBLE);
                mList.setVisibility(View.GONE);
                bg_game.setVisibility(View.GONE);

                if (title.getText().toString().matches("請選擇球類")) {
                    ps6.setVisibility(View.GONE);
                    mList.setVisibility(View.VISIBLE);
                    bg_game.setVisibility(View.VISIBLE);
                    title.setText("請選擇球類");
                } else {
                    if (i < game.length - 1) {
                        i++;
                        title.setText(game[i]);
                    } else {
                        i = 0;
                        title.setText(game[i]);
                    }

                    databaseReference = FirebaseDatabase.getInstance().getReference().child("gameover_data/"+os.subSequence(0, 10)+"/"  + tab_ball_class + "/" + game[i]);
                    databaseReference.addValueEventListener(new ValueEventListener() {

                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                             myDataset = new ArrayList < > ();
                            myDataset.clear();

                            for (DataSnapshot ds: snapshot.getChildren()) {
                                contacts = ds.getValue(gameover_data.class);
                                myDataset.add(contacts);
                            }

                            if (myDataset.isEmpty()) {
                                bg_game.setVisibility(View.VISIBLE);

                                ps6.setVisibility(View.GONE);

                            } else {
                                mList.setVisibility(View.VISIBLE);
                                switch (j) {
                                    case 0:
                                        baseball_input.MyAdapter myAdapter = new baseball_input.MyAdapter(myDataset);
                                        final LinearLayoutManager layoutManager = new LinearLayoutManager(tab);
                                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                        mList.setLayoutManager(layoutManager);
                                        mList.setAdapter(myAdapter);
                                        ps6.setVisibility(View.GONE);
                                        break;
                                    case 1:
                                        basketball_input.MyAdapter myAdapter1 = new basketball_input.MyAdapter(myDataset);
                                        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(tab);
                                        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                                        mList.setLayoutManager(layoutManager1);
                                        mList.setAdapter(myAdapter1);
                                        ps6.setVisibility(View.GONE);
                                        break;
                                    case 2:
                                        football_input.MyAdapter myAdapter2 = new football_input.MyAdapter(myDataset);
                                        final LinearLayoutManager layoutManager2 = new LinearLayoutManager(tab);
                                        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
                                        mList.setLayoutManager(layoutManager2);
                                        mList.setAdapter(myAdapter2);
                                        ps6.setVisibility(View.GONE);
                                        break;
                                    case 3:
                                        usafootball_input.MyAdapter myAdapter3 = new usafootball_input.MyAdapter(myDataset);
                                        final LinearLayoutManager layoutManager3 = new LinearLayoutManager(tab);
                                        layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
                                        mList.setLayoutManager(layoutManager3);
                                        mList.setAdapter(myAdapter3);
                                        ps6.setVisibility(View.GONE);
                                        break;
                                    case 4:
                                        tennis_input.MyAdapter myAdapter4 = new tennis_input.MyAdapter(myDataset);
                                        final LinearLayoutManager layoutManager4 = new LinearLayoutManager(tab);
                                        layoutManager4.setOrientation(LinearLayoutManager.VERTICAL);
                                        mList.setLayoutManager(layoutManager4);
                                        mList.setAdapter(myAdapter4);
                                        ps6.setVisibility(View.GONE);
                                        break;
                                    case 5:
                                        iceball_input.MyAdapter myAdapter5 = new iceball_input.MyAdapter(myDataset);
                                        final LinearLayoutManager layoutManager5 = new LinearLayoutManager(tab);
                                        layoutManager5.setOrientation(LinearLayoutManager.VERTICAL);
                                        mList.setLayoutManager(layoutManager5);
                                        mList.setAdapter(myAdapter5);
                                        ps6.setVisibility(View.GONE);
                                        break;
                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });
                }
            } else {
                Technique.BOUNCE_IN_LEFT.playOn(title);
                Technique.BOUNCE_IN_LEFT.playOn(mList);
            }
        }
    };

}