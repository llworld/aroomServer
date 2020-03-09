package com.aroominn.aroom.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.entity.*;
import com.aroominn.aroom.entity.Collections;
import com.aroominn.aroom.service.StoriesService;
import com.aroominn.aroom.utils.FileUtils;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.aroominn.aroom.entity.RespEntity.RespCode.*;

@Slf4j
@Controller
@RequestMapping(value = "/api/stories")
public class StoriesController {

    /**
     * GET      用来获取资源，
     * POST     用来新建资源（也可以用于更新资源），
     * PUT      用来更新资源，
     * DELETE   用来删除资源
     */

    @Autowired
    private StoriesService service;

    /**
     * 点赞 取消点赞
     * (#{storyId},#{ownerId},#{userId},#{status})
     *
     * @param like
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/like")
    public RespEntity operationLike(@RequestBody Like like) {
        /**
         * 通过status判断赞的类型
         * 0:点赞
         * 1:取消赞
         */

        int type = like.getStatus();

        service.likeTale(like);


        return type == 1 ? new RespEntity(LIKE, "like") : new RespEntity(UNLIKE, "unlike");

    }

    /**
     * 举报tale
     *
     * @param report
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/report")
    public RespEntity operationReport(@RequestBody Report report) {
        service.operaReport(report);
        return new RespEntity(SUCCESS, "举报成功");
    }

    /**
     * 转发tale
     * 1、查出来再写入
     * 2、前端全部传过来写入
     * 3 insert () value (select () from stories where id={1}) into stories
     *
     * @param repost
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/repost")
    public RespEntity repostTale(@RequestBody Stories repost) {
        return null;
    }

    /**
     * 收藏tale
     *
     * @param collections
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/collect")
    public RespEntity collectTale(@RequestBody Collections collections) {
        //把收藏的故事ID添加到收藏列表
        try {
            service.operaCollect(collections);
        } catch (Exception e) {
            log.error("会有异常吗");
        }
        return new RespEntity(SUCCESS, "操作成功");
    }


    /**
     * 请求评论列表
     *
     * @param param
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/details")
    public RespEntity detailStories(@RequestBody JSONObject param) {
        PageInfo<Comment> commentInfo = service.commentList(param);
        return new RespEntity(SUCCESS, commentInfo);
    }

    /**
     * 评论功能
     *
     * @param comment
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/comment")
    public RespEntity commentStories(@RequestBody Comment comment) {
        if (comment != null) {
            comment.setTimes(new Date().toLocaleString());
            service.commentTale(comment);
        }
        return new RespEntity(SUCCESS, "评论成功");
    }

    @PostMapping(value = "/destroy")
    @ResponseBody
    public RespEntity destroyTale(@RequestBody JSONObject param){
        service.destroyTale(param);
        return new RespEntity(SUCCESS,"删除成功");
    }

    @PostMapping(value = "/brewing")
    @ResponseBody
    public RespEntity multiImport(@RequestParam("info") String info, @RequestParam(value = "pictures", required = false) CommonsMultipartFile[] pictures) {
        String reportBean = info;
        //文本内容
        for (CommonsMultipartFile file : pictures) {
//            FileItem imgName = file.getFileItem();
            File localFile = new File("G:/" + file.getOriginalFilename());
            try {
                file.transferTo(localFile);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new RespEntity(SUCCESS, "完工");
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public RespEntity handleFileUpload(HttpServletRequest request) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        String content = params.getParameter("content");
        String id = params.getParameter("id");
        JSONArray imgList = FileUtils.getInstance().saveFiles(files, id);
        JSONObject param = new JSONObject();
        param.put("userId", id);
        param.put("times", new Date());
        param.put("content", content);
        param.put("images", imgList.toString());
        service.brewingStory(param);
        return new RespEntity(SUCCESS, "完工");
    }
}