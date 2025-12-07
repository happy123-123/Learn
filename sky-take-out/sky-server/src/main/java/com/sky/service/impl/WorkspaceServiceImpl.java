package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.entity.*;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetMealMapper setmealMapper;

    /**
     * 根据时间段统计营业数据
     * @param begin
     * @param end
     * @return
     */
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
        /**
         * 营业额：当日已完成订单的总金额
         * 有效订单：当日已完成订单的数量
         * 订单完成率：有效订单数 / 总订单数
         * 平均客单价：营业额 / 有效订单数
         * 新增用户：当日新增用户的数量
         */

        List<DailyOrder> orders = orderMapper.getOrders(begin, end);
        //查询总订单数
        Integer totalOrderCount = 0;
        if (orders != null || orders.size() != 0){
            for (DailyOrder order : orders) {
                totalOrderCount += order.getOrderCount();
            }
        }

        //营业额
        List<DailyTurnover> sum = orderMapper.getSum(begin, end, Orders.COMPLETED);
        Double turnover = 0.0;
        if (sum != null || sum.size() != 0){
            for (DailyTurnover dailyTurnover : sum) {
                turnover += dailyTurnover.getDailyAmount();
            }
        }

        //有效订单数
        List<DailyValidOrder> validOrders = orderMapper.getValidOrders(begin, end, Orders.COMPLETED);
        Integer validOrderCount = 0;
        if (validOrders != null || validOrders.size() != 0){
            for (DailyValidOrder dailyValidOrder : validOrders) {
                validOrderCount += dailyValidOrder.getValidOrderCount();
            }
        }

        Double unitPrice = 0.0;

        Double orderCompletionRate = 0.0;
        if(totalOrderCount != 0 && validOrderCount != 0){
            //订单完成率
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
            //平均客单价
            unitPrice = turnover / validOrderCount;
        }

        //新增用户数
        List<DailyUserStatistics> users = userMapper.getNewUsers(begin, end);
        Integer newUsers = 0;
        if (users != null || users.size() != 0){
            for (DailyUserStatistics user : users) {
                newUsers += user.getUserCount();
            }
        }

        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();
    }


    /**
     * 查询订单管理数据
     *
     * @return
     */
    public OrderOverViewVO getOrderOverView() {
        LocalDateTime begin = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);

        //待接单
        Integer waitingOrders = orderMapper.getOverViewOrders(begin,end,Orders.TO_BE_CONFIRMED);

        //待派送
        Integer deliveredOrders = orderMapper.getOverViewOrders(begin,end,Orders.CONFIRMED);

        //已完成
        Integer completedOrders = orderMapper.getOverViewOrders(begin,end,Orders.COMPLETED);

        //已取消
        Integer cancelledOrders = orderMapper.getOverViewOrders(begin,end,Orders.CANCELLED);

        //全部订单
        Integer allOrders = orderMapper.getOverViewOrders(begin,end,null);

        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders)
                .build();
    }

    /**
     * 查询菜品总览
     *
     * @return
     */
    public DishOverViewVO getDishOverView() {
        Integer sold = dishMapper.getOverViewDish(StatusConstant.ENABLE);

        Integer discontinued = dishMapper.getOverViewDish(StatusConstant.DISABLE);

        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    /**
     * 查询套餐总览
     *
     * @return
     */
    public SetmealOverViewVO getSetmealOverView() {
        Integer sold = setmealMapper.getOverViewSetmeal(StatusConstant.ENABLE);

        Integer discontinued = setmealMapper.getOverViewSetmeal(StatusConstant.DISABLE);

        return SetmealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }
}
