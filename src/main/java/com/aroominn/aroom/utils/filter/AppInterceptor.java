package com.aroominn.aroom.utils.filter;

import com.aroominn.aroom.entity.RespEntity;
import com.aroominn.aroom.entity.User;
import com.aroominn.aroom.service.UserService;
import inter.UserAuthenticate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Objects;

import static com.aroominn.aroom.consist.Constant.USER_ID;

@SuppressWarnings("ALL")
@Component
public class AppInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AppInterceptor.class);


    @Autowired
    private UserService userService;

    /**
     * 进入controller层之前拦截请求
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("---------------------开始进入请求地址拦截----------------------------");
        String token = request.getHeader("token");
        log.info(token);
        User user = null;

            user = userService.validateToken(token);


        if (user == null) {
            String jsonObjectStr = new RespEntity(RespEntity.RespCode.FAILTOKEN).toString();
            returnJson(response, "{'msg':'token验证失败'}");
            return false;
        } else {
            log.info("request请求地址path[{}] uri[{}]", request.getContextPath(), request.getRequestURI());
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            UserAuthenticate userAuthenticate = method.getAnnotation(UserAuthenticate.class);
            //如果没有加注解则userAuthenticate为null
            if (Objects.nonNull(userAuthenticate)) {
                Integer userId = getUserId(request, user);
                //userAuthenticate.permission()取出permission判断是否需要校验权限
                if (userId == null || (userAuthenticate.permission() && !checkAuth(userId, request.getRequestURI()))) {
//                throw new FastRuntimeException(20001,"No access");
                    throw new RuntimeException("No access");
                }
            }


            return true;
        }


    }

    /**
     * 根据token获取用户ID
     *
     * @param request
     * @return
     */
    private Integer getUserId(HttpServletRequest request, User user) {
        //添加业务逻辑根据token获取用户UserId
        request.getHeader("H-User-Token");


        Integer userId = user.getId();
        String userMobile = "18888888888";
        request.setAttribute(USER_ID, userId);
//        request.setAttribute(USER_MOBILE,userMobile);
        return userId;
    }

    /**
     * 校验用户访问权限
     *
     * @param userId
     * @param requestURI
     * @return
     */
    private boolean checkAuth(Integer userId, String requestURI) {
        //添加业务逻辑根据UserId获取用户的权限组然后校验访问权限
        return true;
    }

    private void returnJson(HttpServletResponse response, String json) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            log.error("response error", e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

        log.info("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }


    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("---------------视图渲染之后的操作-------------------------0");
    }


}

