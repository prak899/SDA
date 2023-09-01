package com.example.testing.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testing.R;

public class SellFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_buy, container, false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String receivedData = arguments.getString("carData"); // Replace "key" with the actual key
            TextView tv = rootView.findViewById(R.id.carDescription);
            tv.setText(receivedData);
        }
        return rootView;
    }
}