package com.aroominn.aroom.controller;

import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.entity.RespEntity;
import com.aroominn.aroom.entity.Stories;
import com.aroominn.aroom.entity.User;
import com.aroominn.aroom.service.InnService;
import com.github.pagehelper.PageInfo;
import inter.UserAuthenticate;
import inter.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.aroominn.aroom.entity.RespEntity.RespCode.FAILED;
import static com.aroominn.aroom.entity.RespEntity.RespCode.SUCCESS;

@Controller
@RequestMapping(value = "/api/inn")
public class InnController {

    @Autowired
    private InnService innService;


    @ResponseBody
    @PostMapping(value = "/brewing")
    public RespEntity brewingStories(@RequestBody JSONObject param) {

        return new RespEntity(SUCCESS, "酝酿成功");
    }

    /**
     * 获取故事列表
     * 0：关注人的故事
     * 1：推荐的故事
     * 2：最新故事
     *
     * @param param
     * @return
     */
    @UserAuthenticate
    @ResponseBody
    @PostMapping(value = "/all")
    public RespEntity followStories(@RequestBody JSONObject param, @UserId Integer uId) {
        System.out.println(uId + "-----------------------------------");      //请求用户的ID
        int type = param.getIntValue("type");
        int pageNum = param.getIntValue("pageNum");
        int pageSize = param.getIntValue("pageSize");
        int userId = param.getIntValue("id");
        User user = new User();
        user.setId(uId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", uId);
        switch (type) {
            case 0:     //关注
                return new RespEntity(SUCCESS, innService.findFollowStories(pageNum, pageSize, uId));
            case 1:     //推荐
                return new RespEntity(SUCCESS, innService.findRecommendStories(pageNum, pageSize, uId));
            case 2:     //最新
                return new RespEntity(SUCCESS, innService.findLatesStories(pageNum, pageSize, jsonObject));
            case 3:     //指定人
                return new RespEntity(SUCCESS, innService.findUserStories(param));
            default:
                return new RespEntity(FAILED, "参数错误");
        }
    }


    /**
     * 用户所有故事
     *
     * @param param userId
     * @return
     */
    @ResponseBody
    @PostMapping("/personalStories")
    public RespEntity HiStoriesStories(@RequestBody JSONObject param) {
        PageInfo<Stories> personal = innService.findUserStories(param);
        return new RespEntity(SUCCESS, personal);
    }

    /**
     * 用户收藏的故事
     * @return
     */
    @ResponseBody
    @PostMapping("/personalCollect")
    public RespEntity collectedStories(@RequestBody JSONObject param) {
        PageInfo<Stories> personal = innService.findCollectStories(param);
        return new RespEntity(SUCCESS, personal);
    }


}
