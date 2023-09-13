package com.digi.learn;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

public class Results extends AppCompatActivity {


    private RelativeLayout relativeLayout;
    private TextView winStatus;
    private TextView winText;
    private ImageView playerImage;
    private ImageView opponentImage;
    private TextView playerLevel;
    private TextView playerName;
    private TextView opponentName;
    private TextView scores;

    private TextView opponentLevel;
    private TextView winningXP;
    private MaterialButton rematchBtn;

    private boolean status;
    private int pScore;
    private int oScore;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        initViews();



        Intent intent = getIntent();
        if (null != intent) {
            status = intent.getBooleanExtra("status", false);
            playerName.setText(intent.getStringExtra("playerName"));
            opponentName.setText(intent.getStringExtra("opponentName"));
            playerLevel.setText(intent.getStringExtra("playerLevel"));
            opponentLevel.setText(intent.getStringExtra("opponentLevel"));
            pScore = intent.getIntExtra("score", 0);

            Glide.with(this)
                    .load(intent.getStringExtra("playerProfilePic"))
                    .into(playerImage);

            Glide.with(this)
                    .load(intent.getStringExtra("opponentProfilePic"))
                    .into(opponentImage);

        }

        scores.setText(pScore + " : " + oScore);

        if (status){
            setScore();
        }else {
            relativeLayout.setBackgroundColor(R.color.grey_dark);
            winStatus.setText("Awaiting Opponent");
            winText.setText("Winner Gets");
        }

        Log.d(TAG, "onCreate: Status "+ status);
        Toast.makeText(this, "Status:  "+status, Toast.LENGTH_SHORT).show();


        //TODO Restart Quiz with the same opponent
        rematchBtn.setOnClickListener(v -> {

        });

    }


    private void initViews(){
        relativeLayout = findViewById(R.id.relative_layout);
        winStatus = findViewById(R.id.win_status);
        winText = findViewById(R.id.win_txt);
        playerImage = findViewById(R.id.player_image);
        opponentImage = findViewById(R.id.opponent_image);
        playerLevel = findViewById(R.id.player_level);
        opponentLevel = findViewById(R.id.opponent_level);
        playerName = findViewById(R.id.player_name);
        opponentName = findViewById(R.id.opponent_name);
        scores = findViewById(R.id.scores);
        rematchBtn = findViewById(R.id.rematch_btn);
        winningXP = findViewById(R.id.winningXP);

    }

    @SuppressLint("ResourceAsColor")
    private void setScore(){
        if (pScore > oScore){
            winStatus.setText("You Won");
            winText.setText("You Won");
            winningXP.setText("10 XP");
            relativeLayout.setBackgroundColor(R.color.green);
        }else if (pScore == oScore){
            winStatus.setText("Draw");
            winText.setText("Draw");
            winningXP.setText("5 XP");
            relativeLayout.setBackgroundColor(R.color.grey_dark);
        }else {
            winStatus.setText("You Loose");
            winText.setText("You Loose");
            winningXP.setText("2 XP");
            relativeLayout.setBackgroundColor(R.color.red);
        }
    }


}