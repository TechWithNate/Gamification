package com.digi.learn;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.digi.learn.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateProfile extends AppCompatActivity {


    String studentLevel;
    private ImageView user_img;
    private String username;
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText studentID;
    private EditText tel;
    private EditText fb;
    private EditText git;
    private EditText linked_in;
    private EditText bio;
    private MaterialButton save_btn;

    String studentIdPattern = "^[0-9]{8}[a-zA-Z]$";
    // Regular expression pattern for a valid name (only alphabets)
    String namePattern = "^[a-zA-Z]+$";

    private static final int PICK_IMAGE = 100;
    private Uri imagePath;

    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private String imageUriAccessToken;
    private int points = 10;
    private int gameLevel = 1;
    private String firebaseNotificationToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        initViews();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        firstname.setText(getIntent().getStringExtra("firstName"));
        lastname.setText(getIntent().getStringExtra("lastName"));
        email.setText(getIntent().getStringExtra("email"));
        studentID.setText(getIntent().getStringExtra("studentID"));
        studentLevel = getIntent().getStringExtra("level");



        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // Get new FCM registration token
                        firebaseNotificationToken = task.getResult();

                    }
                });
        user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserImage();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFields();
            }
        });

    }

    private void checkFields() {

        if (firstname.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter first name", Toast.LENGTH_SHORT).show();
            firstname.requestFocus();
        }else if (lastname.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter last name", Toast.LENGTH_SHORT).show();
            lastname.requestFocus();
        }else if (!firstname.getText().toString().trim().matches(namePattern)){
            Toast.makeText(this, "Enter a valid first name", Toast.LENGTH_SHORT).show();
            firstname.requestFocus();
        }else if (!lastname.getText().toString().trim().matches(namePattern)){
            Toast.makeText(this, "Enter a valid last name", Toast.LENGTH_SHORT).show();
            lastname.requestFocus();
        } else if (email.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter your email address", Toast.LENGTH_SHORT).show();
            email.requestFocus();
        }else if (studentID.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter your students ID", Toast.LENGTH_SHORT).show();
            studentID.requestFocus();
        }else if (!studentID.getText().toString().trim().matches(studentIdPattern)){
            // Invalid student ID, show an error message to the user
            Toast.makeText(this, "Invalid student ID. Please enter a valid ID.", Toast.LENGTH_SHORT).show();
            studentID.requestFocus();
        } else if (bio.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter your bio", Toast.LENGTH_SHORT).show();
            bio.requestFocus();
        }else if (imagePath.toString().isEmpty()){
            Toast.makeText(this, "Select an image", Toast.LENGTH_SHORT).show();
        }else {
            setData();
            Intent intent = new Intent(CreateProfile.this, Home.class);
            startActivity(intent);
            finish();
        }

    }

    private void initViews(){
        user_img = findViewById(R.id.user_img);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        studentID = findViewById(R.id.studentID);
        tel = findViewById(R.id.tel);
        fb = findViewById(R.id.fb_link);
        git = findViewById(R.id.git_hub);
        linked_in = findViewById(R.id.linked_in);
        bio = findViewById(R.id.bio);
        save_btn = findViewById(R.id.save_btn);
    }

    private void setUserImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            if (data != null) {
                imagePath = data.getData();
                user_img.setImageURI(imagePath);
                sendImage();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setData(){
        // TODO: set profile data
        sendToRealtimeDb();
    }

    private void sendToRealtimeDb() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(firebaseAuth.getUid());

        User user = new User(firebaseAuth.getUid(), firstname.getText().toString(), lastname.getText().toString(), studentLevel, studentID.getText().toString(), email.getText().toString(), imageUriAccessToken, bio.getText().toString(), linked_in.getText().toString(), git.getText().toString(), tel.getText().toString(), fb.getText().toString(), points, gameLevel, firebaseNotificationToken);
        databaseReference.setValue(user);
        Toast.makeText(this, "Profile Created Successfully", Toast.LENGTH_SHORT).show();
        sendToFireStore();

    }

    private void sendImage() {
        StorageReference imageRef = storageReference.child("images").child(firebaseAuth.getUid()).child("Profile Pics");

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageUriAccessToken = uri.toString();
                        Toast.makeText(CreateProfile.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateProfile.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendToFireStore(){
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        Map<String, Object> userData = new HashMap<>();
        userData.put("firstname", firstname.getText().toString());
        userData.put("lastname", lastname.getText().toString());
        userData.put("email", email.getText().toString());
        userData.put("studentId", studentID.getText().toString());
        userData.put("tel", tel.getText().toString());
        userData.put("git", git.getText().toString());
        userData.put("fb", fb.getText().toString());
        userData.put("linkedIn", linked_in.getText().toString());
        userData.put("userBio", bio.getText().toString());
        userData.put("profilePicture", imageUriAccessToken);
        userData.put("points", points);
        userData.put("position", 0);
        userData.put("gameLevel", gameLevel);
        userData.put("firebaseNotificationToken", firebaseNotificationToken);



        documentReference.set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(CreateProfile.this, "Data Added To FireStore", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateProfile.this, "Failed to add to FireStore"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Create Firebase Notification Toke
    private void notificationToken(){

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();

                    }
                });

    }

}