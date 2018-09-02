package com.example.bamouhmohamed.androidproject;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by younes on 01/05/2018.
 */

public class permissionsClass {
    Boolean MacAddressOk;
    Context mContext;
    String MacAddress;
    permissionsClass (Context mContext){
        MacAddressOk = true;
        this.mContext = mContext;
        setMacAddress();
    }
    public void setMacAddress(){
        try {
            WifiManager manager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            String address = info.getMacAddress();
            MacAddress = address;
        }
        catch (Exception e){
            MacAddressOk = false;
        }
    }


    public String getMacAddress() {
        return MacAddress;
    }

    public Boolean getMacAddressOk() {
        return MacAddressOk;
    }
}
