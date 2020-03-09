package com.aroominn.aroom.controller;

import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.entity.RespEntity;
import com.aroominn.aroom.service.SettingService;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/setting")
public class FeedBackController {

    @Autowired
    SettingService service;

    @PostMapping(value = "/feedback")
    @ResponseBody
    public RespEntity feedback(@RequestBody JSONObject param) {

        if (TextUtils.isEmpty(param.getString("text"))) {
            return new RespEntity(RespEntity.RespCode.FAILED, "反馈信息不能为空");
        }

        service.feedBack(param);

        return new RespEntity(RespEntity.RespCode.SUCCESS, "ok");
    }

}
