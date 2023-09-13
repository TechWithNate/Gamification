package com.digi.learn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.digi.learn.Adapter.SelectOpponentAdapter;
import com.digi.learn.Models.Opponent;
import com.digi.learn.Models.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SelectOpponent extends AppCompatActivity {

    private MaterialToolbar topAppBar;
    private RecyclerView opponentRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<User> users;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    SelectOpponentAdapter adapter;
    String courseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_opponent);
        initViews();


        Intent intent = getIntent();
        if (null != intent){
            courseId = intent.getStringExtra("courseId");
        }



        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        //Query query = databaseReference.orderByChild("userId").equalTo(firebaseAuth.getCurrentUser().getUid());

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(databaseReference, User.class)
                        .build();


        adapter = new SelectOpponentAdapter(options);


        opponentRecycler.setHasFixedSize(true);
        opponentRecycler.setLayoutManager(layoutManager);
        opponentRecycler.setAdapter(adapter);

    }

    public void initViews(){
        topAppBar = findViewById(R.id.topAppBar);
        opponentRecycler = findViewById(R.id.opponents_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        users = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null){
            adapter.stopListening();
        }

    }


    //    public void fetchUsers() {
//        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        String currentUserId = currentUser.getUid();
//
//        // Query the database to get all users except the current user
//        Query query = usersRef.orderByKey().equalTo(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                users.clear(); // Clear the list before adding users
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    User user = snapshot.getValue(User.class);
//
//                    if (!user.getuID().equals(currentUserId)) {
//                        users.add(user); // Add user to the list if it's not the current user
//                    }
//                }
//
//                // Set up the adapter and bind it to the RecyclerView
//                SelectOpponentAdapter adapter = new SelectOpponentAdapter(this, users);
//                opponentRecycler.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle database error
//            }
//        });
//    }

}