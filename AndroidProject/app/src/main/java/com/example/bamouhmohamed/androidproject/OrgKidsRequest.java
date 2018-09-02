package com.example.bamouhmohamed.androidproject;

/**
 * Created by younes on 10/06/2018.
 */

class OrgKidsRequest {
    int user_id;
    String app_id;
    int organization_id;

    public OrgKidsRequest(int user_id, String app_id, int organization_id) {
        this.user_id = user_id;
        this.app_id = app_id;
        this.organization_id = organization_id;
    }
}
