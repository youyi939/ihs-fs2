package com.hsgrjt.fushun.ihs.interceptors;

import com.hsgrjt.fushun.ihs.annotations.RequireLoginOnly;
import com.hsgrjt.fushun.ihs.annotations.RequirePermission;
import com.hsgrjt.fushun.ihs.utils.JsonMessage;
import com.hsgrjt.fushun.ihs.utils.SysNames;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class IhsAuthenticationInterceptor implements HandlerInterceptor {

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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        } else {
            return true;
        }
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        if (handlerMethod.getMethodAnnotation(RequirePermission.class) != null || handlerMethod.getBeanType().isAnnotationPresent(RequirePermission.class)) {
//            // 标记有需要指定权限
//            System.out.println("****************需要指定权限******************");
//            TokenSession tokenSession = session.getTokenDetails(getToken(request));
//            RequirePermission anno = handlerMethod.getMethodAnnotation(RequirePermission.class);
//            if (anno == null) {
//                anno = handlerMethod.getBeanType().getAnnotation(RequirePermission.class);
//            }
//            // 检查token是否有效
//            if (tokenSession == null) {
//                // token无效
//                return writeMessage(response, JsonMessage.LOGIN_FAIL);
//                // 检查token是否选择了门店
//            } else if (tokenSession.getUcmId() > 0) {
//                // 选择了门店，检查是否有权限
//                UserClubMapping ucm = ucmService.getUserClubMappingById(tokenSession.getUcmId());
//                if (ucm == null) {
//                    // 实则不会发生，避免数据异常，严谨
//                    return writeMessage(response, JsonMessage.CLUB_NOT_SELECT);
//                } else if (ucm.getPermission().contains("|" + anno.value() + "|")) {
//                    // 有权限，皆大欢喜
//                    request.setAttribute(SysNames.REQUEST_ATTRIB_TOKEN, tokenSession);
//                    request.setAttribute(SysNames.REQUEST_ATTRIB_UCM, ucm);
//                    return true;
//                } else {
//                    // 没有权限
//                    return writeMessage(response, JsonMessage.ACCESS_DENIED);
//                }
//            } else {
//                // 未选择门店
//                return writeMessage(response, JsonMessage.CLUB_NOT_SELECT);
//            }
//        } else if (handlerMethod.getMethodAnnotation(RequireLoginOnly.class) != null || handlerMethod.getBeanType().isAnnotationPresent(RequireLoginOnly.class)) {
//            // 标记仅需要登录
//            // System.out.println("****************需要登录******************");
//            TokenSession tokenSession = session.getTokenDetails(getToken(request));
//            // 检查token是否有效
//            if (tokenSession == null) {
//                // token无效
//                return writeMessage(response, JsonMessage.LOGIN_FAIL);
//            } else {
//                // token有效，放行
//                request.setAttribute(SysNames.REQUEST_ATTRIB_TOKEN, tokenSession);
//                return true;
//            }
//        } else {
//            // 啥也没标记，那就是不需要登录，直接放行
//            return true;
//        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

}
