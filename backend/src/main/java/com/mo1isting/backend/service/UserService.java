package com.mo1isting.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mo1isting.backend.common.utils.TokenUtil;
import com.mo1isting.backend.common.Result;
import com.mo1isting.backend.entity.User;
import com.mo1isting.backend.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * 功能: UserService
 * 作者： Mooyi
 * 日期： 2023/8/27 21:04
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Resource
    private UserMapper userMapper;

    /**
     * 用户登录
     *
     * @param account
     * @param userPassword
     * @return
     */
    public Result login(String account, String userPassword) throws JsonProcessingException {
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

        String token = TokenUtil.getToken(one);

        return Result.success(token);
    }

    /**
     * 登录账号类型
     *
     * @param account
     * @return
     */
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
     *
     * @param user
     * @return
     */
    public Result register(User user){
        User one = getOne((Wrappers.<User>lambdaQuery().eq(User::getUserName, user.getUserName())));
        if(one != null){
            return Result.error("-1","用户已注册");
        }
        save(user);
        return Result.success();
    }

    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    public Result<User> getUserInfoByUserName(String userName) {
        User one = getOne((Wrappers.<User>lambdaQuery().eq(User::getUserName, userName)));
        one.setUserPassword("******");
        if (one != null) {
            return Result.success(one);
        }
        else{
            return Result.error("-1", "获取用户信息失败");
        }
    }

    /**
     * 用户注销
     *
     * @param userName
     * @return
     */
    public Result<String> deleteUser(String userName) {
        int res = userMapper.delete(Wrappers.<User>lambdaQuery().eq(User::getUserName, userName));
        if (res == 0) {
            return Result.error("-1", "用户注销失败");
        }
        else {
            return Result.success(new String("用户注销成功"));
        }
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    public Result<User> updateUserInfo(User user) {
        int res = userMapper.update(user, Wrappers.<User>lambdaQuery().eq(User::getUserName, user.getUserName()));
        if (res == 0) {
            return Result.error("-1", "用户信息注销失败");
        }
        else {
            User one = getOne((Wrappers.<User>lambdaQuery().eq(User::getUserName, user.getUserName())));
            one.setUserPassword("******");
            return Result.success(one);
        }
    }
}
