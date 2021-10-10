package com.hsgrjt.fushun.ihs.system.controller;


import com.hsgrjt.fushun.ihs.config.GlobalConfiguration;
import com.hsgrjt.fushun.ihs.system.entity.TokenSession;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.service.TokenSessionService;
import com.hsgrjt.fushun.ihs.system.service.UserService;
import com.hsgrjt.fushun.ihs.utils.JsonMessage;
import com.hsgrjt.fushun.ihs.utils.TokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author 晟翼科技
 * @since 2021-08-10
 */
@Api(tags = {"CORE 用户controller"})
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenSessionService tokenService;

    @PostMapping("add")
    public String addUser(@RequestBody User user) {
        if (userService.save(user)) {
            return JsonMessage.SUCCESS;
        } else {
            return JsonMessage.DATABASE_ERROR;
        }
    }

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable String id) {
        if (userService.removeById(id)) {
            return JsonMessage.SUCCESS;
        } else {
            return JsonMessage.DATABASE_ERROR;
        }
    }

    @PostMapping("modify")
    public String modifyUser(@RequestBody User user) {
        if (userService.updateById(user)) {
            return JsonMessage.SUCCESS;
        } else {
            return JsonMessage.DATABASE_ERROR;
        }
    }

    @GetMapping("has_username/{username}")
    public String hasUsername(@PathVariable String username) {
        if (userService.hasUsername(username)) {
            return JsonMessage.EXIST;
        } else {
            return JsonMessage.NOT_EXIST;
        }
    }

    @GetMapping("all")
    public List<User> getAllUser() {
        return userService.list();
    }

    @ResponseBody
    @PostMapping("login")
    public Object login(@RequestBody User loginInfo, HttpServletResponse response) {
        if (loginInfo != null) {
            User user = userService.getUserByLoginUsername(loginInfo.getUsername());
            System.out.println(loginInfo);
            if (user == null) {
                return JsonMessage.LOGIN_FAIL;
            } else {
                // 用户信息存在，比对密码
                if (user.getPassword().equals(loginInfo.getPassword())) {
                    // 密码正确，生成token
                    int cookieSaveSeconds = GlobalConfiguration.COOKIE_SAVE_SECONDS;
                    if (loginInfo.getVersion() == 1) {
                        cookieSaveSeconds = GlobalConfiguration.COOKIE_SAVE_SECONDS_REMEMBER;
                    }
                    String token = TokenUtil.createJwtToken("userId:" + user.getId(), cookieSaveSeconds);
                    TokenSession session = new TokenSession(token, cookieSaveSeconds, user.getId());
                    if (tokenService.save(session)) {
                        user.setPassword(token);
                        return user;
                    } else {
                        return JsonMessage.DATABASE_ERROR;
                    }
                    // 将token封装入密码回传
                } else {
                    return JsonMessage.LOGIN_FAIL;
                }
            }
        } else {
            return JsonMessage.PARAMETER_ERROR;
        }
    }

    @GetMapping("logout/{userId}")
    public String logout(@PathVariable String userId, HttpServletRequest request) {
        // 清除session
        tokenService.removeById(request.getHeader("User-Token"));
        return JsonMessage.SUCCESS;
    }

    @GetMapping("/isUserVip")
    public Boolean isUserVip(@RequestParam ("id") Integer id,@RequestParam("type")String type){
        return userService.isUserVip(id,type);
    }

}

