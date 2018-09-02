package com.example.bamouhmohamed.androidproject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by younes on 07/05/2018.
 */

class RecievedData implements Serializable {
    private String token_type;
    private String access_token;
    private List<Organisation> organizations;
    private List<Enrollments> enrollments;

    public String getToken_type() {
        return token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public List<Organisation> getOrganizations() {
        return organizations;
    }
    public List<Enrollments> getEnrollments() {
        return enrollments;
    }


}
