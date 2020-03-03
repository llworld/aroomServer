package com.aroominn.aroom.vo;

import java.io.Serializable;

public class Story implements Serializable {

    private Integer id;             //故事id
    private Integer userId;         //发布人id
    private String times;           //发布时间
    private String content;         //发布内容
    private int likes;              //点赞数
    private int comment;            //评论数
    private int repost;             //转发数

    private String nick;        //昵称
    private String head;        //头像连接
    private String phone;       //用户手机号
    private int category;       //用户类别(根据类别添加标签)

    private int isLike;         //是否点赞
    private int type;           //0:原创 1:转发  2:收藏  3:举报
    private int ownerId;        //故事拥有人，原创为空

    private int like;           //点赞
    private int collection;     //收藏

}
