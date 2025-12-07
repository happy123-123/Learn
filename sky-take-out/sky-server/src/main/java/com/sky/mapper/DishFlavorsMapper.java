package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorsMapper {
    void addDishFlavor(List<DishFlavor> flavors);

    void deleteAll(List<Long> list);

    void controlAll(Integer status, Long id);

    List<DishFlavor> findAllById(Long dishId);

    void deleteByDishId(Long dishId);
}
