package com.digi.learn.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digi.learn.Models.Leaderboard;
import com.digi.learn.R;

import java.util.ArrayList;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<Leaderboard> leaderboards;

    public LeaderboardAdapter(Context context, ArrayList<Leaderboard> leaderboards) {
        this.context = context;
        this.leaderboards = leaderboards;
    }

    @NonNull
    @Override
    public LeaderboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.learderboard_row, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardAdapter.ViewHolder holder, int position) {

        Leaderboard board = leaderboards.get(position);
        holder.user_points.setText(board.getPoints());
        String img_url = board.getImg_url();
        Glide.with(context)
                .load(img_url)
                .centerCrop()
                .into(holder.user_img);
        holder.position.setText(board.getPosition());
        holder.username.setText(board.getUsername());



    }

    @Override
    public int getItemCount() {
        return leaderboards.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setUserList(ArrayList<Leaderboard> userList) {
        this.leaderboards = userList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView position;
        private final ImageView user_img;
        private final TextView user_points;
        private final TextView username;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            position = itemView.findViewById(R.id.pos);
            username = itemView.findViewById(R.id.username);
            user_img = itemView.findViewById(R.id.profile_img);
            user_points = itemView.findViewById(R.id.points);

        }
    }

}
