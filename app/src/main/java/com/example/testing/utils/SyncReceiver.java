package com.example.testing.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class SyncReceiver extends BroadcastReceiver {

    public static final String SYNC_ACTION = "com.example.testing.SYNC_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Syncing data...", Toast.LENGTH_SHORT).show();
        /*Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            MainActivity mainActivity = new MainActivity();
            mainActivity.syncData();
            Log.d("IntentService", "onHandleIntent: service started");
        });*/
        // You can also send a broadcast to notify other components of the sync completion.
        Intent syncCompleteIntent = new Intent(SYNC_ACTION);
        context.sendBroadcast(syncCompleteIntent);
    }
}


