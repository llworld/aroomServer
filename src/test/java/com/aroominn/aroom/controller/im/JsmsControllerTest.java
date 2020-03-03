package com.aroominn.aroom.controller.im;

import io.rong.RongCloud;
import io.rong.methods.user.User;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static com.aroominn.aroom.consist.Constant.*;

@Slf4j
public class JsmsControllerTest {

    @Test
    public void validSmsCode() throws Exception {

        String phone="17861983600";
        RongCloud rongCloud = RongCloud.getInstance(RC_KEY, RC_SECRET);
        User user = rongCloud.user;

        /**
         * API 文档: http://www.rongcloud.cn/docs/server_sdk_api/user/user.html#register
         *
         * 注册用户，生成用户在融云的唯一身份标识 Token
         */
        UserModel userModel = new UserModel()
                .setId(phone)
                .setName("aroom")
                    .setPortrait("http://img5.imgtn.bdimg.com/it/u=1599160821,2917806006&fm=11&gp=0.jpg");
        TokenResult result = user.register(userModel);
        System.out.println("getToken:  " + result.toString());

    }
}