package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCardService {
    void addSpc(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> getSpC();

    void deleteSpC();

    void subSpc(ShoppingCartDTO shoppingCartDTO);
}
