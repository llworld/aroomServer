package com.aroominn.aroom.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 关注
 */
public class Follow implements Serializable {
    private int id;
    private int userId;             //关注人
    private int followId;           //被关注人
    private Date time;            //关注时间
    private int status;             //关注状态  0:关注(userId关注followId)   1:互为关注

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
