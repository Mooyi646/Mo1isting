package com.mo1isting.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mo1isting.backend.common.Result;
import com.mo1isting.backend.entity.CoffeeTools;
import com.mo1isting.backend.mapper.CoffeeToolsMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能: CoffeeToolsService
 * 作者： Mooyi
 * 日期： 2023/12/19 20:31
 */
@Service
public class CoffeeToolsService extends ServiceImpl<CoffeeToolsMapper, CoffeeTools> {
    @Resource
    CoffeeToolsMapper toolsMapper;

    /**
     * 增加器具
     *
     * @param tool
     * @return
     */
    public Result addTool(CoffeeTools tool) {
        CoffeeTools tool1 = toolsMapper.selectOne(Wrappers.<CoffeeTools>lambdaQuery()
                .eq(CoffeeTools::getToolName, tool.getToolName()));

        if (tool1 != null) {
            return Result.error("-1", "器具已存在");
        }
        else {
            save(tool);
            return Result.success();
        }
    }

    /**
     * 更新器具
     * @param tool
     * @return
     */
    public Result updateTool(CoffeeTools tool) {
        int res = toolsMapper.update(tool, Wrappers.<CoffeeTools>lambdaUpdate().eq(CoffeeTools::getToolId, tool.getToolId()));
        CoffeeTools tool_updated =  toolsMapper.selectOne(Wrappers.<CoffeeTools>lambdaQuery()
                .eq(CoffeeTools::getToolId, tool.getToolId()));

        if (res == 0) {
            return Result.error("-1", "器具更新失败");
        }
        else {
            return Result.success(tool_updated);
        }
    }

    /**
     * 删除指定器具
     * @param toolName
     * @return
     */
    public Result deleteTool(String toolName) {
        int res = toolsMapper.delete(Wrappers.<CoffeeTools>lambdaQuery().eq(CoffeeTools::getToolName, toolName));

        if (res == 0) {
            return Result.error("-1", "器具删除失败");
        }
        else {
            return Result.success();
        }
    }

    /**
     * 获取器具分页列表
     * @param pageNum 页码
     * @param pageSize 每页数据量
     * @return
     */
    public Result<List<CoffeeTools>> getToolsList(int pageNum, int pageSize) {
        List<CoffeeTools> res = toolsMapper.selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<>()).getRecords();

        if (res == null) {
            return Result.error("-1", "获取数据失败");
        }
        else {
            return Result.success(res);
        }
    }

    /**
     * 搜索咖啡器具，分页
     * 关键字可以是：coffeeToolsName, coffeeToolsShop, coffeeToolsProducer
     *
     * @param pageNum
     * @param pageSize
     * @param content
     * @return
     */
    public Result<List<CoffeeTools>> searchTools(int pageNum, int pageSize, String content) {
        List<CoffeeTools> res = toolsMapper.selectPage(new Page<>(pageNum, pageSize),
                Wrappers.<CoffeeTools>lambdaQuery().like(CoffeeTools::getToolName, content).or(
                        wrappers -> wrappers.like(CoffeeTools::getToolShop, content).or(
                                wrappers1 -> wrappers1.like(CoffeeTools::getToolProducer, content)))).getRecords();

        if (res == null) {
            return Result.error("-1", "搜索失败");
        }
        else {
            return Result.success(res);
        }
    }
}
