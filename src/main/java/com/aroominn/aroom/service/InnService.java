package com.aroominn.aroom.service;

import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.entity.Stories;
import com.aroominn.aroom.entity.User;
import com.github.pagehelper.PageInfo;

public interface InnService {

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
        /*关注*/
        PageInfo<Stories> findFollowStories(int pageNum, int pageSize,int id);
        /*推荐*/
        PageInfo<Stories> findRecommendStories(int pageNum, int pageSize,int id);
        /*最新*/
        PageInfo<Stories> findLatesStories(int pageNum, int pageSize, JSONObject user);
        /*查询用户的故事*/
        PageInfo<Stories> findUserStories(JSONObject param);

        /*收藏的故事*/
        PageInfo<Stories>findCollectStories(JSONObject param);





        /**
         * 故事评论
         */


    }

