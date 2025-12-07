package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /*
    * 新增菜品
    * */
    @PostMapping("/admin/dish")
    public Result add(@RequestBody DishDTO dishDTO){
        dishService.addDish(dishDTO);

        String key = "dish_" + dishDTO.getCategoryId();
        redisTemplate.delete(key);
        return Result.success();
    }

    /*
    * 菜品分页查询
    * */
    @GetMapping("/admin/dish/page")
    public Result find(DishPageQueryDTO dishPageQueryDTO){
        PageResult page = dishService.findPage(dishPageQueryDTO);
        return Result.success(page);
    }

    /*
    * 批量删除菜品
    * */
    @DeleteMapping("/admin/dish")
    public Result delete(@RequestParam List<Long> ids){
        dishService.deleteDish(ids);

        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);
        return Result.success();
    }

    /*
    * 根据分类id查询菜品
    * */
    @GetMapping("/admin/dish/list")
    public Result findList(Long categoryId){
        List<DishVO> list = dishService.findList(categoryId);
        return Result.success(list);
    }

    /*
    * 根据id查询菜品
    * */
    @GetMapping("/admin/dish/{id}")
    public Result findById(@PathVariable Long id){
        DishVO dishVO = dishService.findById(id);
        return Result.success(dishVO);
    }

    /*
    * 菜品启售停售
    * */
    @PostMapping("/admin/dish/status/{status}")
    public Result startOrStop(@PathVariable Integer status,Long id){
        dishService.controlDish(status,id);

        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);
        return Result.success();
    }

    /*
    * 修改菜品
    * */
    @PutMapping("/admin/dish")
    public Result update(@RequestBody DishDTO dishDTO){
        dishService.updateDish(dishDTO);

        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);
        return Result.success();
    }
}
