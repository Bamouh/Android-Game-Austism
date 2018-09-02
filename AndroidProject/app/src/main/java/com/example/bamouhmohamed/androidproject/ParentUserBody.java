package com.example.bamouhmohamed.androidproject;

import java.util.List;

/**
 * Created by younes on 10/06/2018.
 */

class ParentUserBody {
    private String token_type;
    private String access_token;
    private List<Enrollments> enrollments;

    public String getToken_type() {
        return token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public List<Enrollments> getEnrollments() {
        return enrollments;
    }


}
