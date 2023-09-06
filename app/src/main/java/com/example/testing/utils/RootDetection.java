package com.example.testing.utils;

import android.annotation.SuppressLint;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class RootDetection {
    public boolean isDeviceRooted() {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
    }

    // Check for common signs of rooted or jail-broken devices.
    private static boolean checkRootMethod1() {
        String[] paths = {"/system/bin/su", "/system/xbin/su", "/sbin/su", "/su/bin/su"};
        for (String path : paths) {
            if (new File(path).exists()) {
                return true;
            }
        }
        return false;
    }

    // Check for the presence of the "su" command.
    private static boolean checkRootMethod2() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return bufferedReader.readLine() != null;
        } catch (Exception e) {
            return false;
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    // Check for the presence of a superuser application package.
    @SuppressLint("PrivateApi")
    private static boolean checkRootMethod3() {
        String[] superuserPackages = {"com.koushikdutta.superuser", "com.noshufou.android.su", "eu.chainfire.supersu"};
        for (String packageName : superuserPackages) {
            try {
                Class.forName("android.app.ApplicationPackageManager")
                        .getMethod("getPackageInfo", String.class, int.class)
                        .invoke(null, packageName, 0);
                return true;
            } catch (Exception e) {
                // Superuser app not found
            }
        }
        return false;
    }
}

