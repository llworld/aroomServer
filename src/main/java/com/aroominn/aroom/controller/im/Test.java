package com.aroominn.aroom.controller.im;

import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.entity.RespEntity;
import com.aroominn.aroom.entity.User;
import com.aroominn.aroom.service.UserService;
import com.sun.jndi.toolkit.url.Uri;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Controller
public class Test {

    @Autowired
    UserService service;

    @ResponseBody
    @PostMapping(value = "/test")
    public RespEntity loginSmsCode(@RequestBody JSONObject param) {
        User user=new User();
user.setPhone("15566778899");
      int d=  service.addUser(user);
      log.error(d+"--->这是获取到的id");

    /*    Map<String,String> map=new HashMap<>();
        map.put("id","521");
        map.put("name","test");
        map.put("portraitUri","http://img5.imgtn.bdimg.com/it/u=3760264768,2474732525&fm=11&gp=0.jpg");
        User user=new User();
        user.setNick("test");
        user.setId(Integer.valueOf(param.getString("id")));
        user.setHead("http://img5.imgtn.bdimg.com/it/u=3760264768,2474732525&fm=11&gp=0.jpg");
        System.out.println("返回结果");*/

        return new RespEntity(RespEntity.RespCode.SUCCESS);
    }

}
