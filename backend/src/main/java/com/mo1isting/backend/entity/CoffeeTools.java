package com.mo1isting.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 功能: CoffeeTools
 * 作者： Mooyi
 * 日期： 2023/12/19 20:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("coffeetools")
public class CoffeeTools {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private int toolId;
    private Date toolBoughtDate;
    private String toolName;
    private String toolPic;
    private Double toolPrice;
    private String toolShop;
    private String toolProducer;
}
