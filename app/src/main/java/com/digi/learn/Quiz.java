package com.digi.learn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.digi.learn.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.messaging.FirebaseMessaging;



import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quiz extends AppCompatActivity {

    private LinearProgressIndicator progress;
    private CountDownTimer countDownTimer;
    private MaterialToolbar topAppBar;
    private ImageView playerImage, opponentImage;
    private TextView playerName, opponentName,
            playerLevel, opponentLevel;

    private TextView question;
    RadioButton radioButtonA;
    RadioButton radioButtonB;
    RadioButton radioButtonC;
    RadioButton radioButtonD;
    private RadioGroup radioGroup;
    private MaterialButton submitBtn;

    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseFirestore firebaseFirestore;

    private DatabaseReference databaseReference;
    private int totalQuestions= 13;
    private int questionsToAnswer = 5;
    String oppLevel;
    String oppName;
    String courseTitle;
    String oppProfileImage;
    String playerImageToken;
    TextView playerScore;
    private String opponentId;

    int score = 0; // Add a variable to keep track of the user's score
    boolean answered = false; // Add a variable to check if the user has already answered
    int currentQuestionIndex = 0;
    String selectedAnswer = "";
    String correctAnswer;
    String quizKey;
    DatabaseReference quizzesRef;
    private String firebaseNotificationToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        initViews();

        Intent intent = getIntent();
        if (null != intent){
            courseTitle = intent.getStringExtra("courseTitle");
            oppName = intent.getStringExtra("opponentName");
            oppLevel = intent.getStringExtra("opponentLevel");
            oppProfileImage = intent.getStringExtra("opponentProfileImage");
            opponentId =  intent.getStringExtra("userId");
            firebaseNotificationToken = intent.getStringExtra("firebaseNotificationToken");
            Toast.makeText(this, "This is the user ID"+ intent.getStringExtra("userId"), Toast.LENGTH_SHORT).show();
        }


        topAppBar.setTitle("");
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        opponentName.setText(oppName);
        opponentLevel.setText(oppLevel);
        Glide.with(this)
                .load(oppProfileImage)
                .into(opponentImage);

        //setProgress();
        playerDetails();

        databaseReference = FirebaseDatabase.getInstance().getReference("Courses");


        quizzesRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(firebaseAuth.getUid()).child("quizzes");
//        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/"+firebaseAuth.getUid()); // Replace "user1" with the current user's ID
//        DatabaseReference quizzesRef = userRef.child("quizzes");
        quizKey = quizzesRef.push().getKey(); // Generate a unique key for the quiz

        // Prepare the quiz data
        Map<String, Object> quizData = new HashMap<>();
        quizData.put("courseName", "Course 1"); // Set the course name as needed
        quizData.put("opponentId", opponentId); // Set the opponent's ID as needed
        // Add questions and answers to the quizData map as needed

        // Set the quiz data under the generated key
        quizzesRef.child(quizKey).setValue(quizData);

        // Fetch the initial set of questions
        fetchQuestions();
        submitBtn.setOnClickListener(v -> checkRadioButton());
        radioGroup.clearCheck();



    }


    private void initViews(){
        topAppBar = findViewById(R.id.topAppBar);
        playerImage = findViewById(R.id.player_image);
        opponentImage = findViewById(R.id.opponent_image);
        playerName = findViewById(R.id.player_name);
        opponentName = findViewById(R.id.opponent_name);
        playerLevel = findViewById(R.id.player_level);
        opponentLevel = findViewById(R.id.opponent_level);
        progress = findViewById(R.id.progress);
        question = findViewById(R.id.question);
        radioButtonA = findViewById(R.id.answer1);
        radioButtonB = findViewById(R.id.answer2);
        radioButtonC = findViewById(R.id.answer3);
        radioButtonD = findViewById(R.id.answer4);
        submitBtn = findViewById(R.id.submit_btn);
        radioGroup = findViewById(R.id.radio_group);
        playerScore = findViewById(R.id.player_score);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel the timer to prevent memory leaks
        countDownTimer.cancel();
    }

    //Start progress bar
//    private void setProgress(){
//        // Create a CountDownTimer for 30 seconds
//        countDownTimer = new CountDownTimer(30000, 1000) { // 30 seconds (30,000 milliseconds), tick every second (1000 milliseconds)
//            @Override
//            public void onTick(long millisUntilFinished) {
//                // Update the progress bar
//                //progress.setProgress((int) millisUntilFinished);
//                int progressL = (int) millisUntilFinished;
//                progress.setProgress(progressL);
//            }
//
//            @Override
//            public void onFinish() {
//                // Timer finished, you can perform any action here
//                fetchQuestions();
//            }
//        };
//
//        // Start the countdown timer
//        countDownTimer.start();
//    }

    private void playerDetails(){
        storageReference = firebaseStorage.getReference();
        storageReference.child("images").child(firebaseAuth.getUid()).child("Profile Pics")
                        .getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        playerImageToken = uri.toString();
                                        Glide.with(Quiz.this)
                                                .load(uri)
                                                .centerCrop()
                                                .into(playerImage);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Quiz.this, "Failed: to fetch image "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               User user = snapshot.getValue(User.class);
               if (null != user){
                   playerName.setText(user.getFirstname());
                   playerLevel.setText("Level "+ user.getGameLevel());
               }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Quiz.this, "Failed to fetch user details: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchQuestions() {


        radioGroup.clearCheck();
        // Assume you have a DatabaseReference reference to your Firebase database

        // Generate five random indices (questions) within the range
        List<Integer> randomIndices = generateRandomIndices(totalQuestions, 5);

        if(currentQuestionIndex == questionsToAnswer){
            finishQuiz();
            return;
        }



        for (int i = 0; i < 5; i++) {
            int randomIndex = randomIndices.get(i);

            // Query Firebase to get the question and its choices
            int finalI = i;
            databaseReference.child("professional_and_social_computing")
                    .child("question" + (randomIndex + 1)) // Firebase indices start at 1
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // Parse the question and choices from dataSnapshot
                            String questionText = dataSnapshot.child("text").getValue(String.class);
                            correctAnswer = dataSnapshot.child("correctChoice").getValue(String.class);
                            Map<String, String> choices = new HashMap<>();
                            for (DataSnapshot choiceSnapshot : dataSnapshot.child("choices").getChildren()) {
                                String choiceKey = choiceSnapshot.getKey();
                                String choiceValue = choiceSnapshot.getValue(String.class);
                                choices.put(choiceKey, choiceValue);
                            }

                            // Now you have the question and its choices, you can display them
                            // and set up radio buttons for the choices
                            Map<String, Object> quiz = new HashMap<>();
                            quiz.put("text", questionText); // Set the course name as needed
                            quiz.put("choices", choices);
                            quiz.put("correctChoice", correctAnswer);
                            quizzesRef.child(quizKey).child("question"+ (finalI +1)).setValue(quiz);
                           // Toast.makeText(Quiz.this, "Quiz "+ currentQuestionIndex, Toast.LENGTH_SHORT).show();


                            displayQuestion(questionText, choices);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle error
                        }
                    });
        }
    }

    // Generate unique random indices within a specified range
    private List<Integer> generateRandomIndices(int range, int count) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < range; i++) {
            indices.add(i);
        }

        Collections.shuffle(indices); // Shuffle the list of indices
        return indices.subList(0, count); // Return the first 'count' indices
    }

    // Display the question text and answer choices in your TextView and RadioButtons
    private void displayQuestion(String questionText, Map<String, String> choices) {

        // Set the question text in the TextView
        question.setText(questionText);

        // Set the answer choices in the RadioButtons
        radioButtonA.setText(choices.get("A"));
        radioButtonB.setText(choices.get("B"));
        radioButtonC.setText(choices.get("C"));
        radioButtonD.setText(choices.get("D"));
    }


    private void finishQuiz(){

//        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users/"+firebaseAuth.getUid()); // Replace "user1" with the current user's ID
//        DatabaseReference quizzesRef = userRef.child("quizzes");
//
//        String quizKey = quizzesRef.push().getKey(); // Generate a unique key for the quiz
//
//        // Prepare the quiz data
//        Map<String, Object> quizData = new HashMap<>();
//        quizData.put("courseName", "Course 1");
//        quizData.put("opponentId", opponentId);
//        // Add questions and answers as needed
//
//        // Set the quiz data under the generated key
//        quizzesRef.child(quizKey).setValue(quizData);

        FMCSend.pushNotification(
                Quiz.this,
                firebaseNotificationToken,
                "Challenge",
                playerName.getText().toString()+" Challenged You"
        );

        Intent intent = new Intent(Quiz.this, Results.class);
        intent.putExtra("score", score);
        intent.putExtra("playerLevel", playerLevel.getText().toString());
        intent.putExtra("playerName", playerName.getText().toString());
        intent.putExtra("playerProfilePic", playerImageToken);
        intent.putExtra("opponentName", oppName);
        intent.putExtra("opponentLevel", oppLevel);
        intent.putExtra("opponentProfilePic", oppProfileImage);
        intent.putExtra("status", false);
        startActivity(intent);

    }


    private void checkRadioButton(){
        // Get the ID of the selected radio button
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (radioButtonA.isChecked()){
            selectedAnswer = "A";
        }else if (radioButtonB.isChecked()){
            selectedAnswer = "B";
        } else if (radioButtonC.isChecked()) {
            selectedAnswer = "C";
        }else if (radioButtonD.isChecked()){
            selectedAnswer = "D";
        }else {
            selectedAnswer = null;
        }

        if (selectedAnswer.equals(correctAnswer)){
            score++;
            playerScore.setText(score+"");
        }
        currentQuestionIndex++;
        fetchQuestions();

    }


    // Send notification to Player2
    private void sendNotificationToPlayer2(String quizKey) {
        // Create a data payload with the quizKey
        Map<String, String> dataPayload = new HashMap<>();
        dataPayload.put("quizKey", quizKey);

        // Create an FCM message

        FMCSend.pushNotification(
                Quiz.this,
                "cOgDz1F_QtO190wZQ9ZtoS:APA91bHfUR-06TBiP5FRMpDHC6ZnUon4J3NQh28nGWFfEdxlR6A6U-Q4guYS2Svfbm15AyqprN5DOGkda4cbKwNKmB0Bj19imugbYdSK-SnWqrqD538ivfuA9fHt4M9FytT-AvYaJ3PQ",
                "Challenge",
                oppName+"Challenge You in Course Name"
        );



//        Message message = Message.builder()
//                .setNotification(Notification.builder()
//                        .setTitle("Quiz Notification")
//                        .setBody("Player1 has started a quiz.")
//                        .build())
//                .putAllData(dataPayload)
//                .setToken(firebaseNotificationToken) // Replace with Player2's FCM token
//                .build();
//
//        Messag
//
//        // Send the message
//        try {
//            String response = FirebaseMessaging.getInstance().send(message);
//            // Handle the response if needed
//        } catch (FirebaseMessagingException e) {
//            // Handle the exception
//        }
    }


}