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
 * 日期： 2024/1/17 17:11
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("coffee_user")
public class CoffeeUser {
    private int coffeeId;
    private int userId;
}
