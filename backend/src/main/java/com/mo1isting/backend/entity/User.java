package com.mo1isting.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能:
 * 作者： Mooyi
 * 日期： 2023/8/27 21:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private int userId;
    private String userName;
    private String userAvatar;
    private Boolean userGender;
    private String userBirth;
    private String userPassword;
    private String userPhoneNumber;
    private String userEmail;

}
