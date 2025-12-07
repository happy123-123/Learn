package com.happy.service;

import com.happy.pojo.Gender;
import com.happy.pojo.JobOption;
import com.happy.pojo.studentCount;
import com.happy.pojo.studentDegree;

import java.util.List;

public interface ReportService {
    JobOption findJobOption();
//    List<JobOption> findJobOption();

    List<Gender> findGender();

    List<studentDegree> findStudentDegree();

    studentCount findStudentCount();
}
