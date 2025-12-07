package com.sky.controller.user;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserDishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /*
    * 根据分类id查询菜品
    * */
    @GetMapping("/user/dish/list")
    public Result findList(Long categoryId){
        String key = "dish_" + categoryId;
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        // 缓存存在
        if(list != null && list.size() > 0){
            return Result.success(list);
        }

        list = dishService.findList(categoryId);
        redisTemplate.opsForValue().set(key,list);
        return Result.success(list);
    }
}
