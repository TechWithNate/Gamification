package com.digi.learn;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.digi.learn.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateTextPost extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private EditText post;
    private FloatingActionButton publishBtn;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    String name;
    String imageUri;
    String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_text_post);
        initViews();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (null != user){
                    name = user.getFirstname();
                    imageUri = user.getProfilePicture();
                    Toast.makeText(CreateTextPost.this, "Name: "+name, Toast.LENGTH_SHORT).show();
                    Toast.makeText(CreateTextPost.this, "Image "+imageUri, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onDataChange: "+ name);
                    Log.d(TAG, "onDataChange: "+ imageUri);
                }else {
                    Toast.makeText(CreateTextPost.this, "User is empty", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishPost();
            }
        });
    }

    private void initViews(){
        toolbar = findViewById(R.id.topAppBar);
        post = findViewById(R.id.post);
        publishBtn = findViewById(R.id.publishBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
    }

    private void publishPost(){
        if (post.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter a post", Toast.LENGTH_SHORT).show();
        }else {
            String timeStamp = String.valueOf(System.currentTimeMillis());

            Map<Object, String> postData = new HashMap<>();
            postData.put("name", name);
            postData.put("post", post.getText().toString());
            postData.put("time", timeStamp);
            postData.put("userImage", imageUri);

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
            databaseReference.child(timeStamp).setValue(postData).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(CreateTextPost.this, "Published", Toast.LENGTH_SHORT).show();
                    post.setText("");
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateTextPost.this, "Failed to publish post"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}