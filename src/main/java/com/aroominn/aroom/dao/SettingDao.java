package com.aroominn.aroom.dao;


import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingDao {


    int feedBack(JSONObject param);
}
