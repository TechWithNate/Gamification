package com.digi.learn.Fragments;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.digi.learn.R;
import com.google.android.material.button.MaterialButton;

public class HomeFragment extends Fragment {

    View view;

    private MaterialButton selectOpponent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        initViews();

        selectOpponent.setOnClickListener(v -> {
            opponentPopUp();
        });

        return view;
    }


    //Open pop up to select opponent
    private void opponentPopUp() {
        CourseSelectionDialogFragment dialog = new CourseSelectionDialogFragment();
        dialog.show(getActivity().getSupportFragmentManager(), "CourseSelectionDialogFragment");
    }


    //    Initialize Views
    private void initViews(){
        selectOpponent = view.findViewById(R.id.select_opponent_btn);
    }




}
