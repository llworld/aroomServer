package com.aroominn.aroom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.dao.FriendsDao;
import com.aroominn.aroom.entity.User;
import com.aroominn.aroom.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "friendService")
public class FriendsServiceImpl implements FriendsService {

    @Autowired
    FriendsDao friendsDao;

    @Override
    public List<User> findXiaoer(JSONObject param) {
        return friendsDao.findXiaoer(param);
    }

    @Override
    public List<User> findPublican(JSONObject param) {
        return friendsDao.findPublican(param);
    }

    @Override
    public List<User> findPartner(JSONObject param) {
        return friendsDao.findPartner(param);
    }

    @Override
    public User findFriendUserInfo(JSONObject param) {
        return friendsDao.findFriendInfo(param);
    }
}
