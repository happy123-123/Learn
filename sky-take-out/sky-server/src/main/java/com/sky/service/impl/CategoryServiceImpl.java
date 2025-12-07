package com.sky.service.impl;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setStatus(1);
        categoryMapper.addAll(category);
    }

    @Override
    public PageResult findPage(CategoryPageQueryDTO categoryPageQueryDTO) {
        Long total = categoryMapper.count();
        categoryPageQueryDTO.setPage((categoryPageQueryDTO.getPage()-1)*categoryPageQueryDTO.getPageSize());
        List<Category> records = categoryMapper.findByPage(categoryPageQueryDTO);
        return new PageResult(total, records);
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        categoryMapper.updateAll(category);
    }

    @Override
    public void controlCtg(Integer status, Long id) {
        Category category = new Category();
        category.setStatus(status);
        category.setId(id);
        categoryMapper.controlAll(category);
    }

    @Override
    public void delete(Long id) {
        categoryMapper.deleteAll(id);
    }

    @Override
    public List<Category> findByType(Integer type) {
        List<Category> list = categoryMapper.findCtgByType(type);
        return list;
    }
}
