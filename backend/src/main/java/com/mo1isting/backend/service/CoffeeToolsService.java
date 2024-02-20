package com.mo1isting.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    public Result addTool(CoffeeTools tool, Integer userId) {
        CoffeeTools tool1 = toolsMapper.selectOne(Wrappers.<CoffeeTools>lambdaQuery().eq(CoffeeTools::getToolName, tool.getToolName()));

        if (tool1 != null) {
            return Result.error("-1", "器具已存在");
        }
        tool.setUserId(userId);
        save(tool);
        return Result.success();
    }

    /**
     * 更新器具
     *
     * @param tool
     * @return
     */
    public Result updateTool(CoffeeTools tool, Integer userId) {
        int res = toolsMapper.update(tool, Wrappers.<CoffeeTools>lambdaUpdate().eq(CoffeeTools::getToolId, tool.getToolId())
                .eq(CoffeeTools::getUserId, userId));
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
    public Result deleteTool(String toolName, Integer userId) {
        int res = toolsMapper.delete(Wrappers.<CoffeeTools>lambdaQuery().eq(CoffeeTools::getToolName, toolName).eq(CoffeeTools::getUserId, userId));

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
    public Result<List<CoffeeTools>> getToolsList(int pageNum, int pageSize, Integer userId) {
        List<CoffeeTools> res = toolsMapper.selectPage(new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<CoffeeTools>().eq(CoffeeTools::getUserId, userId)).getRecords();

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
    public Result<List<CoffeeTools>> searchTools(int pageNum, int pageSize, String content, Integer userId) {
        List<CoffeeTools> res = toolsMapper.selectPage(new Page<>(pageNum, pageSize),
                Wrappers.<CoffeeTools>lambdaQuery().eq(CoffeeTools::getUserId, userId)
                        .and(superWrapper -> superWrapper.like(CoffeeTools::getToolName, content).or(
                                wrappers -> wrappers.like(CoffeeTools::getToolShop, content).or(
                                        wrappers1 -> wrappers1.like(CoffeeTools::getToolProducer, content))))).getRecords();

        if (res == null) {
            return Result.error("-1", "搜索失败");
        }
        else {
            return Result.success(res);
        }
    }
}
