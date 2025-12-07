package com.happy.mapper;

import com.happy.pojo.Clazz;
import com.happy.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClazzMapper {
    Long findAll();

    List<Clazz> find(EmpQueryParam empQueryParam);

    void deleteById(Integer id);

    void add(Clazz clazz);

    Clazz findById(Integer id);

    void update(Clazz clazz);

    List<Clazz> findClazzList();

    Integer linkClazz(Integer id);
}
