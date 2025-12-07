package com.happy.controller;

import com.happy.pojo.Clazz;
import com.happy.pojo.EmpQueryParam;
import com.happy.pojo.PageResult;
import com.happy.pojo.Result;
import com.happy.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ClazzController {
    @Autowired
    private ClazzService clazzService;
    @GetMapping("/clazzs")
    public Result findClazz(EmpQueryParam empQueryParam){
        PageResult<Clazz> clazz = clazzService.findClazz(empQueryParam);
        return Result.success(clazz);
    }
    @DeleteMapping("/clazzs/{id}")
    public Result delete(@PathVariable Integer id){
        clazzService.deleteById(id);
        return Result.success();
    }
    @PostMapping("/clazzs")
    public Result add(@RequestBody Clazz clazz){
        clazzService.add(clazz);
        return Result.success();
    }

    @GetMapping("/clazzs/{id}")
    public Result findById(@PathVariable Integer id){
        Clazz clazz = clazzService.findById(id);
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        return Result.success(clazz);
    }
    @PutMapping("/clazzs")
    public Result update(@RequestBody Clazz clazz){
        clazzService.update(clazz);
        return Result.success();
    }

    @GetMapping("/clazzs/list")
    public Result findClazzList(){
        List<Clazz> list = clazzService.findClazzList();
        return Result.success(list);
    }
}
