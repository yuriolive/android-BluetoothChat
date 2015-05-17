/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.bluetoothchat;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.print.PrintHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import com.example.android.bluetoothchat.entities.MapID;
import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;
import com.example.android.common.logger.LogFragment;
import com.example.android.common.logger.LogWrapper;
import com.example.android.common.logger.MessageOnlyLogFilter;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class FBlogin extends SampleActivityBase {

    public static final String TAG = "FBlogin";
    public static final String APPLICATION_ID = "zlAyTHU9HtVINDpz6U98s0s62eIHgM4q26ZwYvZj";
    public static final String CLIENT_KEY = "nrqdMzZ4QbkkeCbARW64KH4eL9r30hQ1WGc7TMsQ";
    private CallbackManager callbackManager;


    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "teste");

        FacebookSdk.sdkInitialize(getApplicationContext());

        //Instantianting next page
        final Intent nextpage = new Intent(FBlogin.this, MainActivity.class);
        if(AccessToken.getCurrentAccessToken() != null) { Log.d(TAG, "FOI"); startActivity(nextpage); }
        // Enable Local Datastore.
        //Parse.enableLocalDatastore(getApplicationContext());
        ParseObject.registerSubclass(MapID.class);
        Parse.initialize(this, "zlAyTHU9HtVINDpz6U98s0s62eIHgM4q26ZwYvZj", "nrqdMzZ4QbkkeCbARW64KH4eL9r30hQ1WGc7TMsQ");

        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.fb_layout);

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        Log.d(TAG, "11111");
                        if (bluetoothAdapter == null) {
                            Log.d(TAG, "device does not support bluetooth");
                            return;
                        }
                        String myMACAddress = bluetoothAdapter.getAddress();
                        String facebookID = loginResult.getAccessToken().getUserId();

                        ParseRequest.UpdateorAddID(myMACAddress, facebookID);
                        /*ParseQuery<ParseObject> query = ParseQuery.getQuery("ID_Map");
                        query.whereEqualTo("BT_ID", myMACAddress);
                        List<ParseObject> parseList = null;
                        try {
                            parseList = query.find();
                        }
                        catch(Exception ex){}
                        if(parseList == null || parseList.isEmpty()) {

                            Log.d("TAG", "entrou em updateID");
                            ParseObject parseObject = ParseObject.create("ID_Map");
                            parseObject.put("FB_ID", facebookID);
                            parseObject.put("BT_ID", myMACAddress);
                            parseObject.saveInBackground();
                        }
                        else {
                            for(ParseObject po : parseList) {
                                po.put("FB_ID", facebookID);
                                po.saveInBackground();
                            }
                        }*/
                        String img_url = "http://graph.facebook.com/" + facebookID + "/picture?type=small";
                        startActivity(nextpage);
                    }

                    @Override
                    public void onCancel() {
                        String s = "sdd";
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        String s = "sdd";
                    }
                });

        ParseAnalytics.trackAppOpenedInBackground(getIntent());


    }


    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fb_layout, container, false);

        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");

        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult){
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                Log.d(TAG, "11111");
                if (bluetoothAdapter == null) {
                    Log.d(TAG, "device does not support bluetooth");
                    return;
                }
                String myMACAddress = bluetoothAdapter.getAddress();
                //ParseObject parseObject = new ParseObject("ID_Map");
                //parseObject.put("BT_ID", myMACAddress);
                Log.d(TAG, myMACAddress);
                String facebookID = loginResult.getAccessToken().getUserId();
                //parseObject.put("FB_ID", facebookID);
                Log.d(TAG, facebookID);
                //parseObject.saveInBackground();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        return view;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
