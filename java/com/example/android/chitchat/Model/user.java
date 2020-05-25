package com.example.android.chitchat.Model;

import java.io.Serializable;

/**
 * Created by hp on 14-07-2018.
 */

public class user implements Serializable{
    public String fname,lname,status,uname,key;

    public user() {
    }

    public user(String fname, String lname, String status, String uname, String key) {
        this.fname = fname;
        this.lname = lname;
        this.status = status;

        this.uname = uname;
        this.key = key;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
