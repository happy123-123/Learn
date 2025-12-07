package com.sky.controller.admin;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/admin/order/conditionSearch")
    public Result conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageResult pageResult = orderService.adminSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/admin/order/statistics")
    public Result statistics() {
        OrderStatisticsVO statistics = orderService.getStatistics();
        return Result.success(statistics);
    }

    @GetMapping("/admin/order/details/{id}")
    public Result getOrderDetail(@PathVariable Long id) {
        OrderVO orderDetail = orderService.getOrderDetailById(id);
        return Result.success(orderDetail);
    }

    @PutMapping("/admin/order/confirm")
    public Result confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {
        orderService.confirm(ordersConfirmDTO);
        return Result.success();
    }

    @PutMapping("/admin/order/rejection")
    public Result rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) {
        orderService.rejection(ordersRejectionDTO);
        return Result.success();
    }

    @PutMapping("/admin/order/cancel")
    public Result cancel(@RequestBody OrdersCancelDTO ordersCancelDTO) {
        orderService.adminCancel(ordersCancelDTO);
        return Result.success();
    }

    @PutMapping("/admin/order/delivery/{id}")
    public Result delivery(@PathVariable Long id){
        orderService.delivery(id);
        return Result.success();
    }

    @PutMapping("/admin/order/complete/{id}")
    public Result complete(@PathVariable Long id){
        orderService.complete(id);
        return Result.success();
    }
}
