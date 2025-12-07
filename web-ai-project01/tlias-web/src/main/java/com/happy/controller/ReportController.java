package com.happy.controller;

import com.happy.pojo.*;
import com.happy.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/report/empJobData")
    public Result findJobOption(){
        JobOption jobOption = reportService.findJobOption();
//        List<JobOption> jobOption = reportService.findJobOption();
        return Result.success(jobOption);
    }

    @GetMapping("/report/empGenderData")
    public Result findGender(){
        List<Gender> gender = reportService.findGender();
        return Result.success(gender);
    }

    @GetMapping("/report/studentDegreeData")
    public Result findStudentDegree(){
        List<studentDegree> studentDegree = reportService.findStudentDegree();
        return Result.success(studentDegree);
    }

    @GetMapping("/report/studentCountData")
    public Result findStudentCountData(){
        studentCount sc = reportService.findStudentCount();
        return Result.success(sc);
    }
}
