package com.happy.service;

import com.happy.mapper.DeptMapper;
import com.happy.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class DeptServiceImpl implements DeptService{
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> findAll() {
        return deptMapper.find();
    }

    @Override
    public void deleteById(Integer id) {
        Integer i = deptMapper.linkDept(id);
        if (i>0){
            throw new RuntimeException("该部门下有员工,不能删除");
        }else {
            deptMapper.delete(id);
        }
    }

    @Override
    public void insert(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insertAll(dept);
    }

    @Override
    public Dept findtest(Integer id) {
        return deptMapper.find01(id);
    }

    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.updateAll(dept);
    }
}
