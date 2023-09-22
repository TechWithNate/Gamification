package com.digi.learn;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private EditText etName;
    private EditText etMessage;
    private MaterialButton publishBtn;

    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();



        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();

                        // Log and toast
                        Log.d(TAG, "onCreate: Token" +  token);
                        System.out.println("TOKEN::: "+ token);
                        Toast.makeText(MainActivity.this, "Token "+token, Toast.LENGTH_SHORT).show();
                    }
                });


        publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etName.getText().toString().trim();
                String message = etMessage.getText().toString().trim();



                    FMCSend.pushNotification(
                            MainActivity.this,
                            token,
                            title,
                            message
                    );
                    Log.d(TAG, "onCreate: Token" +  token);
                    System.out.println("TOKEN::: "+ token);
                    Toast.makeText(MainActivity.this, "Token "+token, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "Token "+token, Toast.LENGTH_SHORT).show();
            }
        });

        Log.d(TAG, "onCreate: Token" +  token);
        System.out.println("TOKEN::: "+ token);
    }

    public void initViews(){
        etName = findViewById(R.id.name);
        etMessage = findViewById(R.id.message);
        publishBtn = findViewById(R.id.send_notification);
    }

}