package com.digi.learn.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.digi.learn.R;
import com.digi.learn.SelectOpponent;

public class CourseSelectionDialogFragment extends DialogFragment {

    private String courseName;
    private View dialogView;
    private RelativeLayout courseLayout1, courseLayout2,
            courseLayout3, courseLayout4,
            courseLayout5, courseLayout6,
            courseLayout7, courseLayout8;

    private TextView course1, course2,
            course3, course4,
            course5, course6,
            course7, course8;


    @SuppressLint("SetTextI18n")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create an AlertDialog.Builder and set the layout and buttons
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        // Inflate the custom layout for course selection
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.courses_layout, null);
        initViews();

        course1.setText("Professional And Social  Computing");
        course2.setText("Computer Organization and Architecture");
        course3.setText("Software Engineering");
        course4.setText("Research Methodology");
        course5.setText("System Analysis And Design");
        course6.setText("Data Communication and Telecommunication Technologies");
        course7.setText("Data Structures and Algorithm");
        course8.setText("Operating Systems");

        // Set click listeners for each RelativeLayout
        courseLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click on courseLayout1
                // You can perform actions like selecting the course, updating UI, etc.
                selectOpponent("course1");

            }
        });

        courseLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click on courseLayout2
                selectOpponent("course2");
            }
        });

        courseLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click on courseLayout2
                selectOpponent("course3");
            }
        });

        courseLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click on courseLayout2
                selectOpponent("course4");
            }
        });

        courseLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click on courseLayout2
                selectOpponent("course5");
            }
        });


        courseLayout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click on courseLayout2
                selectOpponent("course6");
            }
        });


        courseLayout7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click on courseLayout2
                selectOpponent("course7");
            }
        });


        courseLayout8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click on courseLayout2
                selectOpponent("course8");
            }
        });






        // Add more click listeners for other layouts

        builder.setView(dialogView);


        return builder.create();
    }

    private void initViews() {
        // Get references to your RelativeLayout elements
        courseLayout1 = dialogView.findViewById(R.id.course_layout_1);
        courseLayout2 = dialogView.findViewById(R.id.course_layout_2);
        courseLayout3 = dialogView.findViewById(R.id.course_layout_3);
        courseLayout4 = dialogView.findViewById(R.id.course_layout_4);
        courseLayout5 = dialogView.findViewById(R.id.course_layout_5);
        courseLayout6 = dialogView.findViewById(R.id.course_layout_6);
        courseLayout7 = dialogView.findViewById(R.id.course_layout_7);
        courseLayout8 = dialogView.findViewById(R.id.course_layout_8);


        course1 = dialogView.findViewById(R.id.course1);
        course2 = dialogView.findViewById(R.id.course2);
        course3 = dialogView.findViewById(R.id.course3);
        course4 = dialogView.findViewById(R.id.course4);
        course5 = dialogView.findViewById(R.id.course5);
        course6 = dialogView.findViewById(R.id.course6);
        course7 = dialogView.findViewById(R.id.course7);
        course8 = dialogView.findViewById(R.id.course8);
    }

    private void selectOpponent(String courseId) {
        Intent intent = new Intent(getContext(), SelectOpponent.class);
        intent.putExtra("courseId", courseId);
        startActivity(intent);


    }
}

