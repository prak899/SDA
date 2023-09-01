package com.example.testing.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testing.adapter.RecyclerAdapter;
import com.example.testing.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private LinkedList<String> linkedList;
    private boolean isNewAvailable;
    private int age = 19;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        activityMainBinding.recyclerData
                .setLayoutManager(new LinearLayoutManager(this));
        getSampleDataList();
        isNewAvailable(activityMainBinding, age);
        recyclerViewData(activityMainBinding);

    }

    private void isNewAvailable(ActivityMainBinding activityMainBinding, int age) {
        isNewAvailable = getAge(age);
    }
    private boolean getAge(int age){
        boolean isApplicable ;
        isApplicable = age > 18;

        return isApplicable;
    }
    @SuppressLint("NotifyDataSetChanged")
    private void recyclerViewData(ActivityMainBinding activityMainBinding) {
        linkedList  = new LinkedList<>(getSampleDataList());
        IntStream.range(0, linkedList.size()).forEach(i -> {

            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(
                    this, linkedList,
                    getSupportFragmentManager(), isNewAvailable);
            activityMainBinding.recyclerData.setAdapter(recyclerAdapter);
            recyclerAdapter.notifyDataSetChanged();

        });
    }

    private List<String> getSampleDataList() {
        List<String> stringList = new ArrayList<>();
        try {
            for (int i = 0; i < 50; i++) {
                stringList.add("dummyData to check whether it's showing or not ->" + i);
            }
        } catch (Exception e) {
            Log.d(TAG, "getSampleDataList: "+e.getMessage());
        }
        return stringList;
    }
}