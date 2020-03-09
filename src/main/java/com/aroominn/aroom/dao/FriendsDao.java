package com.aroominn.aroom.dao;

import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsDao {

    /*查询关注我的人 （小二儿）*/
    List<User> findXiaoer(JSONObject param);

    /*查询互相关注的人（伙伴）*/
    List<User> findPartner(JSONObject param);

    /*查询我关注的人（掌柜的）*/
    List<User> findPublican(JSONObject param);


    User findFriendInfo(JSONObject param);
}
