package com.digi.learn.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digi.learn.Fragments.StartChallengeFragment;
import com.digi.learn.Models.Opponent;
import com.digi.learn.Models.User;
import com.digi.learn.Quiz;
import com.digi.learn.R;
import com.digi.learn.SelectOpponent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class SelectOpponentAdapter extends FirebaseRecyclerAdapter<User, SelectOpponentAdapter.ViewHolder> {


    public SelectOpponentAdapter(@NonNull FirebaseRecyclerOptions<User> options) {

        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.opponent_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull User user) {
        holder.name.setText(user.getFirstname() + " " + user.getLastname());
        holder.level.setText("Level " + user.getGameLevel());

        Picasso.get().load(user.getProfilePicture()).into(holder.profile_image);

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, Quiz.class);
            intent.putExtra("opponentName", user.getFirstname() + " " +user.getLastname());
            intent.putExtra("opponentLevel", user.getGameLevel());
            intent.putExtra("opponentProfileImage", user.getProfilePicture());
            intent.putExtra("userId", user.getuID());
            intent.putExtra("firebaseNotificationToken", user.getFirebaseNotificationToken());

            // Start the target activity
            context.startActivity(intent);
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView level;
        private ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.opponent_name);
            level = itemView.findViewById(R.id.opponent_level);
            profile_image = itemView.findViewById(R.id.opponent_image);
        }
    }









}
