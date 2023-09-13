package com.digi.learn.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.digi.learn.Achievements;
import com.digi.learn.Login;
import com.digi.learn.Models.User;
import com.digi.learn.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileFragment extends Fragment {

    private View view;
    private ImageView profile_img;
    private TextView username;
    private TextView course_level;
    private TextView user_bio;
    private ImageView fb, git, linked;
    private MaterialButton logoutBtn;

    private TextView points;
    private TextView game_level;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    private String imageUriAccessToken;
    private MaterialCardView achievements;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment, container, false);
        initViews();


//        achievements.setOnClickListener(v -> {
//            startActivity(new Intent(getContext(), Achievements.class));
//        });
//
//        storageReference = firebaseStorage.getReference();
//        storageReference.child("images").child(firebaseAuth.getUid()).child("Profile Pics")
//                        .getDownloadUrl()
//                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//                                        imageUriAccessToken = uri.toString();
//                                        Glide.with(getContext())
//                                                .load(uri)
//                                                .centerCrop()
//                                                .into(profile_img);
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getContext(), "Failed: to fetch image "+e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(firebaseAuth.getUid());
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//               User user = snapshot.getValue(User.class);
//               if (null != user){
//                   username.setText(user.getFirstname());
//                   course_level.setText(user.getStudentLevel());
//                   user_bio.setText(user.getUserBio());
//                   points.setText(user.getPoints() +"XP");
//                   game_level.setText("Lvl "+String.valueOf(user.getGameLevel()));
//               }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "Failed to fetch user details: "+error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
        achievements.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), Achievements.class));
        });
////
////        leaderboard.setOnClickListener(v -> {
////            replaceFragment(new LeaderboardFragment());
////        });
////
////        share.setOnClickListener(v -> {
////            Intent intent = new Intent(Intent.ACTION_SEND);
////            intent.setType("text/plain");
////            String body = "Share with your friends an improved way of learning computer science theoretical courses";
////            String link = "https://play.google.com/store/apps/details?id=com.nate.smile";
////            intent.putExtra(Intent.EXTRA_TEXT, body);
////            intent.putExtra(Intent.EXTRA_TEXT, link);
////            startActivity(Intent.createChooser(intent, "Share using"));
////        });
////
////        feedback.setOnClickListener(v -> {
////            replaceFragment(new FeedbackFragment());
////        });
////
////        about.setOnClickListener(v -> {
////            replaceFragment(new AboutFragment());
////        });
////
////        privacy.setOnClickListener(v -> {
////
////        });
//
//        logoutBtn.setOnClickListener(v -> {
//            firebaseAuth.signOut();
//            Intent intent = new Intent(getContext(), Login.class);
//            startActivity(intent);
//            getActivity().finish();
//
//        });
//

        return view;

    }


    private void initViews(){
        logoutBtn = view.findViewById(R.id.logout);
        profile_img = view.findViewById(R.id.profile_img);
        username = view.findViewById(R.id.username);
        course_level = view.findViewById(R.id.course_level);
        user_bio = view.findViewById(R.id.user_bio);
        game_level = view.findViewById(R.id.game_level);
        points = view.findViewById(R.id.points);
        achievements = view.findViewById(R.id.achieve_card);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

    }


}
