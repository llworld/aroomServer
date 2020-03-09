package com.aroominn.aroom.dao;

import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.entity.Follow;
import com.aroominn.aroom.entity.HomeInfo;
import com.aroominn.aroom.entity.Report;
import com.aroominn.aroom.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * dao接口层
 */
@Repository
public interface UserDao {

    int insert(User domain);

    List<User> selectUsers();

    User selectLogin(Map<String,String> map);

    int followUser(Follow follow);

    User selectByPhone(User user);

    User selectByToken(User user);

    int updateUser(User user);

    int report(Report report);

    User getUserById(int id);

    HomeInfo findHomeInfo(JSONObject param);
}
