package com.aroominn.aroom.controller;

import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.entity.HomeInfo;
import com.aroominn.aroom.entity.RespEntity;
import com.aroominn.aroom.entity.User;
import com.aroominn.aroom.service.InnService;
import inter.UserAuthenticate;
import inter.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.aroominn.aroom.entity.RespEntity.RespCode.FAILED;
import static com.aroominn.aroom.entity.RespEntity.RespCode.SUCCESS;

@Slf4j
@Controller
@RequestMapping(value = "/api/homepage")
public class HomePageController {


    @Autowired
    private InnService innService;


    @ResponseBody
    @PostMapping(value = "/userinfo")
    public RespEntity userInfo(@RequestBody JSONObject param) {

        HomeInfo info = new HomeInfo();
        info.setFollow("33");
        info.setHeadUrl("http://192.168.1.6/resource/images/DefaultHead/xiaoliu.jpg");
        info.setName("这是我的名字");
        return new RespEntity(RespEntity.RespCode.SUCCESS, info);
    }


    /**
     * @param param id pageNum  pageSize
     *              查询 id 的 第pageNum页，每页pageSize个
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/stories")
    public RespEntity followStories(@RequestBody JSONObject param) {

        String userId = param.getString("userId");
        int pageNum = param.getIntValue("pageNum");
        int pageSize = param.getIntValue("pageSize");

        return new RespEntity(SUCCESS, innService.findUserStories(param));

    }


}
