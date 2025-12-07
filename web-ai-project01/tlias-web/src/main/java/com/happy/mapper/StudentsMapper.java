package com.happy.mapper;

import com.happy.pojo.EmpQueryParam;
import com.happy.pojo.Student;
import com.happy.pojo.studentDegree;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentsMapper {
    Long count();

    List<Student> findAllStudents(EmpQueryParam empQueryParam);

    void insertStudents(Student student);

    Student findStudentsById(Integer id);

    void updateStudents(Student student);

    void deleteStudents(List<Integer> ids);

    void updateViolationScore(Integer id, Integer score);

    List<studentDegree> findStudentDegree();

    List<Map<String, Object>> findStudentCount();
}
