package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetMealDishMapper {
    void addDish(List<SetmealDish> setmealDishes);

    List<SetmealDish> getById(Long id);

    void deleteById(Long setmealId);
}
