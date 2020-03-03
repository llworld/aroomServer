package com.aroominn.aroom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.dao.StoriesDao;
import com.aroominn.aroom.entity.*;
import com.aroominn.aroom.service.StoriesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "storiesService")
public class StoriesServiceImpl implements StoriesService {

    @Autowired
    private StoriesDao StoriesDao;

    //发布新故事
    @Override
    public int insertStories(JSONObject param) {
        return 0;
    }

    //收藏故事
    @Override
    public int operaCollect(Collections param) {
        return 0;
    }

    //点赞
    @Override
    public int likeTale(Like like) {
        StoriesDao.likeStories(like);
        return 0;
    }

    @Override
    public int unLikeTale(Like like) {
        return 0;
    }

    //转发
    @Override
    public int operaRepost(JSONObject param) {
        return 0;
    }

    //举报
    @Override
    public int operaReport(Report param) {
        return 0;
    }

    //评论
    @Override
    public int commentTale(Comment comment) {
        return StoriesDao.commentStories(comment);
    }

    @Override
    public PageInfo<Comment> commentList(JSONObject param) {

        PageHelper.startPage(param.getIntValue("pageNum"), param.getIntValue("pageSize"));
        List<Comment> comments = StoriesDao.getComment(param);
        PageInfo result = new PageInfo(comments);

        return result;
    }

    @Override
    public int brewingStory(JSONObject param) {

        return StoriesDao.brewingStory(param);
    }
}
