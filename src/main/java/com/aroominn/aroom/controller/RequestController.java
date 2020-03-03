package com.aroominn.aroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;


@Controller
public class RequestController {

    @RequestMapping(value = "/api/**", method = RequestMethod.GET)
    @ResponseBody
    public String getProcess(HttpServletRequest request) {
        String retrunValue = "Hello, Angus! This is GET request!";
        System.out.println("=======GET Process=======");

        Map<String, String[]> requestMsg = request.getParameterMap();
        Enumeration<String> requestHeader = request.getHeaderNames();

        System.out.println("------- header -------");
        while (requestHeader.hasMoreElements()) {
            String headerKey = requestHeader.nextElement().toString();
            //打印所有Header值

            System.out.println("headerKey=" + headerKey + ";value=" + request.getHeader(headerKey));
        }
        System.out.println("------- parameter -------");
        for (String key : requestMsg.keySet()) {
            for (int i = 0; i < requestMsg.get(key).length; i++) { //打印所有请求参数值

                System.out.println("key=" + key + ";value=" + requestMsg.get(key)[i].toString());
            }
        }
        return retrunValue;
    }

    @RequestMapping(value = "/api/**", method = RequestMethod.POST)
    @ResponseBody
    public String postProcess(HttpServletRequest request) {
        String retrunValue = "Hello, api! This is POST Request!";
        System.out.println("=======POST Process=======");


        Map<String, String[]> requestMsg = request.getParameterMap();
        Enumeration<String> requestHeader = request.getHeaderNames();
        InputStream io = null;
        String body;
        System.out.println("------- body -------");
        try {
            io = request.getInputStream();
            body = IOUtils.toString(io);
            //打印BODY内容
            System.out.println("Request Body=" + body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("------- header -------");
        while (requestHeader.hasMoreElements()) {
            String headerKey = requestHeader.nextElement().toString();
            //打印所有Header值

            System.out.println("headerKey=" + headerKey + ";value=" + request.getHeader(headerKey));
        }
        System.out.println("------- parameters -------");
        for (String key : requestMsg.keySet()) {
            for (int i = 0; i < requestMsg.get(key).length; i++) { //打印所有请求参数值
                System.out.println("key=" + key + ";value=" + requestMsg.get(key)[i].toString());
            }
        }
        return retrunValue;
    }
}
