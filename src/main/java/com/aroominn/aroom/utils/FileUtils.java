package com.aroominn.aroom.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aroominn.aroom.entity.RespEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.aroominn.aroom.consist.Constant.IMAGES_URL;
import static com.aroominn.aroom.consist.Constant.SERVER_URL;

public class FileUtils {

    public static FileUtils getInstance() {
        return new FileUtils();
    }

    public JSONArray saveFiles(List<MultipartFile> files, String id) {
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        JSONArray jsonArray = new JSONArray();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            /*阿里环境为当前目录*/
                            new File("./images/" + id + file.getOriginalFilename())));//存在项目根目录下
//                            new File("./../images/" + id + file.getOriginalFilename())));//存在项目根目录下
                    stream.write(bytes);
                    stream.close();
                    JSONObject jsonObject = new JSONObject();
                    jsonArray.add(SERVER_URL + "resource/images/" + id + file.getOriginalFilename());
                    strings.add(SERVER_URL + "resource/images/" + id + file.getOriginalFilename());
//                    jsonArray.add(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                    stream = null;
                    return null;
                }
            } else {
                return null;
            }
        }

        System.out.println(strings);
        return jsonArray;
    }
}