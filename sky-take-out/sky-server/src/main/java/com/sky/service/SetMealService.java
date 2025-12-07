package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetMealService {
    PageResult findSetMealByPage(SetmealPageQueryDTO setmealPageQueryDTO);

    void add(SetmealDTO setmealDTO);

    void delete(List<Long> ids);

    void update(SetmealDTO setmealDTO);

    SetmealVO getById(Long id);

    void controlSetMeal(Integer status, Long id);

    List<Setmeal> findByCtg(Long categoryId);

    List<DishItemVO> getDishItemVO(Long id);
}
