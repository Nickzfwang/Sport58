package com.example.user.sport58;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class football_input {

    public static class MyAdapter extends RecyclerView.Adapter < MyAdapter.ViewHolder > {
        private final ArrayList < gameover_data > mData;


        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView away, away_message, awaytitle, awayscore, awaysum, away1, away2;
            private TextView home, home_message, hometitle, homescore, homesum, home1, home2;
            private TextView big_or_small, gameid, bigsmall_message;
            private RelativeLayout away_m, home_m, bigsmall_m;
            private Button show_all;

            public ViewHolder(View v) {
                super(v);
                show_all = v.findViewById(R.id.show_all);
                bigsmall_message = v.findViewById(R.id.bigsmall_message);
                bigsmall_m = v.findViewById(R.id.bigsmall_m);
                home_message = v.findViewById(R.id.home_message);
                away_message = v.findViewById(R.id.away_message);
                away_m = v.findViewById(R.id.away_m);
                home_m = v.findViewById(R.id.home_m);
                big_or_small = v.findViewById(R.id.big_or_small);
                gameid = v.findViewById(R.id.gameid);
                away = v.findViewById(R.id.away);
                awaytitle = v.findViewById(R.id.awaytitle);
                awayscore = v.findViewById(R.id.awayscore);
                awaysum = v.findViewById(R.id.awaysum);

                away1 = v.findViewById(R.id.away1);
                away2 = v.findViewById(R.id.away2);

                home = v.findViewById(R.id.home);
                hometitle = v.findViewById(R.id.hometitle);
                homescore = v.findViewById(R.id.homescore);
                homesum = v.findViewById(R.id.homesum);

                home1 = v.findViewById(R.id.home1);
                home2 = v.findViewById(R.id.home2);


            }
        }

        public MyAdapter(ArrayList < gameover_data > data) {
            mData = data;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_gameover_football, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position, List payloads) {
            holder.setIsRecyclable(false);
            holder.away_m.setVisibility(View.GONE);
            holder.home_m.setVisibility(View.GONE);
            holder.bigsmall_m.setVisibility(View.GONE);
            holder.gameid.setText(mData.get(position).getGameid());
            holder.away.setText(mData.get(position).getAway());
            holder.awayscore.setText(mData.get(position).getUpscore());
            holder.home.setText(mData.get(position).getHome());
            holder.homescore.setText(mData.get(position).getUnderscore());

            holder.homesum.setText(mData.get(position).getUnderscore());
            holder.awaysum.setText(mData.get(position).getUpscore());
            holder.hometitle.setText(mData.get(position).getHome());
            holder.awaytitle.setText(mData.get(position).getAway());


            switch (Winner_color.winner_color(mData.get(position).getUnderscore(), mData.get(position).getUpscore())) {
                case 0:
                    holder.home.setTextColor(Color.parseColor("#ff0099cc"));
                    holder.hometitle.setTextColor(Color.parseColor("#ff0099cc"));
                    break;
                case 1:
                    holder.away.setTextColor(Color.parseColor("#ff0099cc"));
                    holder.awaytitle.setTextColor(Color.parseColor("#ff0099cc"));
            }


            String[] score = ball_score.score(mData.get(position).getScorebox());
            if (score[0] != null) {
                holder.away1.setText(score[1]);
                holder.away2.setText(score[3]);

                holder.home1.setText(score[0]);
                holder.home2.setText(score[2]);
            }
            switch (big_or_small.big_or_small(mData.get(position).getUpscore(), mData.get(position).getUnderscore(), mData.get(position).getStart(), mData.get(position).getNcbigs())) {
                case 0:
                    holder.big_or_small.setText("等待結果中");
                    break;
                case 1:
                    holder.big_or_small.setText("大分");

                    break;
                case 2:
                    holder.big_or_small.setText("小分");
                    break;
                case 3:
                    holder.big_or_small.setText("無國際盤大小賠率");
                    break;
            }


            holder.hometitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int h = holder.home_m.getVisibility();
                    if (!mData.get(position).getNhlet().matches("") && !mData.get(position).getNhletpa().matches("")) {
                        if (h == 8) {
                            holder.home_m.setVisibility(View.VISIBLE);
                            holder.home_message.setText("讓" + mData.get(position).getNhlet() + "分," + mData.get(position).getNhletpa());
                        } else {
                            holder.home_m.setVisibility(View.GONE);
                        }
                    }

                }
            });
            holder.awaytitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int a = holder.away_m.getVisibility();
                    if (!mData.get(position).getNclet().matches("") && !mData.get(position).getNcletpa().matches("")) {
                        if (a == 8) {
                            holder.away_m.setVisibility(View.VISIBLE);
                            holder.away_message.setText("讓" + mData.get(position).getNclet() + "分," + mData.get(position).getNcletpa());
                        } else {
                            holder.away_m.setVisibility(View.GONE);
                        }
                    }
                }
            });
            holder.big_or_small.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int b = holder.bigsmall_m.getVisibility();
                    if (!mData.get(position).getNcbigs().matches("") && !mData.get(position).getNcbigspa().matches("")) {
                        if (b == 8) {
                            if (holder.big_or_small.getText().toString().matches("大分")) {
                                holder.bigsmall_m.setVisibility(View.VISIBLE);
                                holder.bigsmall_message.setText(mData.get(position).getNcbigs() + "大分," + mData.get(position).getNcbigspa());
                            }
                        } else {
                            holder.bigsmall_m.setVisibility(View.GONE);
                        }
                    }
                }
            });

            holder.show_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int a = holder.away_m.getVisibility();
                    int h = holder.home_m.getVisibility();
                    int b = holder.bigsmall_m.getVisibility();

                    if (!mData.get(position).getNcbigs().matches("") && !mData.get(position).getNcbigspa().matches("")) {
                        if (b == 8) {
                            if (holder.big_or_small.getText().toString().matches("大分")) {
                                holder.bigsmall_m.setVisibility(View.VISIBLE);
                                holder.bigsmall_message.setText(mData.get(position).getNcbigs() + "大分," + mData.get(position).getNcbigspa());
                            }
                        } else {
                            holder.bigsmall_m.setVisibility(View.GONE);
                        }
                    }
                    if (!mData.get(position).getNclet().matches("") && !mData.get(position).getNcletpa().matches("")) {
                        if (a == 8) {
                            holder.away_m.setVisibility(View.VISIBLE);
                            holder.away_message.setText("讓" + mData.get(position).getNclet() + "分," + mData.get(position).getNcletpa());
                        } else {
                            holder.away_m.setVisibility(View.GONE);
                        }
                    }
                    if (!mData.get(position).getNhlet().matches("") && !mData.get(position).getNhletpa().matches("")) {
                        if (h == 8) {
                            holder.home_m.setVisibility(View.VISIBLE);
                            holder.home_message.setText("讓" + mData.get(position).getNhlet() + "分," + mData.get(position).getNhletpa());
                        } else {
                            holder.home_m.setVisibility(View.GONE);
                        }
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