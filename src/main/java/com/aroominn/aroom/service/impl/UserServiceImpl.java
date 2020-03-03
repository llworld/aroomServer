package com.aroominn.aroom.service.impl;

import com.aroominn.aroom.dao.UserDao;
import com.aroominn.aroom.entity.Follow;
import com.aroominn.aroom.entity.Report;
import com.aroominn.aroom.entity.User;
import com.aroominn.aroom.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "userService")
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;


    @Override
    public int addUser(User user) {
        userDao.insert(user);
        int id = user.getId();
        System.out.println(id);
        return id;
    }

    @Override
    public PageInfo<User> findAllUser(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userDao.selectUsers();
        PageInfo result = new PageInfo(users);
        return result;
    }

    @Override
    public User login(String userPhone) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", userPhone);
        return userDao.selectLogin(map);
    }

    @Override
    public int followUser(Follow follow) {

        return userDao.followUser(follow);
    }

    @Override
    public User selectByPhone(User user) {

        return userDao.selectByPhone(user);
    }

    @Override
    public User validateToken(String token) {
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        User user = new User();
        user.setToken(token);
        return userDao.selectByToken(user);
    }

    @Override
    public int updateUser(User user) {

        return userDao.updateUser(user);
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public int reportUser(Report report) {
        return userDao.report(report);
    }


}
