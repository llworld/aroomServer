package com.aroominn.aroom.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.dao.SettingDao;
import com.aroominn.aroom.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    SettingDao settingDao;

    @Override
    public int feedBack(JSONObject param) {

        return settingDao.feedBack(param);
    }
}
