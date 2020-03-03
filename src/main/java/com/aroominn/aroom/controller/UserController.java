package com.aroominn.aroom.controller;


import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.entity.Follow;
import com.aroominn.aroom.entity.Report;
import com.aroominn.aroom.entity.RespEntity;
import com.aroominn.aroom.entity.User;
import com.aroominn.aroom.service.UserService;

import com.aroominn.aroom.utils.rongcloud.UserApi;
import inter.UserAuthenticate;
import inter.UserId;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;

import static com.aroominn.aroom.consist.Constant.RC_KEY;
import static com.aroominn.aroom.consist.Constant.RC_SECRET;
import static com.aroominn.aroom.consist.Constant.SERVER_URL;
import static com.aroominn.aroom.entity.RespEntity.RespCode.FAILED;
import static com.aroominn.aroom.entity.RespEntity.RespCode.REGISTERED;
import static com.aroominn.aroom.entity.RespEntity.RespCode.SUCCESS;

@Controller
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /*@ResponseBody 注解，它表示此接口响应为纯数据，不带任何界面展示，可以获得标准Json。*/
    @ResponseBody
    @PostMapping("/all")
    public RespEntity findAllUser(@RequestBody JSONObject jsonParam) {
        return new RespEntity(SUCCESS, userService.findAllUser(jsonParam.getIntValue("pageNum"), jsonParam.getIntValue("pageSize")));

    }

    /**
     * 注册时判断是否注册
     * 是：返回已注册
     * 否：添加，获取token
     *
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public RespEntity registerUser(@RequestBody User user) {

        /**
         * 检查请求信息
         */
        if (user == null) {
            return new RespEntity(FAILED);
        }
        if (user.getPhone() == null || user.getPhone().trim().equals("")) {
            return new RespEntity(FAILED);
        }

        //1、检验用户是否注册过
        User u = userService.selectByPhone(user);
        if (u != null) {
            return new RespEntity(REGISTERED);
        }

        int res = userService.addUser(user);
        return res == 1 ? new RespEntity(SUCCESS, u) : new RespEntity(FAILED, "");

    }

    @ResponseBody
    @PostMapping("/login")
    public Object loginUser(@RequestBody JSONObject jsonParam) {

        /**
         * 登陆时通过账号查询密码与传过来的密码对比
         * 通过账号查找不到密码返回账号错误
         * 密码对比错误返回密码错误
         * （验证码登陆 未设置过密码时如何处理 前端提示设置密码）
         */
        User user = userService.login(jsonParam.getString("userPhone"));
        if (user == null) {
            return new RespEntity(FAILED, "账号不存在");
        }
        if (!jsonParam.getString("userPassword").equals(user.getPassword())) {
            return new RespEntity(FAILED, "密码错误");
        }
        /*用户登录成功后获取token*/

        try {
//            String token = UserApi.getInstance().getToken(user.getPhone() + "", user.getNick(), user.getHead());
//            user.setToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new RespEntity(SUCCESS, user);
    }

    /**
     * 更新用户信息
     */
    @UserAuthenticate
    @ResponseBody
    @PostMapping("/update")
    public RespEntity updateUser(@RequestBody User user, @UserId Integer uId) {
        if (uId != null) {
            user.setId(uId);
        }

       int id= userService.updateUser(user);

        User u=userService.getUserById(uId);


        return new RespEntity(SUCCESS,u);
    }

    /**
     * 关注用户
     *
     * @param follow
     * @return
     */
    @ResponseBody
    @PostMapping("/follow")

    public RespEntity followUser(@RequestBody Follow follow) {

        follow.setTime(new Date());
        int res = userService.followUser(follow);
        return res == 1 ? new RespEntity(SUCCESS, "关注成功") : new RespEntity(FAILED, "未知错误");
    }

    /**
     * 举报用户
     */

    @ResponseBody
    @PostMapping("/report")
    public RespEntity reportUser(@RequestBody Report report) {

        if (report == null) {
            return new RespEntity(FAILED, "");
        }
        if (report.getReason().equals("")) {
            return new RespEntity(FAILED, "举报原因为空");
        }
        int res = userService.reportUser(report);

        return res == 1 ? new RespEntity(SUCCESS, "举报成功") : new RespEntity(FAILED, "");
    }

    /**
     * 拉黑用户
     */


 /*
  @ResponseBody
    @PostMapping("/all")
    public Object findAllUser(
            @RequestParam(name = "pageNum",required = false,defaultValue = "1")
            int pageNum,
            @RequestParam(name = "pageSize",required = false,defaultValue = "10")
            int pageSize
    ){
        return userService.findAllUser(pageNum, pageSize);
    }
    */
}
