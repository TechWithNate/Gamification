package com.digi.learn.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digi.learn.Adapter.PostAdapter;
import com.digi.learn.CreateTextPost;
import com.digi.learn.ImagePost;
import com.digi.learn.Models.Post;
import com.digi.learn.Models.User;
import com.digi.learn.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommunityFragment extends Fragment {

    private View view;
    private RecyclerView postRecyclerView;
    private RecyclerView.LayoutManager  layoutManager;
    private ArrayList<Post> posts;
    private FloatingActionButton mainFab, imageFab, textFab;
    private User user;
    private PostAdapter adapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.community_fragment, container, false);

        mainFab = view.findViewById(R.id.add_post);
        imageFab = view.findViewById(R.id.image_post);
        textFab = view.findViewById(R.id.text_post);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");

        mainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFab.getVisibility() == View.GONE && textFab.getVisibility() == View.GONE){
                    imageFab.setVisibility(View.VISIBLE);
                    textFab.setVisibility(View.VISIBLE);
                }else {
                    imageFab.setVisibility(View.GONE);
                    textFab.setVisibility(View.GONE);
                }
            }
        });

        imageFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ImagePost.class);
                startActivity(intent);
            }
        });

        textFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateTextPost.class);
                startActivity(intent);
            }
        });


        postRecyclerView = view.findViewById(R.id.postRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        user = new User();

        posts = new ArrayList<>();
//        posts.add(new Post(user, "Hi there", "12:09", "https://cdn.pixabay.com/photo/2016/11/21/12/42/beard-1845166_1280.jpg", "", "34", "5"));
//        posts.add(new Post(user, "Hi there", "12:09", "https://cdn.pixabay.com/photo/2016/11/21/12/42/beard-1845166_1280.jpg", "This is my first Game here", "34", "5"));
//        posts.add(new Post(user, "Hi there", "12:09", "https://cdn.pixabay.com/photo/2016/11/21/12/42/beard-1845166_1280.jpg", "", "34", "5"));
//        posts.add(new Post(user, "Hi there", "12:09", "https://cdn.pixabay.com/photo/2016/11/21/12/42/beard-1845166_1280.jpg", "This is my first Game here", "34", "5"));
//        posts.add(new Post(user, "Hi there", "12:09", "https://cdn.pixabay.com/photo/2016/11/21/12/42/beard-1845166_1280.jpg", "", "34", "5"));

        adapter = new PostAdapter(posts, getContext());

        postRecyclerView.setHasFixedSize(true);
        postRecyclerView.setLayoutManager(layoutManager);
        postRecyclerView.setAdapter(adapter);


        getAllPosts();


        return view;

    }


    private void getAllPosts(){

        posts.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Post post = dataSnapshot.getValue(Post.class);
                    posts.add(post);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
