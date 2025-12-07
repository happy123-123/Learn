package com.happy.service;

import com.happy.mapper.StudentsMapper;
import com.happy.pojo.EmpQueryParam;
import com.happy.pojo.PageResult;
import com.happy.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class StudentsServiceImpl implements StudentsService{
    @Autowired
    private StudentsMapper studentsMapper;

    @Override
    public PageResult<Student> findAll(EmpQueryParam empQueryParam) {
        Long total = studentsMapper.count();
        empQueryParam.setPage((empQueryParam.getPage() - 1) * empQueryParam.getPageSize());
        List<Student> rows = studentsMapper.findAllStudents(empQueryParam);
        return new PageResult<Student>(total, rows);
    }

    @Override
    public void insert(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        studentsMapper.insertStudents(student);
    }

    @Override
    public Student findById(Integer id) {
        Student student = studentsMapper.findStudentsById(id);
        return student;
    }

    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentsMapper.updateStudents(student);
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        studentsMapper.deleteStudents(ids);
    }

    @Override
    public void updateViolation(Integer id, Integer score) {
        studentsMapper.updateViolationScore(id, score);
    }
}
