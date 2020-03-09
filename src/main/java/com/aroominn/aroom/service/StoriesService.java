package com.aroominn.aroom.service;

import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.entity.*;
import com.github.pagehelper.PageInfo;

public interface StoriesService {


    /**
     *
     */
    int insertStories(JSONObject param);

    /**
     * 故事列表
     * @param pageNum
     * @param pageSize
     * @return
     */


    /**
     * 故事的操作功能
     *
     * @param param
     * @return
     */
    /*收藏*/
    int operaCollect(Collections param);

    /*点赞*/
    /*取消赞*/
    int likeTale(Like like);



    /*转发*/
    int operaRepost(JSONObject param);

    /*举报*/
    int operaReport(Report param);

    /*分享*/

    /**
     * 故事评论
     */
    int commentTale(Comment comment);

    /**
     * 故事评论列表
     * @param param
     * @return
     */
    PageInfo<Comment> commentList(JSONObject param);


    /**
     * 发布故事
     */
    int brewingStory(JSONObject param);

    int destroyTale(JSONObject param);
}
