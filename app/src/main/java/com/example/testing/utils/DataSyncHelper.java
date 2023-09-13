package com.example.testing.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class DataSyncHelper {
    public static void syncData(Context context) {
        for (int i = 0; i < 10; i++){
            Toast.makeText(context, "Data Sync-"+i, Toast.LENGTH_SHORT).show();
        }
    }
}
