package com.aroominn.aroom.entity;

import org.springframework.context.annotation.Bean;

import java.io.Serializable;

/**
 * 举报
 */
public class Report implements Serializable {
    private int id;
    private int storyId;            //故事id
    private int userId;             //用户id
    private String reason;          //举报原因
    private String type;               //举报类型
    private int fid;                //举报人id


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

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


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }
}
