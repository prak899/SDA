package com.example.testing.activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
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
import com.example.testing.enumartion.TaskStatus;
import com.example.testing.model.DataModel;
import com.example.testing.utils.AppInitializer;
import com.example.testing.utils.DatabaseSyncService;
import com.example.testing.utils.EncryptionUtil;
import com.example.testing.utils.RootDetection;
import com.example.testing.utils.RootUtil;
import com.example.testing.utils.SyncReceiver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private LinkedList<String> linkedList;
    private boolean isNewAvailable;
    private ActivityMainBinding activityMainBinding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final int SYNC_ALARM_REQUEST_CODE = 0;
    private SyncReceiver syncReceiver;
    private DataModel progress, todo, completed, cancelled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        initialize();
        RootDetection rootDetection = new RootDetection();
        progress = new DataModel("progress", TaskStatus.IN_PROGRESS);
        todo = new DataModel("todo", TaskStatus.TODO);
        completed = new DataModel("completed", TaskStatus.COMPLETED);
        cancelled = new DataModel("cancelled", TaskStatus.CANCELED);


        Log.d(TAG, "onCreate: "+
                progress.getData()+"\n"+

                progress.getTaskStatus()+"\n"+
                todo.getTaskStatus()+"\n"+
                completed.getTaskStatus()+"\n"+
                cancelled.getTaskStatus());

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        activityMainBinding.recyclerData
                .setLayoutManager(new LinearLayoutManager(this));

        if (!rootDetection.isDeviceRooted()) {
            alertDialog("You can't run this app in your device (Rooted Device error)");

        } else {
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

        // Register the BroadcastReceiver for receiving sync updates
        syncReceiver = new SyncReceiver();
        IntentFilter intentFilter = new IntentFilter(SyncReceiver.SYNC_ACTION);
        registerReceiver(syncReceiver, intentFilter);

        if (sharedPreferences != null) {
            Map<String, ?> allEntries = sharedPreferences.getAll();

            if (allEntries != null && !allEntries.isEmpty()) {
                // SharedPreferences file exists and contains data
                // You can perform actions here if needed
                boolean isDataSync = sharedPreferences.getBoolean("isDataSync", false);

                activityMainBinding.syncing.setChecked(isDataSync);
            } else {
                Toast.makeText(this, "File empty", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Does not exist", Toast.LENGTH_SHORT).show();
        }

        editor.putBoolean("key_name", true); // Storing boolean - true/false
        editor.putString("key_name1", "string value"); // Storing string
        editor.putInt("key_name2", 90); // Storing integer
        editor.putFloat("key_name3", 99); // Storing float
        editor.putLong("key_name4", 123456); // Storing long

        editor.commit(); // commit changes
        activityMainBinding.syncing.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
               // AppInitializer.initialize(MainActivity.this);
                scheduleSync();
            } else {
                Toast.makeText(MainActivity.this, "Disabled", Toast.LENGTH_SHORT).show();

            }
        });
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
    public List<String> getSampleDataList() {

        List<String> stringList = new ArrayList<>();
        try {
            String secretKey = EncryptionUtil.generateSecretKey();
            for (int i = 0; i < 5; i++) {
                String printableData = "dummyData to check whether it's showing or not ->" + i;
                String encryptedText = EncryptionUtil.encrypt(printableData, secretKey);
                String decryptedText = EncryptionUtil.decrypt(encryptedText, secretKey);
                stringList.add(decryptedText);
            }
        } catch (Exception e) {
            Log.d(TAG, "getSampleDataList: " + e.getMessage());
        }
        return stringList;
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the BroadcastReceiver when the activity is destroyed
        unregisterReceiver(syncReceiver);
    }

    // Button click handler to manually trigger data sync
    public void syncDataManually(View view) {
        // You can call your data sync logic here
        Toast.makeText(this, "Manually syncing data...", Toast.LENGTH_SHORT).show();
    }
    Calendar calendar = Calendar.getInstance();
    // Schedule the daily sync alarm when the "Schedule Sync" button is clicked
    public void scheduleSync() {
        editor.putBoolean("isDataSync", true);
        editor.apply();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, SyncReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, SYNC_ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE);

        // Set the time for the alarm to trigger (e.g., 2:00 PM daily)

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 14); // 2:00 PM
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // Schedule the alarm to repeat daily
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );
        countDownTimer();
        Toast.makeText(this, "Sync alarm scheduled daily at 2:00 PM", Toast.LENGTH_LONG).show();
    }
    private void countDownTimer(){
        // Calculate time remaining until the next alarm
        Calendar currentCalendar = Calendar.getInstance();
        long currentTimeMillis = currentCalendar.getTimeInMillis();

        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.setTimeInMillis(calendar.getTimeInMillis()); // Use the alarm time you set

        long alarmTimeMillis = alarmCalendar.getTimeInMillis();

        // Calculate the time remaining in milliseconds
        long timeRemainingMillis = alarmTimeMillis - currentTimeMillis;

        // Ensure the time remaining is positive
        if (timeRemainingMillis < 0) {
            timeRemainingMillis += AlarmManager.INTERVAL_DAY;
        }

        // Calculate hours, minutes, and seconds from timeRemainingMillis
        /*long hours = TimeUnit.MILLISECONDS.toHours(timeRemainingMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeRemainingMillis) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeRemainingMillis) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minutes);

        // You now have `hours`, `minutes`, and `seconds` representing the time remaining until the alarm.

        String countdownText = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        activityMainBinding.countDown.setText(countdownText);
        Log.d(TAG, "countDownTimer: "+countdownText);*/
        CountDownTimer countDownTimer = new CountDownTimer(timeRemainingMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Calculate hours, minutes, and seconds from millisUntilFinished
                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                        - TimeUnit.HOURS.toMinutes(hours);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                        - TimeUnit.HOURS.toSeconds(hours)
                        - TimeUnit.MINUTES.toSeconds(minutes);

                // Format the time and update the UI
                String countdownText = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                activityMainBinding.countDown.setText(countdownText);
                Log.d(TAG, "countDownTimer: " + countdownText);
            }

            @Override
            public void onFinish() {
                // Handle the countdown timer finished event if needed
            }
        };

        // Start the countdown timer
        countDownTimer.start();
    }
}