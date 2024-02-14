package com.android.kaviapp.Models;

/**
 * Created by dell on 01-03-2017.
 */

public class User {
    public String id;
    public String password;
    public String name;
    public Integer mobile;

    public User( String id, Integer mobile, String password, String name) {
        this.id = id;
        this.mobile = mobile;
        this.password = password;
        this.name = name;


    }
}
