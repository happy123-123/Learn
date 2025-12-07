package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCardMapper {
    ShoppingCart getSpC(ShoppingCart shoppingCart);

    void updateSpcNum(ShoppingCart shoppingCart);

    void addSpc(ShoppingCart shoppingCart);

    List<ShoppingCart> getShoppingCard(Long userId);

    void deleteAll(Long userId);

    void deleteById(Long id);

    void insertList(List<ShoppingCart> shoppingCards);
}
