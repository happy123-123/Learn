package com.happy.service;

import com.happy.pojo.Clazz;
import com.happy.pojo.EmpQueryParam;
import com.happy.pojo.PageResult;

import java.util.List;

public interface ClazzService {
    PageResult<Clazz> findClazz(EmpQueryParam empQueryParam);

    void deleteById(Integer id);

    void add(Clazz clazz);

    Clazz findById(Integer id);

    void update(Clazz clazz);

    List<Clazz> findClazzList();
}
