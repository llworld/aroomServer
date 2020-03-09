package com.aroominn.aroom.controller;

import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.entity.HomeInfo;
import com.aroominn.aroom.entity.RespEntity;
import com.aroominn.aroom.entity.User;
import com.aroominn.aroom.service.InnService;
import com.aroominn.aroom.service.UserService;
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

    @Autowired
    private UserService userService;


    @ResponseBody
    @PostMapping(value = "/userinfo")
    public RespEntity userInfo(@RequestBody JSONObject param) {
        if (param.getIntValue("toId")==0){
            //被查看的人的id不能为空
        }
        if (param.getIntValue("fromId")==0){
            //查看人的id不能为空
        }

        HomeInfo info=userService.findHomeInfo(param);

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
