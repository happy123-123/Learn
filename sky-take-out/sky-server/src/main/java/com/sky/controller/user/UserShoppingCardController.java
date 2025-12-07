package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserShoppingCardController {
    @Autowired
    private ShoppingCardService userShoppingCardService;

    @GetMapping("/user/shoppingCart/list")
    public Result getShoppingCart(){
        List<ShoppingCart> SpC = userShoppingCardService.getSpC();
        return Result.success(SpC);
    }

    @PostMapping("/user/shoppingCart/add")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        userShoppingCardService.addSpc(shoppingCartDTO);
        return Result.success();
    }

    @DeleteMapping("/user/shoppingCart/clean")
    public Result delete(){
        userShoppingCardService.deleteSpC();
        return Result.success();
    }

    @PostMapping("/user/shoppingCart/sub")
    public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO){
        userShoppingCardService.subSpc(shoppingCartDTO);
        return Result.success();
    }
}
