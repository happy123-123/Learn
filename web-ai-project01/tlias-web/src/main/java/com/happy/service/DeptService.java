package com.happy.service;

import com.happy.pojo.Dept;

import java.util.List;

public interface DeptService {

    List<Dept> findAll();

    void deleteById(Integer id);

    void insert(Dept dept);

    Dept findtest(Integer id);

    void update(Dept dept);
}
