package com.example.nal;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class BackService extends IntentService {
    // Must create a default constructor
    public BackService() {
        // Used to name the worker thread, important only for debugging.
        super("back-service");
    }

    @Override
    public void onCreate() {
        super.onCreate(); // if you override onCreate(), make sure to call super().
        // If a Context object is needed, call getApplicationContext() here.
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        // This describes what will happen when service is triggered
    }
}