package com.aroominn.aroom.service;


import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.entity.Follow;
import com.aroominn.aroom.entity.HomeInfo;
import com.aroominn.aroom.entity.Report;
import com.aroominn.aroom.entity.User;
import com.github.pagehelper.PageInfo;

/**
 * service接口层
 */

public interface UserService {

    /*添加用户  手机号必填*/
    int addUser(User user);

    PageInfo<User> findAllUser(int pageNum, int pageSize);

    User login(String userPhone);

    int followUser(Follow follow);

    User selectByPhone(User user);

    User validateToken(String token);

    /*id或者token不能为空*/
    int updateUser(User user);

    User getUserById(int id);

    int reportUser(Report report);

    HomeInfo findHomeInfo(JSONObject object);
}
