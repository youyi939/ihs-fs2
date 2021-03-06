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
        // ???Controller??????????????????
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // ??????Controller?????????????????????
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        boolean requirePermission = handlerMethod.getMethodAnnotation(RequirePermission.class) != null || handlerMethod.getBeanType().isAnnotationPresent(RequirePermission.class);
        boolean loginOnly = handlerMethod.getMethodAnnotation(RequireLoginOnly.class) != null || handlerMethod.getBeanType().isAnnotationPresent(RequireLoginOnly.class);
        if (requirePermission || loginOnly) {
            // ????????????????????????token
            TokenSession tokenSession = session.getTokenDetails(getToken(request));
            if (tokenSession == null || tokenSession.getExpirationTime().before(new Date())) {
                // token??????
                try {
                    session.deleteToken(tokenSession.getToken());
                } catch (Exception e) { } finally {
                    return writeMessage(R.error(JsonMessage.LOGIN_TIMEOUT), response);
                }
            } else {
                // token????????????????????????????????????????????????
                User user = userService.getById(tokenSession.getUserId());
                if (user == null) {
                    // ????????????????????????????????????????????????
                    return writeMessage(R.error(JsonMessage.LOGIN_TIMEOUT), response);
                }
                // ?????????TokenSession???User???????????????????????????
                request.setAttribute(SysNames.REQUEST_ATTRIB_TOKEN, tokenSession);
                request.setAttribute(SysNames.REQUEST_ATTRIB_UCM, user);
                // ??????????????????????????????????????????????????????
                if (requirePermission) {
                    // ????????????????????????
                    RequirePermission anno = handlerMethod.getMethodAnnotation(RequirePermission.class);
                    if (anno == null) {
                        anno = handlerMethod.getBeanType().getAnnotation(RequirePermission.class);
                    }
                    if (user.getPermission().contains("|" + anno.value() + "|")) {
                        // ??????????????????|????????????????????????|??????????????????ABC????????????????????????|A|B|C|???????????????????????????B????????????????????????????????????|B|
                        // ????????????????????????
                        return true;
                    } else {
                        // ????????????
                        return writeMessage(R.error(JsonMessage.ACCESS_DENIED), response);
                    }
                } else {
                    // ?????????????????????????????????????????????????????????
                    return true;
                }
            }
        } else {
            // ?????????????????????????????????????????????????????????
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
