package com.mo1isting.backend.controller;

import com.mo1isting.backend.common.Result;
import com.mo1isting.backend.entity.CoffeeTools;
import com.mo1isting.backend.service.CoffeeToolsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能: CoffeeToolsController
 * 作者： Mooyi
 * 日期： 2023/12/19 20:33
 */
@CrossOrigin
@RestController
@RequestMapping("/tools")
public class CoffeeToolsController {
    @Resource
    CoffeeToolsService toolsService;

    @Resource
    private HttpServletRequest request;

    /**
     * 增加器具
     * @param tool
     */
    @PostMapping("/addTool")
    public Result addTool(@RequestBody CoffeeTools tool){
        Result res = toolsService.addTool(tool);
        request.getSession().setAttribute("tool", res);
        return res;
    }

    @PostMapping("/updateTool")
    public Result<CoffeeTools> updateTool(@RequestBody CoffeeTools tool){
        Result res = toolsService.updateTool(tool);
        return res;
    }

    @PostMapping("/deleteTool")
    public Result deleteTool(@RequestParam("toolName") String toolName){
        Result res = toolsService.deleteTool(toolName);
        return res;
    }

    @PostMapping("/getToolsList")
    public Result<List<CoffeeTools>> getToolsList(@RequestParam("pageNum")String pageNum,
                                                  @RequestParam("pageSize")String pageSize){
        Result<List<CoffeeTools>> res = toolsService.getToolsList(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        return res;
    }

    @PostMapping("/search")
    public Result<List<CoffeeTools>> searchTools(@RequestParam("pageNum")String pageNum,
                                            @RequestParam("pageSize")String pageSize,
                                            @RequestParam("content")String content){
        Result<List<CoffeeTools>> res = toolsService.searchTools(Integer.parseInt(pageNum), Integer.parseInt(pageSize), content);
        return res;
    }
}
