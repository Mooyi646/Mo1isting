package com.mo1isting.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 功能: 咖啡参数
 * 作者： Mooyi
 * 日期： 2023/8/27 1:11
 */
//getter,setter函数生成
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coffee {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private int coffeeId;
    private String coffeeName;
    private String coffeePic;
    private String coffeeShop;
    private String coffeeOrigin;
    private String coffeeFlavor;
    private int coffeeProcess;
    private int coffeeRoast;
}
