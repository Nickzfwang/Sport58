package com.example.user.sport58;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jrummyapps.android.animations.Technique;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class Tab_dataquery extends Fragment {

    private static TextView date;
    private RecyclerView mList;
    private data_query contacts;
    private ImageView bg_game;
    private Drawable d;
    private ProgressBar ps7;
    private  Activity data;
    private Calendar mCal;
    private CharSequence os;

    private static final int[] resId = {
            R.drawable.ball_01,
            R.drawable.ball_02,
            R.drawable.ball_03
    };
    private static final int[] resId2 = {
            R.drawable.lose1,
            R.drawable.lose2,
            R.drawable.win1,
            R.drawable.win2
    };
    private static   ArrayList < data_query > myDataset;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_dataquery, container, false);
        mCal = Calendar.getInstance();
        mCal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        os = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime());    // kk:24小時制, hh:12小時制
        bg_game = rootView.findViewById(R.id.imageView3);
        mList = rootView.findViewById(R.id.list_view3);
        ps7 = rootView.findViewById(R.id.progressBar7);
        bg_game.setVisibility(View.GONE);
        date = rootView.findViewById(R.id.date);
        //初始化Recyclerview---------------------------------------//
        myDataset = new ArrayList < > ();
        dataquery_input.MyAdapter myAdapter = new dataquery_input.MyAdapter(myDataset);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(data);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(myAdapter);
        ////--------------------------------------------------------///
        dataqueryRunnable h1 = new dataqueryRunnable();
        Thread thr = new Thread(h1);
        thr.start();



        return rootView;
    }


    public class dataqueryRunnable implements Runnable {
        public void run() {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("data_query/"+os.subSequence(0, 10));
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    // do some stuff once
                    final ArrayList < data_query > myDataset = new ArrayList < > ();
                    myDataset.clear();
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        contacts = ds.getValue(data_query.class);
                        myDataset.add(contacts);

                    }

                    if (myDataset.isEmpty()) {
                        bg_game.setVisibility(View.VISIBLE);
                        ps7.setVisibility(View.GONE);

                    } else {
                        mList.setVisibility(View.VISIBLE);
                        dataquery_input.MyAdapter myAdapter = new dataquery_input.MyAdapter(myDataset);
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(data);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        mList.setLayoutManager(layoutManager);
                        mList.setAdapter(myAdapter);
                        Technique.BOUNCE_IN.playOn(mList);
                        bg_game.setVisibility(View.GONE);
                        ps7.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });


        }
    }


    public static class dataquery_input {

        public static class MyAdapter extends RecyclerView.Adapter < MyAdapter.ViewHolder > {
            private final ArrayList < data_query > mData;


            public class ViewHolder extends RecyclerView.ViewHolder {
                private final TextView pa_and_score;
                private final TextView leftscore;
                private final TextView rightscore;
                private final TextView id_and_game;
                private final TextView left_away;
                private final TextView right_home;
                private final TextView mid_date;
                private final ImageView ball;
                private final ImageView gun;
                private final ImageView currect;

                public ViewHolder(View v) {
                    super(v);
                    pa_and_score = v.findViewById(R.id.pa_and_score);
                    left_away = v.findViewById(R.id.left_away);
                    right_home = v.findViewById(R.id.right_home);
                    leftscore = v.findViewById(R.id.leftscore);
                    rightscore = v.findViewById(R.id.rightscore);
                    id_and_game = v.findViewById(R.id.id_and_game);
                    mid_date = v.findViewById(R.id.mid_date);
                    ball = v.findViewById(R.id.ball);
                    gun = v.findViewById(R.id.gun);
                    currect = v.findViewById(R.id.currect);
                }
            }

            public MyAdapter(ArrayList < data_query > data) {
                mData = data;
            }

            @Override
            public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_dataquery, parent, false);
                return new ViewHolder(v);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {

            }

            @Override
            public void onBindViewHolder(final ViewHolder holder, final int position, List payloads) {
                holder.setIsRecyclable(false);
                date.setText(mData.get(position).getAdate() + " 動8報");
                holder.mid_date.setText(mData.get(position).getAg() + ":" + mData.get(position).getAi());
                holder.id_and_game.setText(mData.get(position).getGameid() + " " + mData.get(position).getAclass2());
                holder.right_home.setText(mData.get(position).getHome());
                holder.left_away.setText(mData.get(position).getAway());
                holder.rightscore.setText(mData.get(position).getUnderscore());
                holder.leftscore.setText(mData.get(position).getUpscore());
                if (!mData.get(position).getWin().matches("")) {
                    if (mData.get(position).getWin().matches("L")) {

                        holder.currect.setImageResource(resId2[3]);
                        holder.gun.setImageResource(resId2[0]);
                    } else {
                        holder.gun.setImageResource(resId2[1]);
                        holder.currect.setImageResource(resId2[2]);
                    }
                } else {

                    if (!mData.get(position).getUnderscore().matches("") && !mData.get(position).getUpscore().matches("")) {
                        if ((Float.parseFloat(mData.get(position).getUpscore()) + Float.parseFloat(mData.get(position).getUnderscore())) > Float.parseFloat(mData.get(position).getScbigs())) {
                            holder.gun.setImageResource(resId2[1]);
                            holder.currect.setImageResource(resId2[2]);
                        } else {
                            holder.currect.setImageResource(resId2[3]);
                            holder.gun.setImageResource(resId2[0]);
                        }
                    } else {
                        holder.gun.setImageResource(resId2[1]);
                        holder.currect.setImageResource(resId2[3]);
                    }
                }
                if (mData.get(position).getAclass().trim().matches("棒球")) {

                    holder.ball.setImageResource(resId[1]);
                } else {
                    if (mData.get(position).getAclass().trim().matches("足球")) {

                        holder.ball.setImageResource(resId[0]);
                    } else {

                        holder.ball.setImageResource(resId[2]);
                    }
                }




                if (mData.get(position).getAclass().matches("足球")) {
                    holder.pa_and_score.setText(mData.get(position).getTips() + mData.get(position).getCountnum() + "%");
                } else {
                    if (mData.get(position).getTips().matches("b")) {
                        holder.pa_and_score.setText("58數據:" + mData.get(position).getCountnum() + "投注大分較有利!");
                    } else {
                        holder.pa_and_score.setText("58數據:" + mData.get(position).getCountnum() + "投注小分較有利!");
                    }
                }




            }


            @Override
            public int getItemCount() {
                return mData.size();
            }
        }

    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------//









}