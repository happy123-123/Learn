package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/admin/category")
    public Result add(@RequestBody CategoryDTO categoryDTO){
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }

    @GetMapping("/admin/category/page")
    public Result find(CategoryPageQueryDTO categoryPageQueryDTO){
        PageResult page = categoryService.findPage(categoryPageQueryDTO);
        return Result.success(page);
    }

    @PutMapping("/admin/category")
    public Result update(@RequestBody CategoryDTO categoryDTO){
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }

    @PostMapping("/admin/category/status/{status}")
    public Result control(@PathVariable Integer status,Long id){
        categoryService.controlCtg(status,id);
        return Result.success();
    }

    @DeleteMapping("/admin/category")
    public Result delete(@RequestParam Long id){
        categoryService.delete(id);
        return Result.success();
    }

    @GetMapping("/admin/category/list")
    public Result findByType(Integer type){
        List<Category> listType = categoryService.findByType(type);
        return Result.success(listType);
    }
}
