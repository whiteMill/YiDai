package com.sdot.yidai.model;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/15.
 */

public class Employee implements Serializable{

    /**
     * id : null
     * nickname : null
     * headimgurl : null
     * username : 13800008888
     * realname : 13800008888
     * createdAt : null
     */

    private String id;
    private String nickname;
    private String headimgurl;
    private String username;
    private String realname;
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
