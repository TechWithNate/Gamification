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
import com.digi.learn.Models.Post;
import com.digi.learn.R;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private final ArrayList<Post> post;
    private final Context context;

    public PostAdapter(ArrayList<Post> post, Context context) {
        this.post = post;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Post userPost = post.get(position);
        holder.username.setText(userPost.getName());
        holder.title.setText(userPost.getTitle());
        Glide.with(context)
                .load(userPost.getProfilePicture())
                .centerCrop()
                .into(holder.profile_img);
        String post_img = null;
        post_img = userPost.getPostUrl();
        if (null != post_img && userPost.getPost().isEmpty()){
            holder.post_img.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(post_img)
                    .centerCrop()
                    .into(holder.post_img);
            holder.post.setVisibility(View.GONE);
        }else {
            holder.post_img.setVisibility(View.GONE);
            holder.post.setVisibility(View.VISIBLE);
            holder.post.setText(userPost.getPost());
        }

        holder.upvote_count.setText(userPost.getUpvote());
        holder.comment_count.setText(userPost.getComment());



    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView profile_img;
        private final TextView username;
        private final TextView title;
        private final TextView post;
        private final TextView post_date;
        private final TextView upvote_count;
        private final TextView comment_count;
        private final ImageView post_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_img = itemView.findViewById(R.id.profile_img);
            username = itemView.findViewById(R.id.username);
            title = itemView.findViewById(R.id.post_title);
            post_date = itemView.findViewById(R.id.post_date);
            post = itemView.findViewById(R.id.post_text);
            upvote_count = itemView.findViewById(R.id.upvote_count);
            comment_count = itemView.findViewById(R.id.comment_count);
            post_img = itemView.findViewById(R.id.post_img);

        }
    }
}
