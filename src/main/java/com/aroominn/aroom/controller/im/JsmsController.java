package com.aroominn.aroom.controller.im;

import cn.jsms.api.common.SMSClient;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aroominn.aroom.entity.RespEntity;
import com.aroominn.aroom.service.UserService;
import com.aroominn.aroom.utils.RedisUtil;
import com.aroominn.aroom.utils.ali.SmsUtil;
import io.rong.RongCloud;
import io.rong.methods.user.User;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.aroominn.aroom.consist.Constant.*;
import static com.aroominn.aroom.entity.RespEntity.RespCode.*;

//import static org.junit.Assert.assertTrue;
@Slf4j
@Controller
@RequestMapping(value = "/api/jiguang")
public class JsmsController {


    SMSClient client = new SMSClient(MASTER_SECRET, APP_KEY);
//    private CommonResponse response;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserService userService;

    @ResponseBody
    @PostMapping(value = "/loginSmsCode")
    public RespEntity loginSmsCode(@RequestBody JSONObject param) {

        String phone = param.getString("phone");
        String code = SmsUtil.getInstance().createRandomVcode();
        SendSmsResponse smsResponse = SmsUtil.getInstance().sendSmsValidCode(phone, code);

        if (smsResponse.getCode().equals("OK")) {
            //手机号和验证码存入缓存
            if (!redisUtil.hasKey(phone)) {
                redisUtil.set(phone, code, 60);
                return new RespEntity(SUCCESS, smsResponse.getMessage());
            } else {
                //验证码已发送，请稍后再试
                return new RespEntity(SUCCESS, smsResponse.getMessage());
            }

        }
        return new RespEntity(FAILED, smsResponse.getMessage());

    }

    @ResponseBody
    @PostMapping(value = "/loginValidSmsCode")
    public RespEntity validSmsCode(@RequestBody JSONObject param) throws Exception {

        /**
         * 插入信息获取id
         * 融云注册获取token
         * 更新token
         * 获取用户信息
         */

        String phone = param.getString("phone");
        String code = param.getString("code");
        if (!redisUtil.hasKey(phone)) {
            return new RespEntity(FAILED, "该手机号未发送验证码");
        }
        String tcode = redisUtil.get(phone).toString();
        if (code.equals(redisUtil.get(phone).toString())) {

            //查询数据 用户是否存在
            //存在则直接登录
            com.aroominn.aroom.entity.User u = new com.aroominn.aroom.entity.User();
            u.setPhone(phone);
            u = userService.selectByPhone(u);
            if (u != null) {

                //存在直接返回用户数据 登录
                return new RespEntity(SUCCESS, u);
            }

            //不存在  添加新用户
            com.aroominn.aroom.entity.User newUser = new com.aroominn.aroom.entity.User();
            newUser.setPhone(phone);
            newUser.setHead("http://127.0.0.1:8080/resource/images/DefaultHead/dazui.jpg");
//            newUser.setToken(result.getToken());
            int id = userService.addUser(newUser);

            //获取到id去IM注册
            RongCloud rongCloud = RongCloud.getInstance(RC_KEY, RC_SECRET);
            User user = rongCloud.user;
            UserModel userModel = new UserModel()
                    .setId(id + "")
                    .setName("aroom")
                    .setPortrait("http://127.0.0.1:8080/resource/images/DefaultHead/dazui.jpg");
            TokenResult result = user.register(userModel);
            if (result.getCode() != 200) {
                log.error(result.getErrorMessage());
                return new RespEntity(IMERROR, new com.aroominn.aroom.entity.User());
            }
            String token = result.getToken();

            newUser.setId(id);
            newUser.setToken(token);
            newUser.setNick("aroom");

            int addRes = userService.updateUser(newUser);
            log.error(addRes + "更新结果");
            System.out.println("getToken:  " + result.toString());


            /**
             * {"token":"PCuK/O5bCYieTpMIE8cka+3e4grJCAaj+0s/oL5aFiTsa42jmwdN2YwNzcTgf9x4xeUZuK6hojTMuPjleKqP8WwiZbHFIyu7",
             * "userId":"17861983600",
             * "code":200}
             */
            //返回新用户数据  登录
            return new RespEntity(SUCCESS, newUser);
        }
        //验证码错误
        return new RespEntity(FAILVALID, new com.aroominn.aroom.entity.User());

    }


}
