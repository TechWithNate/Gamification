package com.digi.learn;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        ProgressBar progressBar = findViewById(R.id.start_progress);
        progressBar.setVisibility(View.VISIBLE);
        int SPLASH_TIMER = 2500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, CreateAccount.class);
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
                finish();
            }
        }, SPLASH_TIMER);

    }

    //TODO: check firebase code not to open login screen if user is already created
}