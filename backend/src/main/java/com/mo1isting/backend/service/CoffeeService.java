package com.mo1isting.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mo1isting.backend.common.Result;
import com.mo1isting.backend.entity.Coffee;
import com.mo1isting.backend.mapper.CoffeeMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能: CoffeeService
 *
 * 作者： Mooyi
 * 日期： 2023/8/27 20:59
 */
@Service
public class CoffeeService extends ServiceImpl<CoffeeMapper, Coffee> {
    @Resource
    CoffeeMapper coffeeMapper;


    /**
     * 添加咖啡，使用coffeeName & coffeeShop 唯一确定咖啡
     * TODO: 不能用id来判断，所以用什么来确定唯一的咖啡呢？-> coffeeName & coffeeShop
     *
     * @param coffee
     * @return
     */
    public Result<String> addCoffee(Coffee coffee) {
        Coffee one  = getOne(Wrappers.<Coffee>lambdaQuery().eq(Coffee::getCoffeeName, coffee.getCoffeeName())
                .eq(Coffee::getCoffeeShop, coffee.getCoffeeShop()));
        if (one != null) {
            return Result.error("-1", "咖啡已存在");
        }
        save(coffee);
        return Result.success();
    }

    /**
     * 删除咖啡
     *
     * @param coffeeName
     * @param coffeeShop
     * @return
     */
    public Result deleteCoffee(String coffeeName, String coffeeShop) {
        int res = coffeeMapper.delete(Wrappers.<Coffee>lambdaQuery().eq(Coffee::getCoffeeName, coffeeName)
                .eq(Coffee::getCoffeeShop, coffeeShop));
        if (res == 0) {
            return Result.error("-1", "删除咖啡失败，请重试");
        }
        else {
            return Result.success();
        }
    }

    /**
     * 更新咖啡
     * TODO:问题：需要更新咖啡名字和店铺怎么处理？ -> 改成通过ID更新？
     * @param coffee
     * @return
     */
    public Result<Coffee> updateCoffee(Coffee coffee) {
        int res  = coffeeMapper.update(coffee, Wrappers.<Coffee>lambdaUpdate().eq(Coffee::getCoffeeName, coffee.getCoffeeName())
                .eq(Coffee::getCoffeeShop, coffee.getCoffeeShop()));
        if (res == 0) {
            return Result.error("-1", "更新咖啡失败，请重试");
        }
        else {
            Coffee coffee1 = getOne( Wrappers.<Coffee>lambdaUpdate().eq(Coffee::getCoffeeName, coffee.getCoffeeName())
                    .eq(Coffee::getCoffeeShop, coffee.getCoffeeShop()));
            return Result.success(coffee1);
        }
    }

    /**
     * 分页查询coffeeList
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Result<List<Coffee>> getCoffeeList(int pageNum, int pageSize) {
        List<Coffee> res = coffeeMapper.selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<>()).getRecords();
        if (res == null) {
            return Result.error("-1", "获取数据失败");
        }
        else {
            return Result.success(res);
        }
    }

    /**
     * 根据关键字搜索相关咖啡
     * 关键字可以是：coffeeName, coffeeShop, coffeeRoast, coffeeOrigin, coffeeFlavor, coffeeProcess
     * 即名字，商家，烘焙度，产地，风味，处理方法
     * TODO:多条件查询有问题，待修改
     * @param content
     * @return
     */
    public Result<List<Coffee>> searchCoffee(int pageNum, int pageSize, String content) {
        QueryWrapper<Coffee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("coffeeName", content).or()
                .eq("coffeeRoast", content).or()
                .eq("coffeeOrigin", content).or()
                .eq("coffeeFlavor", content).or()
                .eq("coffeeProcess", content);
        List<Coffee> res = coffeeMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper).getRecords();
        if (res == null) {
            return Result.error("-1", "获取数据失败");
        }
        else {
            return Result.success(res);
        }
    }
}
