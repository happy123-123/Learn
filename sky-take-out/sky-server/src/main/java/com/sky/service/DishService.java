package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    void addDish(DishDTO dishDTO);

    PageResult findPage(DishPageQueryDTO dishPageQueryDTO);

    void deleteDish(List<Long> ids);

    void controlDish(Integer status, Long id);

    List<DishVO> findList(Long categoryId);

    DishVO findById(Long id);

    void updateDish(DishDTO dishDTO);
}
