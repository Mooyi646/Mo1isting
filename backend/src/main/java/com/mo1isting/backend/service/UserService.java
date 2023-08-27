package com.mo1isting.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mo1isting.backend.entity.User;
import com.mo1isting.backend.exception.CustomException;
import com.mo1isting.backend.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
     * @param userName
     * @param userPassword
     * @return
     */
    public User login(User user){
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery().eq(User::getUserName,user.getUserName()).eq(User::getUserPassword,user.getUserPassword());
        User one = getOne(wrapper);
        if(one == null){
            throw new CustomException("-1","账号或密码错误");
        }

        return one;
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
