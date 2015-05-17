package com.example.android.bluetoothchat;

import com.example.android.bluetoothchat.entities.MapID;
import com.example.android.common.logger.Log;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by k4ne on 17/05/15.
 */
@ParseClassName("ID_Map")
public class ParseRequest {
    public static final String APPLICATION_ID = "zlAyTHU9HtVINDpz6U98s0s62eIHgM4q26ZwYvZj";
    public static final String CLIENT_KEY = "nrqdMzZ4QbkkeCbARW64KH4eL9r30hQ1WGc7TMsQ";

    Iterable<String> SearchFbIDByMac(Iterable<String> MacID) {
        return null;
    }

    public static void UpdateorAddID(final String myMACAddress, final String facebookID) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ID_Map");
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
        }
        return;
    }

    public Iterable<String> SearchFBID(ArrayList<String> MAC_IDs) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ID_Map");
        query.whereContainsAll("BT_ID", MAC_IDs);
        List<ParseObject> parseList = null;
        List<String> fb_ids = null;

        try {
            parseList = query.find();
        }
        catch(Exception ex){}

        for(ParseObject po : parseList) {
            fb_ids.add(po.get("FB_ID").toString());
        }
        return fb_ids;
    }


}
