package com.aroominn.aroom.dao;

import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.entity.*;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StoriesDao {

    /*点赞*/
    int likeStories(Like like);

    /*举报*/
    int reportStories(Report report);

    /*评论*/
    int commentStories(Comment comment);

    List<Comment> getComment(JSONObject param);

    /*发布故事*/
    int brewingStory(JSONObject param);

    /*收藏故事*/
    int collect(Collections param);

    int deleteTale(JSONObject param);
}
