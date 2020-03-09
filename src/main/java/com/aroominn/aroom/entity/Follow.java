package com.aroominn.aroom.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 关注
 */
public class Follow implements Serializable {
    private int id;
    private int userId;             //关注人 必填
    private int followId;           //被关注人 必填
    private String times;            //关注时间 非填
    private int status;             //0为取关或为关注，1为关注  必填

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFollowId() {
        return followId;
    }

    public void setFollowId(int followId) {
        this.followId = followId;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
