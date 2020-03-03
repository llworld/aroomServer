package com.aroominn.aroom.entity;

import org.springframework.context.annotation.Bean;

import java.io.Serializable;

/**
 * 举报
 */
public class Report implements Serializable {
    private int id;
    private int StoriesId;            //故事id
    private int userId;             //用户id
    private String Reason;          //举报原因
    private int type;               //举报类型
    private int fid;                //举报人id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoriesId() {
        return StoriesId;
    }

    public void setStoriesId(int storiesId) {
        StoriesId = storiesId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }
}
