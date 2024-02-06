package com.mo1isting.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.mo1isting.backend.common.Result;
import com.mo1isting.backend.entity.Coffee;
import com.mo1isting.backend.entity.CoffeeUser;
import com.mo1isting.backend.entity.User;
import com.mo1isting.backend.mapper.CoffeeMapper;
import com.mo1isting.backend.mapper.CoffeeUserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    CoffeeUserMapper coffeeUserMapper;
    /**
     * 处理过程map
     * 处理方法， 1：'水洗',2： '日晒',3： '蜜处理',4： '香精'
     */
    private static Map<String, Integer> processMap = new HashMap<>();

    /**
     * 烘焙度map
     * 1:'浅烘', 2:'中烘', 3:'中深烘', 4:'深烘'
     */
    private static Map<String, Integer> roastMap = new HashMap<>();

    static{
        processMap.put("水洗", 1);
        processMap.put("日晒", 2);
        processMap.put("蜜处理", 3);
        processMap.put("香精", 4);

        roastMap.put("浅烘", 1);
        roastMap.put("中烘", 2);
        roastMap.put("中深烘", 3);
        roastMap.put("深烘", 4);
    }

    /**
     * 添加咖啡，使用coffeeName & coffeeShop 唯一确定咖啡
     * TODO: 不能用id来判断，所以用什么来确定唯一的咖啡呢？-> coffeeName & coffeeShop
     *
     * @param coffee
     * @return
     */
    public Result<Coffee> addCoffee(Coffee coffee, Integer userId) {
        Coffee one  = getOne(Wrappers.<Coffee>lambdaQuery().eq(Coffee::getCoffeeName, coffee.getCoffeeName())
                .eq(Coffee::getCoffeeShop, coffee.getCoffeeShop()));
        if (one != null) {
            return Result.error("-1", "咖啡已存在");
        }
        save(coffee);
        Coffee updatedOne = getOne(Wrappers.<Coffee>lambdaQuery().eq(Coffee::getCoffeeName, coffee.getCoffeeName())
                .eq(Coffee::getCoffeeShop, coffee.getCoffeeShop()));

        coffeeUserMapper.insert(new CoffeeUser(updatedOne.getCoffeeId(), userId));
        return Result.success(updatedOne);
    }

    /**
     * 删除咖啡
     *
     * @param coffeeName
     * @param coffeeShop
     * @return
     */
    public Result deleteCoffee(String coffeeName, String coffeeShop, Integer userId) {
        Coffee one  = getOne(Wrappers.<Coffee>lambdaQuery().eq(Coffee::getCoffeeName, coffeeName)
                .eq(Coffee::getCoffeeShop, coffeeShop));
        // 删除连接表
        coffeeUserMapper.delete(Wrappers.<CoffeeUser>lambdaQuery().eq(CoffeeUser::getCoffeeId, one.getCoffeeId())
                .eq(CoffeeUser::getUserId, userId));
        //删除咖啡表
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
     *
     * @param coffee
     * @return
     */
    public Result<Coffee> updateCoffee(Coffee coffee, Integer userId) {
        // 不存在即新增
        if (coffee == null || coffee.getCoffeeId() < 0) {
            return addCoffee(coffee, userId);
        }
        else {
            int res = coffeeMapper.update(coffee, Wrappers.<Coffee>lambdaUpdate().eq(Coffee::getCoffeeId, coffee.getCoffeeId()));
            if (res == 0) {
                return Result.error("-1", "更新咖啡失败，请重试");
            } else {
                Coffee coffee1 = getOne(Wrappers.<Coffee>lambdaUpdate().eq(Coffee::getCoffeeId, coffee.getCoffeeId()));
                return Result.success(coffee1);
            }
        }
    }

    /**
     * 分页查询coffeeList
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Result<List<Coffee>> getCoffeeList(int pageNum, int pageSize, int userId) {
        //List<Coffee> res = coffeeMapper.selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<>()).getRecords();
        List<Coffee> coffeeList = coffeeMapper.selectJoinPage(new Page<>(pageNum, pageSize), Coffee.class,
                JoinWrappers.lambda(Coffee.class).selectAll(Coffee.class)
                        .leftJoin(CoffeeUser.class, CoffeeUser::getCoffeeId, Coffee::getCoffeeId)
                        .eq(CoffeeUser::getUserId, userId)).getRecords();
        if (coffeeList == null) {
            return Result.error("-1", "获取数据失败");
        }
        else {
            return Result.success(coffeeList);
        }
    }

    /**
     * 根据关键字搜索相关咖啡，搜索范围为个人咖啡
     * 关键字可以是：coffeeName, coffeeShop, coffeeRoast, coffeeOrigin, coffeeFlavor, coffeeProcess
     * 即名字，商家，烘焙度，产地，风味，处理方法
     *
     * @param content
     * @return
     */
    public Result<List<Coffee>> searchCoffee(int pageNum, int pageSize, String content, Integer userId) {
//        QueryWrapper<Coffee> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like("coffeeName", content).or(
//                wrapper -> wrapper.like("coffeeShop", content).or(
//                wrappers1 -> wrappers1.eq("coffeeRoast", roastMap.get(content)).or(
//                        wrappers2 -> wrappers2.like("coffeeOrigin", content).or(
//                                wrappers3 -> wrappers3.like("coffeeFlavor", content).or(
//                                        wrappers4 -> wrappers4.eq("coffeeProcess", processMap.get(content)))))));
//
//        List<Coffee> res = coffeeMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper).getRecords();

        List<Coffee> coffeeList = coffeeMapper.selectJoinPage(new Page<>(pageNum, pageSize), Coffee.class,
                JoinWrappers.lambda(Coffee.class).selectAll(Coffee.class)
                        .leftJoin(CoffeeUser.class, CoffeeUser::getCoffeeId, Coffee::getCoffeeId)
                        .eq(CoffeeUser::getUserId, userId)
                        .and(superWrapper -> superWrapper.like("coffeeName", content).or(
                                wrapper -> wrapper.like("coffeeShop", content).or(
                                        wrappers1 -> wrappers1.eq("coffeeRoast", roastMap.get(content)).or(
                                                wrappers2 -> wrappers2.like("coffeeOrigin", content).or(
                                                        wrappers3 -> wrappers3.like("coffeeFlavor", content).or(
                                                                wrappers4 -> wrappers4.eq("coffeeProcess", processMap.get(content)))))))))
                .getRecords();

        if (coffeeList == null) {
            return Result.error("-1", "无搜索结果");
        }
        else {
            return Result.success(coffeeList);
        }
    }
}
