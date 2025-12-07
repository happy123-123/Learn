package com.happy.controller;

import com.happy.mapper.EmpMapper;
import com.happy.pojo.*;
import com.happy.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping("/emps")
    public Result divideSearch(EmpQueryParam empQueryParam) {
        PageResult<Emp> pageResult = empService.divideFind(empQueryParam);
        return Result.success(pageResult);
    }

    @PostMapping("/emps")
    public Result add(@RequestBody Emp emp) {
        empService.insertEmp(emp);
        return Result.success();
    }
    @DeleteMapping("/emps")
    public Result delete(@RequestParam List<Integer> ids){
        empService.deleteByIds(ids);
        return Result.success();
    }

    @GetMapping("/emps/{id}")
    public Result getInfo(@PathVariable Integer id){
        Emp emp = empService.findBack(id);
        return Result.success(emp);
    }
    @PutMapping("/emps")
    public Result update(@RequestBody Emp emp){
        empService.update(emp);
        return Result.success();
    }

    @GetMapping("/emps/list")
    public Result findEmpList(){
        List<Emp> list = empService.findEmpList();
        return Result.success(list);
    }
}
