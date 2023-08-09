package com.digi.learn.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digi.learn.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FeedbackFragment extends Fragment {

    private View view;
    private EditText feedback;
    private MaterialButton submit;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.feedback_fragment, container, false);
        initViews();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });

        return view;

    }

    private void initViews(){
        feedback = view.findViewById(R.id.feedback);
        submit = view.findViewById(R.id.submitBtn);
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void submitFeedback(){
        if (feedback.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please Enter a message", Toast.LENGTH_SHORT).show();
        }else {
            String message = feedback.getText().toString();

        }
    }

}
