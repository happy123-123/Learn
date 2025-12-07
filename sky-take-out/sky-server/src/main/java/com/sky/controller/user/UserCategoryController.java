package com.sky.controller.user;

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
public class UserCategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/user/category/list")
    public Result findByType(Integer type){
        List<Category> listType = categoryService.findByType(type);
        return Result.success(listType);
    }
}
