package com.mo1isting.backend.controller;

import com.mo1isting.backend.common.Result;
import com.mo1isting.backend.entity.Coffee;
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
    public Result<String> addCoffee(@RequestBody Coffee coffee, HttpServletRequest request){
        Result<String> res = coffeeService.addCoffee(coffee);
        request.getSession().setAttribute("coffee", res);
        return res;
    }

    /**
     * 删除咖啡
     *
     * @param map
     * @param request
     * @return
     */
    @PostMapping("/deleteCoffee")
    public Result deleteCoffee(@RequestBody Map map, HttpServletRequest request){
        Result res = coffeeService.deleteCoffee(map.get("coffeeName").toString(), map.get("coffeeShop").toString());
        request.getSession().setAttribute("coffee", res);
        return res;
    }

    /**
     * 更新咖啡
     *
     * @param coffee
     * @param request
     * @return
     */
    @PostMapping("/updateCoffee")
    public Result<Coffee> updateCoffee(@RequestBody Coffee coffee, HttpServletRequest request){
        Result<Coffee> res = coffeeService.updateCoffee(coffee);
        request.getSession().setAttribute("coffee", res);
        return res;
    }

    /**
     * 展示所有coffee，需要分页？
     *
     * @param request
     * @return
     */
    @PostMapping("/getCoffeeList")
    public Result<List<Coffee>> getCoffeeList(@RequestParam(value="pageNum") String pageNum,
                                              @RequestParam(value="pageSize") String pageSize,
                                              HttpServletRequest request){
        Result<List<Coffee>> res = coffeeService.getCoffeeList(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        request.getSession().setAttribute("coffeeList", res);
        return res;
    }

    @PostMapping("/search")
    public Result<List<Coffee>> searchCoffee(@RequestParam(value="pageNum") String pageNum,
                                             @RequestParam(value="pageSize") String pageSize,
                                             @RequestParam(value="content") String content,HttpServletRequest request){
        Result<List<Coffee>> res = coffeeService.searchCoffee(Integer.parseInt(pageNum), Integer.parseInt(pageSize),content);
        request.getSession().setAttribute("coffeeList", res);
        return res;
    }
}
