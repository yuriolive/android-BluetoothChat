package com.example.android.bluetoothchat;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import com.facebook.GraphRequest;

/**
 * Created by k4ne on 17/05/15.
 */
public class FBrequests {
    public ArrayList<String> getUsersInfo(Iterable<String> list){
        ArrayList<String> users_name = new ArrayList<String>();
        for(String FB_ID : list){
            try{
                URL url = new URL("https://graph.facebook.com/"+ FB_ID + "?fields=name");
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                String aux = "";
                StringBuilder buffer = new StringBuilder();
                while((aux = br.readLine()) != null){
                    buffer.append(aux);
                }
                int pos = buffer.lastIndexOf("\"name\": \"");
                String name = buffer.substring(pos+1, buffer.indexOf("\"", pos+1));
                users_name.add(name);
            }
            catch(Exception ex){}
        }
        return users_name;
    }
}
