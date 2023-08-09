package com.digi.learn.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digi.learn.R;

public class AboutFragment extends Fragment {

    View view;
    TextView mainText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.about_fragment, container, false);
        mainText = view.findViewById(R.id.mainText);

        String text = "We are HND final year students from the Department of computer Science, Accra Technical University (ATU), embarked on a research project centered around the theme \"Improving Teaching and Learning of Computer Science Theoretical Courses Through Gamification.\" Our main objective was to address the existing challenge of effective and innovative pedagogy in the domain of teaching and learning.\n" +
                "\n" +
                "Initially, our research focused on the investigation and analysis of select Computer Science theoretical courses offered at ATU. After conducting thorough research and analyzing the gathered data, we formulated a solution in the form of a mobile-based computer game. The primary aim of this game was to empower students to enhance their engagement with learning Computer Science theoretical courses.\n" +
                "\n" +
                "The project was a collective effort by the following students:\n" +
                "\n" +
                "Dzreke-Poku Nathan Kodzo\n" +
                "Nyaabila Matthew\n" +
                "Lartey Emmanuel Kojo\n" +
                "\n" +
                "Project supervised by Prof. Nana Yaw Asabere.\n" +
                "Year 2023";
        mainText.setText(text);

        return view;

    }
}
