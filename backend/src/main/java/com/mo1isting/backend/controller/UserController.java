package com.mo1isting.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mo1isting.backend.common.Result;
import com.mo1isting.backend.entity.User;
import com.mo1isting.backend.exception.CustomException;
import com.mo1isting.backend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能:
 * 作者： Mooyi
 * 日期： 2023/8/27 21:17
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    public static final ConcurrentHashMap<String, User> MAP = new ConcurrentHashMap<>();

    @Resource
    private UserService userService;

    @Resource
    private HttpServletRequest request;

    public User getUser() {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("-1", "请登录");
        }
        return user;
    }

    /**
     * 登录
     * @param map
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody Map map,
                              HttpServletRequest request) throws JsonProcessingException {
        Result<User> res = userService.login(map.get("account").toString(),map.get("userPassword").toString());
        request.getSession().setAttribute("user",res);
        return res;
    }

    /**
     * 注册
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user, HttpServletRequest request){
        if(user.getUserPassword() == null){
            user.setUserPassword("123456");
        }

        Result<User> res = userService.register(user);
        request.getSession().setAttribute("user",res);

        return res;
    }
}
