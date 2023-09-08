package com.example.testing.activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testing.adapter.RecyclerAdapter;
import com.example.testing.api.ApiService;
import com.example.testing.api.RetrofitClient;
import com.example.testing.databinding.ActivityMainBinding;
import com.example.testing.utils.AppInitializer;
import com.example.testing.utils.DatabaseSyncService;
import com.example.testing.utils.RootDetection;
import com.example.testing.utils.RootUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private LinkedList<String> linkedList;
    private boolean isNewAvailable;
    private ActivityMainBinding activityMainBinding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        initialize();
        RootDetection rootDetection = new RootDetection();



        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        activityMainBinding.recyclerData
                .setLayoutManager(new LinearLayoutManager(this));

        if (!rootDetection.isDeviceRooted()) {
            alertDialog();

            Log.d(TAG, "onCreate: "+ rootDetection.isDeviceRooted());
        }
        else{
            //Toast.makeText(this, "You can continue", Toast.LENGTH_SHORT).show();
            getSampleDataList();
            int age = 19;
            isNewAvailable(activityMainBinding, age);
            recyclerViewData();
            boolean isDataSync = sharedPreferences.getBoolean("isDataSync", false);
            Log.d(TAG, "onCreate: "+ isDataSync);
            Toast.makeText(this, "Your data sync is- "+ isDataSync, Toast.LENGTH_SHORT).show();
            //getLatestData();
        }
    }

    private void initialize() {
        sharedPreferences = getApplicationContext().getSharedPreferences("dataSyncPrefs", 0);
        editor = sharedPreferences.edit();

        if (sharedPreferences != null) {
            Map<String, ?> allEntries = sharedPreferences.getAll();

            if (allEntries != null && !allEntries.isEmpty()) {
                // SharedPreferences file exists and contains data
                // You can perform actions here if needed
                boolean isDataSync = sharedPreferences.getBoolean("isDataSync", false);
                Toast.makeText(this, "your data "+isDataSync, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "File empty", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Does not exist", Toast.LENGTH_SHORT).show();
        }

        editor.putBoolean("key_name", true); // Storing boolean - true/false
        editor.putString("key_name", "string value"); // Storing string
        editor.putInt("key_name", 90); // Storing integer
        editor.putFloat("key_name", 99); // Storing float
        editor.putLong("key_name", 123456); // Storing long

        editor.commit(); // commit changes
        activityMainBinding.syncing.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                AppInitializer.initialize(MainActivity.this);

            } else {
                Toast.makeText(MainActivity.this, "Disabled", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void alertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You can't run this app in your device (Rooted Device error)")
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
    public List<String> getSampleDataList() {
        List<String> stringList = new ArrayList<>();
        try {
            for (int i = 0; i < 10; i++) {
                stringList.add("dummyData to check whether it's showing or not ->" + i);
            }
        } catch (Exception e) {
            Log.d(TAG, "getSampleDataList: " + e.getMessage());
        }
        return stringList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    /*private void getLatestData(){
        Call<RequestBody> requestBodyCall = apiService.getApiResponse();
        requestBodyCall.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(@NonNull Call<RequestBody> call, @NonNull Response<RequestBody> response) {
                if (null != response.body()){
                    String responseData = response.body().getData();
                    Log.d(TAG, "onResponse: "+response.body().getMsg());
                }

            }

            @Override
            public void onFailure(@NonNull Call<RequestBody> call, @NonNull Throwable t) {

            }
        });

    }*/
    public void syncData(){

        try {
            editor.putBoolean("isDataSync", true);
            editor.apply();
        } catch (Exception e) {
            Log.e(TAG, "syncData: "+ e.getMessage());
        }

    }
}