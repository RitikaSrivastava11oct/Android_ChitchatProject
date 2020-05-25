package com.example.android.chitchat.Model;

/**
 * Created by hp on 16-07-2018.
 */


/**
 * Created by prachijn on 16-07-2018.
 */

public class PostModel {

    String name;
    String lastMsg;
    public PostModel(String name,String lastMsg)
    {
        this.name=name;
        this.lastMsg=lastMsg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }



}
