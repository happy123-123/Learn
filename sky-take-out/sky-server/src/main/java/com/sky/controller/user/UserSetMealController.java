package com.sky.controller.user;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserSetMealController {
    @Autowired
    private SetMealService setMealService;

    @GetMapping("/user/setmeal/list")
    @Cacheable(cacheNames = "setmealCache",key = "#categoryId")
    public Result findList(Long categoryId){
        List<Setmeal> list = setMealService.findByCtg(categoryId);
        return Result.success(list);
    }

    @GetMapping("/user/setmeal/dish/{id}")
    public Result findById(@PathVariable Long id){
        List<DishItemVO> dishItemVO = setMealService.getDishItemVO(id);
        return Result.success(dishItemVO);
    }
}
