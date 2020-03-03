package com.aroominn.aroom.dao;


import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.entity.Stories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface InnDao {

    /*关注的故事*/
    List<Stories> followStories(Map<String,String> map);

    /*推荐故事*/
    List<Stories> recommendStories(int id);

    /*最新故事*/
    List<Stories> latesStories(JSONObject user);

    /**某个用户的故事
     * 有问题 查出来的东西乱七八糟的*/
    List<Stories> userStories(JSONObject param);

    /*某个用户的故事*/
    List<Stories> getStoriesByUserId(JSONObject param);

    /*收藏的故事*/
    List<Stories> findCollectStories(JSONObject param);
}
