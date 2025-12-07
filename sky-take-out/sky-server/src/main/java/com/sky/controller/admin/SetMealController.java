package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SetMealController {
    @Autowired
    private SetMealService setMealService;

    /*
    * 分页查询
    * */
    @GetMapping("/admin/setmeal/page")
    public Result findByPage(SetmealPageQueryDTO setmealPageQueryDTO){
        PageResult setMealByPage = setMealService.findSetMealByPage(setmealPageQueryDTO);
        return Result.success(setMealByPage);
    }

    /*
    * 添加套餐
    * */
    @PostMapping("/admin/setmeal")
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId")
    public Result add(@RequestBody SetmealDTO setmealDTO){
        setMealService.add(setmealDTO);
        return Result.success();
    }

    /*
    * 批量删除套餐
    * */
    @DeleteMapping("/admin/setmeal")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result delete(@RequestParam List<Long> ids){
        setMealService.delete(ids);
        return Result.success();
    }

    /*
    * 根据id查询套餐
    * */
    @GetMapping("/admin/setmeal/{id}")
    public Result getById(@PathVariable Long id){
        SetmealVO setmealVO = setMealService.getById(id);
        return Result.success(setmealVO);
    }

    /*
    * 修改套餐
    * */
    @PutMapping("/admin/setmeal")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result update(@RequestBody SetmealDTO setmealDTO){
        setMealService.update(setmealDTO);
        return Result.success();
    }

    /*
    * 套餐起售停售
    * */
    @PostMapping("/admin/setmeal/status/{status}")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result startOrStop(@PathVariable Integer status,Long id){
        setMealService.controlSetMeal(status,id);
        return Result.success();
    }
}
