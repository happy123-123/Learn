package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.mapper.ShoppingCardMapper;
import com.sky.service.ShoppingCardService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCardServiceImpl implements ShoppingCardService {
    @Autowired
    private ShoppingCardMapper userShoppingCardMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetMealMapper setMealMapper;

    @Override
    public void addSpc(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        ShoppingCart spC = userShoppingCardMapper.getSpC(shoppingCart);

        // 判断当前添加的购物车数据是否在购物车中
        if (spC != null) {
            // 在:增加数量
            spC.setNumber(spC.getNumber() + 1);
            userShoppingCardMapper.updateSpcNum(spC);
        } else {
            // 不在:添加数据
            if (shoppingCart.getDishId() != null) {
                // 添加的是菜品
                Dish dish = dishMapper.findById(shoppingCart.getDishId());
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            } else {
                // 添加的是套餐
                Setmeal setmeal = setMealMapper.findById(shoppingCart.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            userShoppingCardMapper.addSpc(shoppingCart);
        }
    }

    @Override
    public List<ShoppingCart> getSpC() {
        List<ShoppingCart> list = userShoppingCardMapper.getShoppingCard(BaseContext.getCurrentId());
        return list;
    }

    @Override
    public void deleteSpC() {
        userShoppingCardMapper.deleteAll(BaseContext.getCurrentId());
    }

    @Override
    public void subSpc(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        ShoppingCart spC = userShoppingCardMapper.getSpC(shoppingCart);

        if (spC != null){
            if (spC.getNumber() == 1) {
                userShoppingCardMapper.deleteById(spC.getId());
            }else {
                spC.setNumber(spC.getNumber() - 1);
                userShoppingCardMapper.updateSpcNum(spC);
            }
        }
    }
}
