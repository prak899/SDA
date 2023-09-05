package com.example.testing.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testing.adapter.RecyclerAdapter;
import com.example.testing.databinding.ActivityMainBinding;
import com.example.testing.utils.RootUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private LinkedList<String> linkedList;
    private boolean isNewAvailable;
    ActivityMainBinding activityMainBinding;
    private RootUtil rootUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        rootUtil = new RootUtil();
        activityMainBinding.recyclerData
                .setLayoutManager(new LinearLayoutManager(this));

        if (rootUtil.isDeviceRooted())
            alertDialog("You can't run this app in your device (Rooted Device error)");
        else{
            Toast.makeText(this, "You can continue", Toast.LENGTH_SHORT).show();
            getSampleDataList();
            int age = 19;
            isNewAvailable(activityMainBinding, age);
            recyclerViewData();
        }
    }
    private void alertDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Exit", (dialog, id)
                        -> MainActivity.this.finish());
                /*.setNegativeButton("No", (dialog, id)
                        -> dialog.cancel());*/
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void isNewAvailable(ActivityMainBinding activityMainBinding, int age) {
        isNewAvailable = getAge(age);
    }

    private boolean getAge(int age) {
        boolean isApplicable;
        isApplicable = age > 18;

        return isApplicable;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void recyclerViewData() {
        linkedList = new LinkedList<>(getSampleDataList());
        IntStream.range(0, linkedList.size()).forEach(i -> {

            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(
                    this, linkedList,
                    getSupportFragmentManager(), isNewAvailable);
            activityMainBinding.recyclerData.setAdapter(recyclerAdapter);
            recyclerAdapter.notifyDataSetChanged();

        });
    }

    @NonNull
    private List<String> getSampleDataList() {
        List<String> stringList = new ArrayList<>();
        try {
            for (int i = 0; i < 50; i++) {
                stringList.add("dummyData to check whether it's showing or not ->" + i);
            }
        } catch (Exception e) {
            Log.d(TAG, "getSampleDataList: " + e.getMessage());
        }
        return stringList;
    }
}