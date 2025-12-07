package com.sky.mapper;

import com.sky.anno.Annotation;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SetMealMapper {
    @Annotation(value = OperationType.UPDATE)
    void control(Dish dish);

    @Select("select count(*) from sky_take_out.setmeal_dish where dish_id=#{dishId}")
    Long findByDishId(Long dishId);

    Long count();

    List<Setmeal> findpageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    @Annotation(value = OperationType.INSERT)
    void addSetMeal(Setmeal setmeal);

    void deleteDish(List<Long> ids);

    Setmeal findById(Long id);

    @Annotation(value = OperationType.UPDATE)
    void updateSetMeal(Setmeal setmeal);

    @Annotation(value = OperationType.UPDATE)
    void controlSetMeal(Setmeal setmeal);

    List<Setmeal> findByCtg(Setmeal setmeal);


    Integer getOverViewSetmeal(Integer status);
}
