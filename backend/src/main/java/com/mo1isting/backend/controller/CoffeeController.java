package com.mo1isting.backend.controller;

import com.mo1isting.backend.common.Result;
import com.mo1isting.backend.entity.Coffee;
import com.mo1isting.backend.entity.User;
import com.mo1isting.backend.service.CoffeeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 功能: Coffee相关接口
 * 作者： Mooyi
 * 日期： 2023/8/27 20:58
 */
@CrossOrigin
@RestController
@RequestMapping("/coffee")
public class CoffeeController {
    @Resource
    CoffeeService coffeeService;

    @Resource
    HttpServletRequest request;


    /**
     * 添加咖啡
     *
     * @param coffee
     * @param request
     * @return
     */
    @PostMapping("/addCoffee")
    public Result<Coffee> addCoffee(@RequestBody Coffee coffee, HttpServletRequest request){
        Integer userId = Integer.parseInt((String)request.getSession().getAttribute("userId"));
        Result<Coffee> res = coffeeService.addCoffee(coffee, userId);
        return res;
    }

    /**
     * 删除咖啡
     *
     * @param coffeeName
     * @param coffeeShop
     * @return
     */
    @PostMapping("/deleteCoffee")
    public Result deleteCoffee(@RequestParam(value = "coffeeName")String coffeeName,
                               @RequestParam(value = "coffeeShop")String coffeeShop){
        Integer userId = Integer.parseInt((String)request.getSession().getAttribute("userId"));
        Result res = coffeeService.deleteCoffee(coffeeName, coffeeShop, userId);
        request.getSession().setAttribute("coffee", res);
        return res;
    }

    /**
     * 更新咖啡
     *
     * @param coffee
     * @return
     */
    @PostMapping("/updateCoffee")
    public Result<Coffee> updateCoffee(@RequestBody Coffee coffee){
        Integer userId = Integer.parseInt((String)request.getSession().getAttribute("userId"));
        Result<Coffee> res = coffeeService.updateCoffee(coffee, userId);
        request.getSession().setAttribute("coffee", res);
        return res;
    }

    /**
     * 展示所有coffee，需要分页？
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    @PostMapping("/getCoffeeList")
    public Result<List<Coffee>> getCoffeeList(@RequestParam(value="pageNum") String pageNum,
                                              @RequestParam(value="pageSize") String pageSize){
        Integer userId = Integer.parseInt((String)request.getSession().getAttribute("userId"));
        Result<List<Coffee>> res = coffeeService.getCoffeeList(Integer.parseInt(pageNum), Integer.parseInt(pageSize), userId);
        request.getSession().setAttribute("coffeeList", res);
        return res;
    }

    @PostMapping("/search")
    public Result<List<Coffee>> searchCoffee(@RequestParam(value="pageNum") String pageNum,
                                             @RequestParam(value="pageSize") String pageSize,
                                             @RequestParam(value="content") String content){
        Integer userId = Integer.parseInt((String)request.getSession().getAttribute("userId"));
        Result<List<Coffee>> res = coffeeService.searchCoffee(Integer.parseInt(pageNum), Integer.parseInt(pageSize),content, userId);
        request.getSession().setAttribute("coffeeList", res);
        return res;
    }
}
