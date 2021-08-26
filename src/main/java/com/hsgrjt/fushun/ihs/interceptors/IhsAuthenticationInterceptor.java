package com.hsgrjt.fushun.ihs.interceptors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsgrjt.fushun.ihs.annotations.RequireLoginOnly;
import com.hsgrjt.fushun.ihs.annotations.RequirePermission;
import com.hsgrjt.fushun.ihs.system.entity.TokenSession;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.service.TokenSessionService;
import com.hsgrjt.fushun.ihs.system.service.UserService;
import com.hsgrjt.fushun.ihs.utils.JsonMessage;
import com.hsgrjt.fushun.ihs.utils.SysNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;

@Component
public class IhsAuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenSessionService session;
    @Autowired
    private UserService userService;

    protected String getToken(HttpServletRequest request) {
        String token = request.getHeader(SysNames.HEADER_USER_TOKEN);
        System.out.println(token);
        return token;
    }

    private boolean writeMessage(HttpServletResponse response, String message) throws Exception {
        PrintWriter out = response.getWriter();
        out.print(message);
        out.flush();
        return false;
    }

    private boolean writeMessage(R r, HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        ObjectMapper om = new ObjectMapper();
        try {
            out.print(om.writeValueAsString(r));
        } catch (JsonProcessingException e) {
            out.print("{\"success\":\"false\"}");
        } finally {
            out.flush();
            return false;
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 非Controller方法直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 捕获Controller方法，判断注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        boolean requirePermission = handlerMethod.getMethodAnnotation(RequirePermission.class) != null || handlerMethod.getBeanType().isAnnotationPresent(RequirePermission.class);
        boolean loginOnly = handlerMethod.getMethodAnnotation(RequireLoginOnly.class) != null || handlerMethod.getBeanType().isAnnotationPresent(RequireLoginOnly.class);
        if (requirePermission || loginOnly) {
            // 有注解，需要获取token
            TokenSession tokenSession = session.getTokenDetails(getToken(request));
            if (tokenSession == null || tokenSession.getExpirationTime().before(new Date())) {
                // token无效
                try {
                    session.deleteToken(tokenSession.getToken());
                } catch (Exception e) { } finally {
                    return writeMessage(R.error(JsonMessage.LOGIN_TIMEOUT), response);
                }
            } else {
                // token有效，判断是需要权限还是只需登录
                User user = userService.getById(tokenSession.getUserId());
                if (user == null) {
                    // 实则不会发生，避免数据异常，严谨
                    return writeMessage(R.error(JsonMessage.LOGIN_TIMEOUT), response);
                }
                // 将整个TokenSession和User对象存入当前请求头
                request.setAttribute(SysNames.REQUEST_ATTRIB_TOKEN, tokenSession);
                request.setAttribute(SysNames.REQUEST_ATTRIB_UCM, user);
                // 判断是需要指定权限，还是只要登录即可
                if (requirePermission) {
                    // 标记需要指定权限
                    RequirePermission anno = handlerMethod.getMethodAnnotation(RequirePermission.class);
                    if (anno == null) {
                        anno = handlerMethod.getBeanType().getAnnotation(RequirePermission.class);
                    }
                    if (user.getPermission().contains("|" + anno.value() + "|")) {
                        // 权限字符串以|分割，并且前后加|，例如用户有ABC三种权限，则存储|A|B|C|，判断用户是否拥有B权限则判断字符串是否包含|B|
                        // 有权限，皆大欢喜
                        return true;
                    } else {
                        // 没有权限
                        return writeMessage(R.error(JsonMessage.ACCESS_DENIED), response);
                    }
                } else {
                    // 标记为只需要登录即可，不废话，直接放行
                    return true;
                }
            }
        } else {
            // 啥也没标记，那就是不需要登录，直接放行
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

}
