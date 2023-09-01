package com.mo1isting.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mo1isting.backend.common.Result;
import com.mo1isting.backend.entity.User;
import com.mo1isting.backend.exception.CustomException;
import com.mo1isting.backend.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 功能:
 * 作者： Mooyi
 * 日期： 2023/8/27 21:04
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Resource
    private UserMapper userMapper;

    /**
     * 用户登录
     * @param account
     * @param userPassword
     * @return
     */
    public Result login(String account, String userPassword){
        //判定输入的account类型：userPhoneNumber:0,userName:1,userEmail:2; NoMatch:3
        int type = accountCheck(account);
        LambdaQueryWrapper<User> wrapper;
        if(type == 0){
            wrapper = Wrappers.<User>lambdaQuery().eq(User::getUserPhoneNumber,account).eq(User::getUserPassword,userPassword);
        }
        else if(type == 1){
            wrapper = Wrappers.<User>lambdaQuery().eq(User::getUserName,account).eq(User::getUserPassword,userPassword);
        }
        else if(type == 2){
            wrapper = Wrappers.<User>lambdaQuery().eq(User::getUserEmail,account).eq(User::getUserPassword,userPassword);
        }
        else{
            return Result.error("-1","账号输入格式错误");
        }
        User one = getOne(wrapper);
        if(one == null){
            return Result.error("-1","账号或密码错误");
        }

        return Result.success();
    }

    public int accountCheck(String account){
        //userPhoneNumber
        if(Pattern.matches("(13[0-9]|14[57]|15[012356789]|18[02356789])\\d{8}",account)){
            return 0;
        }
        //userName
        else if(Pattern.matches("^[a-zA-z_]+\\w*",account)){
            return 1;
        }
        //userEmail
        else if(Pattern.matches("^[a-zA-z0-9]+@[a-zA-z0-9]+\\.[a-zA-z0-9]+",account)){
            return 2;
        }
        //No Match
        else{
            return 3;
        }
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    public User register(User user){
        User one = getOne((Wrappers.<User>lambdaQuery().eq(User::getUserName,user.getUserName())));
        if(one == null){
            throw new CustomException("-1","用户已注册");
        }
        if(user.getUserPassword() == null){
            user.setUserPassword("123456");
        }
        save(user);
        return getOne((Wrappers.<User>lambdaQuery().eq(User::getUserName, user.getUserName())));
    }

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    public User getByUserName(String userName){
        User one = getOne((Wrappers.<User>lambdaQuery().eq(User::getUserName, userName)));
        return one;
    }
}
