package com.example.bjaso.cs3714finalproj.data;

/**
 * Created by pejman on 5/2/2017.
 */

public class Users {

    private String username;
    //private String email;
    private String url;

    public Users(String username, String url) {
        this.username = username;
        //this.email = email;
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    //public String getEmail() {
//        return email;
//    }

    public String getUrl() {
        return url;
    }
}
