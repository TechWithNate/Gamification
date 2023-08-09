package com.digi.learn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.digi.learn.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
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
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ImagePost extends AppCompatActivity {

    private ImageView post_image;
    private EditText caption;
    private FloatingActionButton publish_btn;

    private static int PICK_IMAGE = 100;
    private Uri imagePath;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    String name;
    String profilePicture;
    String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_post);

        initViews();

        //Get name and image url of user from the database to set as author of post
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (null != user){
                    name = user.getFirstname();
                    profilePicture = user.getProfilePicture();
                    Toast.makeText(ImagePost.this, "User name: "+name, Toast.LENGTH_SHORT).show();
                    Toast.makeText(ImagePost.this, "Profile Picture Url: "+profilePicture, Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ImagePost.this, "An error occurred: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Get image from gallery when image picker is clicked
        post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        //Publish post
        publish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishPost();
            }
        });

    }

    //Initializing views
    private void initViews(){
        post_image = findViewById(R.id.post_image);
        caption = findViewById(R.id.caption);
        publish_btn = findViewById(R.id.publish_btn);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
    }

    public void pickImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == PICK_IMAGE && requestCode == RESULT_OK){
            if (data != null) {
                imagePath = data.getData();
                post_image.setImageURI(imagePath);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);



    }

    //Send post to database
    private void publishPost(){
        if (imagePath.toString().isEmpty()){
            Toast.makeText(this, "Select an image", Toast.LENGTH_SHORT).show();
        }else if (caption.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter a post description", Toast.LENGTH_SHORT).show();
        }else {

            String timeStamp = String.valueOf(System.currentTimeMillis());
           StorageReference postImage = storageReference.child("Post images").child(imagePath.getLastPathSegment());
           postImage.putFile(imagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                   while (!uriTask.isSuccessful());

                   String downloadUrl = uriTask.getResult().toString();

                   if (uriTask.isSuccessful()){
                       Map<Object, String> postData = new HashMap<>();
                       postData.put("uID", firebaseAuth.getUid());
                       postData.put("name", name);
                       postData.put("profilePicture", profilePicture);
                       postData.put("caption", caption.getText().toString());
                       postData.put("postImage", downloadUrl);
                       postData.put("timeStamp", timeStamp);


                       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
                       databaseReference.child(timeStamp).setValue(postData).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void unused) {
                               Toast.makeText(ImagePost.this, "Published", Toast.LENGTH_SHORT).show();
                               caption.setText("");
                               finish();
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(ImagePost.this, "Failed to publish post"+e.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       });

                   }



               }
           });
        }
    }
}