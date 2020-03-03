package com.aroominn.aroom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.dao.InnDao;
import com.aroominn.aroom.entity.Stories;
import com.aroominn.aroom.service.InnService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "innService")
public class InnServiceImpl implements InnService {

    @Autowired
    private InnDao innDao;

    @Override
    public int insertStories(JSONObject param) {
        return 0;
    }

    @Override
    public PageInfo<Stories> findFollowStories(int pageNum, int pageSize,int id) {
        PageHelper.startPage(pageNum, pageSize);

        Map<String,String> map=new HashMap<>();
        map.put("id",id+"");
        List<Stories> users = innDao.followStories(map);
        PageInfo result = new PageInfo(users);
        return result;
    }

    @Override
    public PageInfo<Stories> findRecommendStories(int pageNum, int pageSize,int id) {
        PageHelper.startPage(pageNum, pageSize);
        List<Stories> users = innDao.recommendStories(id);
        PageInfo result = new PageInfo(users);
        return result;
    }

    @Override
    public PageInfo<Stories> findLatesStories(int pageNum, int pageSize, JSONObject user) {
        PageHelper.startPage(pageNum, pageSize);
        List<Stories> users = innDao.latesStories(user);
        PageInfo result = new PageInfo(users);
        return result;
    }

    @Override
    public PageInfo<Stories> findUserStories(JSONObject param) {
        int pageNum=param.getIntValue("pageNum");
        int pageSize=param.getIntValue("pageSize");
        PageHelper.startPage(pageNum, pageSize);
        List<Stories> users = innDao.getStoriesByUserId(param);
        PageInfo result = new PageInfo(users);
        return result;
    }

    @Override
    public PageInfo<Stories> findCollectStories(int pageNum, int pageSize, int id) {
        return null;
    }


}
