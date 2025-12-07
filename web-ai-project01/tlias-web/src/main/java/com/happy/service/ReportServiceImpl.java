package com.happy.service;

import com.happy.mapper.EmpMapper;
import com.happy.mapper.StudentsMapper;
import com.happy.pojo.Gender;
import com.happy.pojo.JobOption;
import com.happy.pojo.studentCount;
import com.happy.pojo.studentDegree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private StudentsMapper studentsMapper;
    @Override
    public JobOption findJobOption() {
        List<Map<String, Object>> list = empMapper.findJobOption();
        List<Object> pos = list.stream().map(map -> map.get("pos")).toList();
        List<Object> num = list.stream().map(map -> map.get("num")).toList();
        return new JobOption(pos,num);
    }
//    @Override
//    public List<JobOption> findJobOption() {
//        return empMapper.findJobOption();
//    }

    @Override
    public List<Gender> findGender() {
        return empMapper.findGender();
    }

    @Override
    public List<studentDegree> findStudentDegree() {
        return studentsMapper.findStudentDegree();
    }

    @Override
    public studentCount findStudentCount() {
        List<Map<String,Object>> count = studentsMapper.findStudentCount();
        List<Object> name = count.stream().map(map -> map.get("name")).toList();
        List<Object> value = count.stream().map(map -> map.get("value")).toList();
        return new studentCount(name,value);
    }
}
