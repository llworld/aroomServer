package com.aroominn.aroom.entity;

import java.io.Serializable;

public class Collections implements Serializable {

    /**主键ID*/
    int id;
    /**故事ID*/
    int storyId;
    /**故事拥有人ID*/
    int ownerId;
    /**收藏人ID*/
    int userId;
    /**
     * 收藏状态
     * 0:取消收藏/未收藏
     * 1：收藏
     * */
    int enable;

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }
}
