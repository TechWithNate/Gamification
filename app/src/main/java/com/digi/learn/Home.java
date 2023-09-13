package com.digi.learn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.digi.learn.Fragments.AboutFragment;
import com.digi.learn.Fragments.CommunityFragment;
import com.digi.learn.Fragments.HomeFragment;
import com.digi.learn.Fragments.ChallengeFragment;
import com.digi.learn.Fragments.LeaderboardFragment;
import com.digi.learn.Fragments.ProfileFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.topAppBar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        firebaseAuth = FirebaseAuth.getInstance();

        //Initial Fragment
        replaceFragment(new HomeFragment());
        toolbar.setTitle("Challenge");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.challenge){
                    replaceFragment(new HomeFragment());
                    toolbar.setTitle("Challenge");
                } else if (item.getItemId() == R.id.community) {
                    replaceFragment(new CommunityFragment());
                    toolbar.setTitle("Community");
                } else if (item.getItemId() == R.id.leaderboard) {
                    replaceFragment(new LeaderboardFragment());
                    toolbar.setTitle("Leaderboard");
                }else if (item.getItemId() == R.id.profile){
                    replaceFragment(new ProfileFragment());
                    toolbar.setTitle("Profile");
                }

                    return true;
            }
        });
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//
//
//                Fragment selectedFragment = null;
//
//                switch (item.getItemId()){
//                    case R.id.challenge:
//                        selectedFragment = new ChallengeFragment();
//                        break;
//
//                    case R.id.community:
//
//                        selectedFragment = new CommunityFragment();
//                        break;
//
//                    case R.id.leaderboard:
//
//                        selectedFragment = new LeaderboardFragment();
//
//                        break;
//
//                    case R.id.profile:
//
//                        selectedFragment = new ProfileFragment();
//
//                        break;
//
//
//                }
//                FragmentTransaction transaction = getSupportFragmentManager().
//                        beginTransaction();
//                transaction.replace(R.id.frame_layout, selectedFragment);
//                transaction.commit();
//                return true;
//
//            }
//        });



        /*
        This line of code selects the fragment clicked in the nav drawer
         */


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);


             if (item.getItemId() == R.id.nav_about){
                 replaceFragment(new AboutFragment());
             }else if (item.getItemId() == R.id.nav_account) {
                 replaceFragment(new ProfileFragment());
             }else if (item.getItemId() == R.id.nav_share){
                 //TODO: share app
             }else if (item.getItemId() == R.id.nav_credits){
                 //TODO: open credits activity
                 Intent intent = new Intent(Home.this, Credits.class);
                 startActivity(intent);
             }else if (item.getItemId() == R.id.nav_logout){
                 firebaseAuth.signOut();
                 Intent intent = new Intent(Home.this, Login.class);
                 startActivity(intent);
                 finish();
             }

                return true;
            }
        });

    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }



}