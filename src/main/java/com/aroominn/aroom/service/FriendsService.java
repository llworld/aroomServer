package com.aroominn.aroom.service;

import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FriendsService {


    /*查询关注我的人 （小二儿）*/
    List<User> findXiaoer(JSONObject param);

    /*查询我关注的人（掌柜的）*/
    List<User> findPublican(JSONObject param);

    /*查询互相关注的人（伙伴）*/
    List<User> findPartner(JSONObject param);

    User findFriendUserInfo(JSONObject param);
}
