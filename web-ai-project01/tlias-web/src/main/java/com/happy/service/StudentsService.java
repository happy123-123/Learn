package com.happy.service;

import com.happy.pojo.EmpQueryParam;
import com.happy.pojo.PageResult;
import com.happy.pojo.Student;

import java.util.List;

public interface StudentsService {

    PageResult<Student> findAll(EmpQueryParam empQueryParam);

    void insert(Student student);

    Student findById(Integer id);

    void update(Student student);

    void deleteByIds(List<Integer> ids);

    void updateViolation(Integer id, Integer score);
}
