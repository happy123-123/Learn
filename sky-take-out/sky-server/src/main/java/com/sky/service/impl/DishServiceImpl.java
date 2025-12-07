package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishFlavorsMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SetMealMapper setMealMapper;

    @Autowired
    private DishFlavorsMapper dishFlavorsMapper;

    @Override
    public void addDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setStatus(StatusConstant.DISABLE);
        dishMapper.addDish(dish);

        Long dishId = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null || flavors.size() != 0) {
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishId));
            dishFlavorsMapper.addDishFlavor(flavors);
        }

    }

    @Override
    public PageResult findPage(DishPageQueryDTO dishPageQueryDTO) {
        Long total = dishMapper.count();
        dishPageQueryDTO.setPage((dishPageQueryDTO.getPage() - 1) * dishPageQueryDTO.getPageSize());
        List<Dish> records = dishMapper.findByPage(dishPageQueryDTO);
        return new PageResult(total, records);
    }

    @Override
    public void deleteDish(List<Long> ids) {
        List<Long> list = new ArrayList<>();
        for (Long id : ids) {
            Integer status = dishMapper.findById(id).getStatus();
            if (status == StatusConstant.DISABLE) {
                Long l = setMealMapper.findByDishId(id);
                if (l <= 0) {
                    list.add(id);
                } else throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
            } else throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        }
        if (list.size() != 0) {
            dishMapper.deleteAll(list);
            dishFlavorsMapper.deleteAll(list);
        }
    }

    @Override
    public void controlDish(Integer status, Long id) {
        Dish d=new Dish();
        d.setStatus(status);
        d.setId(id);
        dishMapper.controlAll(d);
        if (status == StatusConstant.DISABLE) {
            if (setMealMapper.findByDishId(id) != 0) {
                Dish dish = dishMapper.findById(id);
                setMealMapper.control(dish);
            }
        }
    }

    @Override
    public List<DishVO> findList(Long categoryId) {
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);
        List<Dish> list = dishMapper.findList(dish);
        List<DishVO> dishVOList = new ArrayList<>();
        for (Dish d : list) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d, dishVO);
            dishVO.setFlavors(dishFlavorsMapper.findAllById(d.getId()));
            dishVOList.add(dishVO);
        }
        return dishVOList;
    }

    @Override
    public DishVO findById(Long id) {
        DishVO dishVO = new DishVO();
        Dish dish = dishMapper.findById(id);
        BeanUtils.copyProperties(dish, dishVO);
        List<DishFlavor> flavors = dishFlavorsMapper.findAllById(id);
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    @Override
    public void updateDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.updateDish(dish);
        if (dishDTO.getFlavors() != null || dishDTO.getFlavors().size() != 0) {
            Long dishId = dishDTO.getId();
            dishFlavorsMapper.deleteByDishId(dishId);
            dishDTO.getFlavors().forEach(dishFlavor -> dishFlavor.setDishId(dishId));
            dishFlavorsMapper.addDishFlavor(dishDTO.getFlavors());
        }
    }
}
