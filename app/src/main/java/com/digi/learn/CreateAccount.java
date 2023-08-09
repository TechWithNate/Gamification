package com.digi.learn;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digi.learn.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class CreateAccount extends AppCompatActivity {


    TextInputLayout textField;
    EditText firstName;
    EditText lastname;
    EditText studentID;
    EditText email;
    EditText password;
    EditText confirmPassword;
    MaterialButton createAccountBtn;
    TextView login;
    AutoCompleteTextView level;
    ProgressBar loadingPb;

    String user_level;

    FirebaseAuth firebaseAuth;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        initViews();

        textField = findViewById(R.id.level_textField);

        List<String> items = Arrays.asList("Level 100", "Level 200", "Level 300");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, items);
        level.setAdapter(adapter);

        level.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                user_level = (String) parent.getItemAtPosition(position);
            }
        });

        createAccountBtn.setOnClickListener(v -> checkFields());


        login.setOnClickListener(v -> {
            Intent intent = new Intent(CreateAccount.this, Login.class);
            startActivity(intent);
            finish();
        });

    }

    private void initViews(){
        firstName = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        studentID = findViewById(R.id.studentID);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        createAccountBtn = findViewById(R.id.create_account);
        login = findViewById(R.id.login);
        level = findViewById(R.id.level);
        loadingPb = findViewById(R.id.loadingPb);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private void checkFields(){
        if (firstName.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter First Name", Toast.LENGTH_SHORT).show();
            firstName.requestFocus();
        }else if (lastname.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Last Name", Toast.LENGTH_SHORT).show();
            lastname.requestFocus();
        }else if (studentID.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your ID", Toast.LENGTH_SHORT).show();
            studentID.requestFocus();
        }else if (textField.toString().isEmpty()){
            Toast.makeText(this, "Select Level", Toast.LENGTH_SHORT).show();
        }else if (email.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            email.requestFocus();
        }else if (password.getText().toString().length() < 6){
            Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            password.setText("");
            confirmPassword.setText("");
        }else if (password.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            password.requestFocus();
        }else if (!confirmPassword.getText().toString().equals(password.getText().toString())){
            Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
            confirmPassword.requestFocus();
        }else {
            loadingPb.setVisibility(View.VISIBLE);
            createAccount(email.getText().toString(), password.getText().toString());
        }

    }


    private void createAccount(String email, String password){

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            loadingPb.setVisibility(View.GONE);
                            Intent intent = new Intent(CreateAccount.this, EditProfile.class);
                            intent.putExtra("firstName", firstName.getText().toString());
                            intent.putExtra("lastName", lastname.getText().toString());
                            intent.putExtra("email", email);
                            intent.putExtra("level", level.getText().toString());
                            intent.putExtra("studentID", studentID.getText().toString());
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(CreateAccount.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(CreateAccount.this, Home.class);
            startActivity(intent);
            finish();

    }
}