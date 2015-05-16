package com.example.android.bluetoothchat;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class ParsePushApplication extends Application {

    @Override
    public void onCreate(){
        Parse.initialize(this, "zlAyTHU9HtVINDpz6U98s0s62eIHgM4q26ZwYvZj", "nrqdMzZ4QbkkeCbARW64KH4eL9r30hQ1WGc7TMsQ");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}