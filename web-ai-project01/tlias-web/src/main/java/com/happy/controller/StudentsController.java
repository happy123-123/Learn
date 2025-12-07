package com.happy.controller;

import com.happy.pojo.EmpQueryParam;
import com.happy.pojo.PageResult;
import com.happy.pojo.Result;
import com.happy.pojo.Student;
import com.happy.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentsController {
    @Autowired
    private StudentsService studentsService;
    @GetMapping("/students")
    public Result findAll(EmpQueryParam empQueryParam){
        PageResult<Student> student = studentsService.findAll(empQueryParam);
        return Result.success(student);
    }

    @PostMapping("/students")
    public Result add(@RequestBody Student student){
        studentsService.insert(student);
        return Result.success();
    }

    @GetMapping("/students/{id}")
    public Result findById(@PathVariable Integer id){
        Student student = studentsService.findById(id);
        return Result.success(student);
    }

    @PutMapping("/students")
    public Result update(@RequestBody Student student){
        studentsService.update(student);
        return Result.success();
    }

    @DeleteMapping("/students/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        studentsService.deleteByIds(ids);
        return Result.success();
    }

    @PutMapping("/students/violation/{id}/{score}")
    public Result updateViolation(@PathVariable Integer id,@PathVariable Integer score){
        studentsService.updateViolation(id,score);
        return Result.success();
    }
}
