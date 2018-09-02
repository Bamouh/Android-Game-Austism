package com.example.bamouhmohamed.androidproject;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by younes on 01/05/2018.
 */

public class PermissionsManagement {
    Boolean permissionsOk;
    Context mContext;
    String MacAddress;
    PermissionsManagement(Context mContext){
        permissionsOk = true;
        this.mContext = mContext;
        setMacAddress();
    }

    public void setMacAddress() {
        try {
            WifiManager manager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            MacAddress = info.getMacAddress();
        }
        catch (Exception e){
            permissionsOk = false;
        }
    }
    public String getMacAddress(){
        return MacAddress;
    }

}
