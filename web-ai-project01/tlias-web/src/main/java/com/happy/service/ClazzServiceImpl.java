package com.happy.service;

import com.happy.mapper.ClazzMapper;
import com.happy.pojo.Clazz;
import com.happy.pojo.EmpQueryParam;
import com.happy.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class ClazzServiceImpl implements ClazzService{
    @Autowired
    private ClazzMapper clazzMapper;
    @Override
    public PageResult<Clazz> findClazz(EmpQueryParam empQueryParam) {
        Long total = clazzMapper.findAll();
        empQueryParam.setPage((empQueryParam.getPage() - 1) * empQueryParam.getPageSize());
        List<Clazz> rows = clazzMapper.find(empQueryParam);
        return new PageResult<Clazz>(total, rows);
    }

    @Override
    public void deleteById(Integer id) {
        Integer i = clazzMapper.linkClazz(id);
        if (i>0){
            throw new RuntimeException("该班级下有关联学生,不能删除");
        }else {
            clazzMapper.deleteById(id);
        }
    }

    @Override
    public void add(Clazz clazz) {
        clazzMapper.add(clazz);
    }

    @Override
    public Clazz findById(Integer id) {
        Clazz clazz = clazzMapper.findById(id);
        return clazz;
    }

    @Override
    public void update(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.update(clazz);
    }

    @Override
    public List<Clazz> findClazzList() {
        List<Clazz> list = clazzMapper.findClazzList();
        return list;
    }
}
