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


public class tennis_input {
    public static class MyAdapter extends RecyclerView.Adapter < MyAdapter.ViewHolder > {
        private final ArrayList < gameover_data > mData;


        public class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView away;
            private final TextView away_message;
            private final TextView awaytitle;
            private final TextView awayscore;

            private final TextView awaysum;
            private final TextView away1;
            private final TextView away2;
            private final TextView away3;
            private final TextView away4;
            private final TextView away5;

            private final TextView home;
            private final TextView home_message;
            private final TextView hometitle;
            private final TextView homescore;

            private final TextView homesum;
            private final TextView home1;
            private final TextView home2;
            private final TextView home3;
            private final TextView home4;
            private final TextView home5;

            private final TextView big_or_small;
            private final TextView gameid;
            private final TextView bigsmall_message;
            private final RelativeLayout away_m;
            private final RelativeLayout home_m;
            private final RelativeLayout bigsmall_m;
            private final Button show_all;

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
                away3 = v.findViewById(R.id.away3);
                away4 = v.findViewById(R.id.away4);
                away5 = v.findViewById(R.id.away5);

                home = v.findViewById(R.id.home);
                hometitle = v.findViewById(R.id.hometitle);
                homescore = v.findViewById(R.id.homescore);
                homesum = v.findViewById(R.id.homesum);

                home1 = v.findViewById(R.id.home1);
                home2 = v.findViewById(R.id.home2);
                home3 = v.findViewById(R.id.home3);
                home4 = v.findViewById(R.id.home4);
                home5 = v.findViewById(R.id.home5);


            }


        }

        public MyAdapter(ArrayList < gameover_data > data) {
            mData = data;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_gameover_tennis, parent, false);

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
                holder.away3.setText(score[5]);
                holder.away4.setText(score[7]);
                holder.away5.setText(score[9]);

                holder.home1.setText(score[0]);
                holder.home2.setText(score[2]);
                holder.home3.setText(score[4]);
                holder.home4.setText(score[6]);
                holder.home5.setText(score[8]);
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