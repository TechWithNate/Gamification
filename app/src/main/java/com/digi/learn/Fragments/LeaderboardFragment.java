package com.digi.learn.Fragments;

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

import com.digi.learn.Adapter.LeaderboardAdapter;
import com.digi.learn.Models.Leaderboard;
import com.digi.learn.Models.User;
import com.digi.learn.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LeaderboardFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db;
    private ArrayList<User> users;
    CollectionReference usersRef;
    LeaderboardAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.leaderboard_fragment, container, false);
        db = FirebaseFirestore.getInstance();
        usersRef = db.collection("users");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        users = new ArrayList<>();
        recyclerView = view.findViewById(R.id.leaderboard_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new LeaderboardAdapter(this.getContext(), users);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    users.add(user);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








//        usersRef.orderBy("points", Query.Direction.ASCENDING)
//                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                            @Override
//                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                                if (error != null) {
//                                    Toast.makeText(getContext(), "Error fetching users: "+error, Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//
//
//                                ArrayList<User> leaderboards = new ArrayList<>();
//                                adapter = new LeaderboardAdapter(getContext(), leaderboards);
//                                if (null != value){
//                                    for (QueryDocumentSnapshot document : value) {
//                                        User user = document.toObject(User.class);
//                                        leaderboards.add(user);
//                                    }
//                                }
//                                adapter.setUserList(leaderboards);
//                            }
//                        });



//        ArrayList<Leaderboard> leaderboards = new ArrayList<>();
//        leaderboards.add(new Leaderboard("1","Jade Smith", "https://cdn.pixabay.com/photo/2016/11/21/12/42/beard-1845166_1280.jpg", "241"));
//        leaderboards.add(new Leaderboard("2","Robert Stark",  "https://cdn.pixabay.com/photo/2015/03/03/20/42/man-657869_1280.jpg", "201"));
//        leaderboards.add(new Leaderboard("3","Jamie Ned",  "https://cdn.pixabay.com/photo/2019/03/09/20/30/international-womens-day-4044939_1280.jpg", "141"));
//        leaderboards.add(new Leaderboard("4", "Frank Matt", "https://cdn.pixabay.com/photo/2016/11/18/19/07/happy-1836445_1280.jpg", "41"));
//        leaderboards.add(new Leaderboard("5","Jade Smith", "https://cdn.pixabay.com/photo/2016/11/21/12/42/beard-1845166_1280.jpg", "241"));
//        leaderboards.add(new Leaderboard("6","Robert Stark",  "https://cdn.pixabay.com/photo/2015/03/03/20/42/man-657869_1280.jpg", "201"));
//        leaderboards.add(new Leaderboard("7","Jamie Ned",  "https://cdn.pixabay.com/photo/2019/03/09/20/30/international-womens-day-4044939_1280.jpg", "141"));
//        leaderboards.add(new Leaderboard("8", "Frank Matt", "https://cdn.pixabay.com/photo/2016/11/18/19/07/happy-1836445_1280.jpg", "41"));
//        leaderboards.add(new Leaderboard("9","Jade Smith", "https://cdn.pixabay.com/photo/2016/11/21/12/42/beard-1845166_1280.jpg", "241"));
//        leaderboards.add(new Leaderboard("10","Robert Stark",  "https://cdn.pixabay.com/photo/2015/03/03/20/42/man-657869_1280.jpg", "201"));
//        leaderboards.add(new Leaderboard("11","Jamie Ned",  "https://cdn.pixabay.com/photo/2019/03/09/20/30/international-womens-day-4044939_1280.jpg", "141"));
//        leaderboards.add(new Leaderboard("12", "Frank Matt", "https://cdn.pixabay.com/photo/2016/11/18/19/07/happy-1836445_1280.jpg", "41"));





        return view;

    }
}
