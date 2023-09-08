package com.example.testing.utils;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.testing.activity.MainActivity;

public class DatabaseSyncService extends IntentService {

    public DatabaseSyncService() {
        super("DatabaseSyncService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // Perform your database synchronization here
        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(() -> {
            MainActivity mainActivity = new MainActivity();
            mainActivity.syncData();
        });
    }
}

