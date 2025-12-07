package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 定时任务处理超时未支付订单
     * <p>
     * 该方法通过定时调度，每分钟执行一次，查找并取消超过15分钟未支付的订单。
     * 订单状态将被设置为已取消，并记录取消原因和取消时间。
     */
    @Scheduled(cron = "0 * * * * ?")
    public void timeOutOrders() {
        // 计算15分钟前的时间点，用于筛选超时订单
        LocalDateTime time = LocalDateTime.now().minusMinutes(15);

        // 查询超过15分钟仍未支付的订单列表
        List<Orders> orders = orderMapper.timeOutOrders(Orders.PENDING_PAYMENT, time);
        // 判断是否有超时订单
        if (orders != null || orders.size() != 0) {
            // 批量更新超时订单状态
            for (Orders order : orders) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("支付超时，取消订单");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }
    }

    /**
     * 定时任务：自动完成配送中的订单
     * 每天凌晨1点执行，将超过1小时仍处于配送中的订单状态更新为已完成
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void alwaysDeliveryOrders() {
        // 获取1小时前的时间点，用于筛选超时的配送订单
        LocalDateTime time = LocalDateTime.now().minusHours(1);

        // 查询所有配送中且超过预计配送时间1个小时的订单
        List<Orders> orders = orderMapper.alwaysDeliveryOrders(Orders.DELIVERY_IN_PROGRESS, time);
        // 判断是否有超时配送（或配送到了 但状态没显示已完成）订单
        if (orders != null || orders.size() != 0) {
            // 批量更新符合条件的订单状态为已完成
            for (Orders order : orders) {
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            }
        }
    }
}
