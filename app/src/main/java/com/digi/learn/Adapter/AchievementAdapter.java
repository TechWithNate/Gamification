package com.digi.learn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digi.learn.Models.AchievementModel;
import com.digi.learn.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.ViewHolder> {

    private Context context;
    private ArrayList<AchievementModel> achievementModels;

    public AchievementAdapter(Context context, ArrayList<AchievementModel> achievementModels) {
        this.context = context;
        this.achievementModels = achievementModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievemts_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AchievementModel model = achievementModels.get(position);
        holder.achievement_text.setText(model.getAchievementName());
        holder.achievement_description.setText(model.getAchievementDescription());

//        Glide.with(context)
//                .load(model.getAchievementImageLink())
//                .centerCrop()
//                .into(holder.achievement_image);

        Picasso.get()
                .load(model.getAchievementImageLink())
                .into(holder.achievement_image);

    }

    @Override
    public int getItemCount() {
        return achievementModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView achievement_image;
        private TextView achievement_text;
        private TextView achievement_description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            achievement_image = itemView.findViewById(R.id.achieve_icon);
            achievement_text = itemView.findViewById(R.id.achieve_text);
            achievement_description = itemView.findViewById(R.id.achieve_description);

        }
    }
}
