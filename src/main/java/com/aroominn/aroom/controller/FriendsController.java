package com.aroominn.aroom.controller;

import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.entity.RespEntity;
import com.aroominn.aroom.entity.User;
import com.aroominn.aroom.service.FriendsService;
import com.aroominn.aroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.aroominn.aroom.entity.RespEntity.RespCode.SUCCESS;

@Controller
@RequestMapping(value = "/api/friends")
public class FriendsController {

    @Autowired
    FriendsService service;

    @ResponseBody
    @PostMapping(value = "/xiaoer")
    public RespEntity followMe(@RequestBody JSONObject param) {
        //聊天列表里显示 用户头像 昵称 使用时长（天数） 发布过的故事个数

        List<User> res = service.findXiaoer(param);


        return new RespEntity(SUCCESS, res);
    }

    @ResponseBody
    @PostMapping(value = "/publican")
    public RespEntity myFollow(@RequestBody JSONObject param) {
        //聊天列表里显示 用户头像 昵称 使用时长（天数） 发布过的故事个数

        List<User> res = service.findPublican(param);


        return new RespEntity(SUCCESS, res);
    }

    @ResponseBody
    @PostMapping(value = "/partner")
    public RespEntity followFriends(@RequestBody JSONObject param) {
        //聊天列表里显示 用户头像 昵称 使用时长（天数） 发布过的故事个数

        List<User> res = service.findPartner(param);


        return new RespEntity(SUCCESS, res);
    }
    @ResponseBody
    @PostMapping(value = "/userinfo")
    public RespEntity friendsInfo(@RequestBody JSONObject param) {
        //聊天列表里显示 用户头像 昵称 使用时长（天数） 发布过的故事个数

        User res = service.findFriendUserInfo(param);


        return new RespEntity(SUCCESS, res);
    }
}
