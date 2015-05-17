package com.example.android.bluetoothchat.entities;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by k4ne on 17/05/15.
 */
@ParseClassName("ID_Map")
public class MapID extends ParseObject {
    public MapID(){

    }
    public void setBTID(String myMACAddress){
        put("BT_ID", myMACAddress);
    }
}
