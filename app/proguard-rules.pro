# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep class com.example.testing.activity.MainActivity { *; }
-keepclassmembers class com.example.testing.activity.MainActivity. **{
    public *;
}
-keep class com.example.testing.activity.   ** { *; }
-keep class com.example.testing.adapter.    ** { *; }
-keep class com.example.testing.api.        ** { *; }
-keep interface com.example.testing.api.ApiService. ** { *; }
-keepclassmembers class com.example.testing.activity. ** { *; }
-keep class com.example.testing.fragments. ** { *; }









