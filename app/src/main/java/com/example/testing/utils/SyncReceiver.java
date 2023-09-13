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
        DataSyncHelper.syncData(context);
        Intent syncCompleteIntent = new Intent(SYNC_ACTION);
        context.sendBroadcast(syncCompleteIntent);
    }
}


