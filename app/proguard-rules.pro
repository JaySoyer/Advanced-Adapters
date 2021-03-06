# Maintains Line numbers for stacktraces
-keepattributes SourceFile,LineNumberTable

# ButterKnife Specific
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# Advanced-Adapters Specific
-keepclassmembers class com.sawyer.advadapters.widget.JSONAdapter {
	boolean isFilteredOut(...);
}
-keepclassmembers class * extends com.sawyer.advadapters.widget.JSONAdapter {
	boolean isFilteredOut(...);
}

# Remove all Logging except errors
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
}

