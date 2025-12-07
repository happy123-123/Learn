package com.happy.service;

import com.happy.pojo.*;

import java.util.List;

public interface EmpService {
    PageResult<Emp> divideFind(EmpQueryParam empQueryParam);

    void insertEmp(Emp emp);

    void deleteByIds(List<Integer> ids);

    Emp findBack(Integer id);

    void update(Emp emp);

    List<Emp> findEmpList();

    LoginInfo loginEmp(Emp emp);
}
