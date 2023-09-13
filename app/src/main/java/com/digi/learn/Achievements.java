package com.digi.learn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.digi.learn.Adapter.AchievementAdapter;
import com.digi.learn.Models.AchievementModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Achievements extends AppCompatActivity {

    private RecyclerView achievementRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<AchievementModel> achievementModels;
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        initViews();

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        achievementRecycler.setHasFixedSize(true);
        achievementRecycler.setLayoutManager(layoutManager);




        achievementModels = new ArrayList<>();
        achievementModels.add(new AchievementModel("New User", "Welcome to DigiLearn", "https://i.imgur.com/0sGvIF2.png"));
        achievementModels.add(new AchievementModel("Engaged In", "Completes First challenge", "https://t4.ftcdn.net/jpg/03/21/20/21/240_F_321202193_aVKabvDw6EYU55qAfP7Lvu78iNICCfEd.jpg"));
        achievementModels.add(new AchievementModel("Epic", "Won 50 challenges", "https://1000logos.net/wp-content/uploads/2020/09/Epic-Logo-old.png"));
        achievementModels.add(new AchievementModel("Respect", "Completed 100 challenges", "https://www.shutterstock.com/shutterstock/photos/1277529580/display_1500/stock-vector-respect-e-sport-logo-1277529580.jpg"));
        achievementModels.add(new AchievementModel("Guru", "Won 500 challenges", "https://logos.flamingtext.com/City-Logos/Guru-Water-Logo.png"));
        achievementModels.add(new AchievementModel("Rising Start", "Won 50 challenges in a row", "https://cdn.dribbble.com/users/14474/screenshots/242651/rs_logo.png"));
        achievementModels.add(new AchievementModel("Intern", "Upvote for a post", "https://i.pinimg.com/originals/11/c5/a6/11c5a6feba3335c47804cedb68b69dbd.png"));
        achievementModels.add(new AchievementModel("Junior", "5 upvote for a post", "https://t3.ftcdn.net/jpg/03/66/48/54/360_F_366485454_k1RUdsFYtKnaPfD5kiL2nHFutx8gKBPv.jpg"));
        achievementModels.add(new AchievementModel("Ninja", "Challenged in all courses", "https://e0.pxfuel.com/wallpapers/784/631/desktop-wallpaper-ninja-gaming-logo-novocom-top-ninja-gamer.jpg"));
        achievementModels.add(new AchievementModel("Boss", "Reached final level 22", "https://static.vecteezy.com/system/resources/previews/004/669/365/non_2x/trophy-champion-logo-vector.jpg"));


        AchievementAdapter adapter = new AchievementAdapter(this, achievementModels);
        achievementRecycler.setAdapter(adapter);

    }


    private void initViews(){
        achievementRecycler = findViewById(R.id.achievements_recycler);
        topAppBar = findViewById(R.id.topAppBar);
        layoutManager = new LinearLayoutManager(this);
    }

}