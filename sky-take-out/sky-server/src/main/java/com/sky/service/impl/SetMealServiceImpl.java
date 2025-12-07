package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealMapper setMealMapper;

    @Autowired
    private SetMealDishMapper setMealDishMapper;

    @Autowired
    private DishMapper dishMapper;

    @Override
    public PageResult findSetMealByPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        Long total = setMealMapper.count();
        setmealPageQueryDTO.setPage((setmealPageQueryDTO.getPage() - 1) * setmealPageQueryDTO.getPageSize());
        List<Setmeal> records = setMealMapper.findpageQuery(setmealPageQueryDTO);
        return new PageResult(total, records);
    }

    @Override
    public void add(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmeal.setStatus(StatusConstant.DISABLE);

        setMealMapper.addSetMeal(setmeal);
        Long id = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && setmealDishes.size() != 0) {
            for (SetmealDish setmealDish : setmealDishes) {
                setmealDish.setSetmealId(id);
            }
            setMealDishMapper.addDish(setmealDishes);
        }
    }

    @Override
    public void delete(List<Long> ids) {
        List<Long> list = new ArrayList<>();
        for (Long id : ids) {
            Setmeal byId = setMealMapper.findById(id);
            if (byId.getStatus() == StatusConstant.DISABLE) {
                list.add(id);
            }
        }
        setMealMapper.deleteDish(list);
        for (Long l : list) {
            List<SetmealDish> setmealDishList = setMealDishMapper.getById(l);
            if(setmealDishList != null && setmealDishList.size() != 0){
                setMealDishMapper.deleteById(l);
            }
        }
    }

    @Override
    public SetmealVO getById(Long id) {
        Setmeal setmeal = setMealMapper.findById(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setMealDishMapper.getById(id));
        return setmealVO;
    }

    @Override
    public void controlSetMeal(Integer status, Long id) {
        if (status == StatusConstant.ENABLE) {
            List<SetmealDish> setmealDishList = setMealDishMapper.getById(id);
            List<Dish> dishList = new ArrayList<>();
            for (SetmealDish setmealDish : setmealDishList) {
                Dish dish = dishMapper.findById(setmealDish.getDishId());
                dishList.add(dish);
            }
            for (Dish dish : dishList) {
                if (dish.getStatus() == StatusConstant.DISABLE) {
                    throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                }
            }
            Setmeal setmeal = setMealMapper.findById(id);
            setmeal.setStatus(status);
            setMealMapper.controlSetMeal(setmeal);
        }else {
            Setmeal setmeal = setMealMapper.findById(id);
            setmeal.setStatus(status);
            setMealMapper.controlSetMeal(setmeal);
        }
    }

    @Override
    public List<Setmeal> findByCtg(Long categoryId) {
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);
        List<Setmeal> list = setMealMapper.findByCtg(setmeal);
        return list;
    }

    @Override
    public List<DishItemVO> getDishItemVO(Long id) {
        List<DishItemVO> list = new ArrayList<>();
        List<SetmealDish> setmealDishList = setMealDishMapper.getById(id);
        for (SetmealDish setmealDish : setmealDishList) {
            DishItemVO dishItemVO= new DishItemVO();
            dishItemVO.setName(setmealDish.getName());
            dishItemVO.setCopies(setmealDish.getCopies());

            Dish dish = dishMapper.findById(setmealDish.getDishId());
            dishItemVO.setImage(dish.getImage());
            dishItemVO.setDescription(dish.getDescription());
            list.add(dishItemVO);
        }
        return list;
    }

    @Override
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setMealMapper.updateSetMeal(setmeal);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && setmealDishes.size() != 0) {
            for (SetmealDish setmealDish : setmealDishes) {
                setmealDish.setSetmealId(setmeal.getId());
                setMealDishMapper.deleteById(setmealDish.getSetmealId());
            }
            setMealDishMapper.addDish(setmealDishes);
        }
    }

}
