package com.aroominn.aroom.entity;

import java.io.Serializable;

public class Friends implements Serializable {

    private Integer Id;
    private String nick;
    private String head;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
