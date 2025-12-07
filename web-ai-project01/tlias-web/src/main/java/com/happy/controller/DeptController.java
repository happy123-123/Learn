package com.happy.controller;

import com.happy.anno.Annotation;
import com.happy.pojo.Dept;
import com.happy.pojo.Result;
import com.happy.service.DeptService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping("/depts")
    public Result search() {
        System.out.println("查询全部数据");
        List<Dept> service = deptService.findAll();
        return Result.success(service);
    }

    /**
     * 删除数据
     * 通过原始的 HttpServletRequest对象获取参数
     */
//    @DeleteMapping("/depts")
//    public Result delete(HttpServletRequest request){
//        String s = request.getParameter("id");
//        deptService.deleteById(Integer.parseInt(s));
//        System.out.println("根据id:"+s+"删除数据");
//        return Result.success();
//    }

    /**
     * 删除数据
     * 通过@RequestParam注解获取参数 其中required属性表示是否传递参数,默认为true,表示必须传递参数,false,表示可以不传递参数
     */
    @Annotation
    @DeleteMapping("/depts")
    public Result delete(@RequestParam("id") Integer id){
        deptService.deleteById(id);
        System.out.println("根据id:"+id+"删除数据");
        return Result.success();
    }

    /**
     * 新增数据
     * 通过@RequestBody注解获取参数
     */
    @Annotation
    @PostMapping("/depts")
    public Result add(@RequestBody Dept dept){
        deptService.insert(dept);
        return Result.success();
    }

    @GetMapping("/depts/{id}")
    public Result getInfo(@PathVariable("id") Integer id){
        Dept dept = deptService.findtest(id);
        return Result.success(dept);
    }

    @Annotation
    @PutMapping("/depts")
    public Result move(@RequestBody Dept dept){
        deptService.update(dept);
        return Result.success();
    }
}
