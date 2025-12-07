package com.sky.mapper;

import com.sky.anno.Annotation;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Annotation(value = OperationType.INSERT)
    void addAll(Category category);

    Long count();

    List<Category> findByPage(CategoryPageQueryDTO categoryPageQueryDTO);

    @Annotation(value = OperationType.UPDATE)
    void updateAll(Category category);

    @Annotation(value = OperationType.UPDATE)
    void controlAll(Category category);

    void deleteAll(Long id);

    List<Category> findCtgByType(Integer type);
}
