package com.digi.learn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private MaterialButton loginBtn;
    private TextView signUp;
    private FirebaseAuth firebaseAuth;
    ProgressBar loadingPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

        loginBtn.setOnClickListener(v -> {

            checkFields();
        });

        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, CreateAccount.class);
            startActivity(intent);
            finish();
        });

    }

    private void initViews(){
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        signUp = findViewById(R.id.signUp);
        loadingPb = findViewById(R.id.loadingPb);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void checkFields(){
        if (email.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter your Index Number", Toast.LENGTH_SHORT).show();
        }else if (password.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter your Password", Toast.LENGTH_SHORT).show();
        }else {
            loadingPb.setVisibility(View.VISIBLE);
            loginUser(email.getText().toString(), password.getText().toString());
        }

    }

    private void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            loadingPb.setVisibility(View.GONE);
                            updateUI(firebaseAuth.getCurrentUser());
                        }else {
                            Toast.makeText(Login.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            loadingPb.setVisibility(View.GONE);
                        }
                    }
                });

    }


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (null != currentUser){
            updateUI(currentUser);
        }

    }

    private void updateUI(FirebaseUser user){
        Intent intent = new Intent(Login.this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}