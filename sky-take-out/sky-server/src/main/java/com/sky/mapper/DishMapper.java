package com.sky.mapper;

import com.sky.anno.Annotation;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface DishMapper {

    @Annotation(value = OperationType.INSERT)
    void addDish(Dish dish);

    Long count();

    List<Dish> findByPage(DishPageQueryDTO dishPageQueryDTO);

    void deleteAll(List<Long> list);

    Dish findById(Long id);

    @Annotation(value = OperationType.UPDATE)
    void controlAll(Dish dish);

    List<Dish> findList(Dish dish);

    @Annotation(value = OperationType.UPDATE)
    void updateDish(Dish dish);

    Integer getOverViewDish(Integer status);
}
