package com.sky.mapper;

import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.DailyOrder;
import com.sky.entity.DailyTurnover;
import com.sky.entity.DailyValidOrder;
import com.sky.entity.Orders;
import com.sky.vo.OrderReportVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    void insert(Orders orders);

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    Orders getById(Long id);

    Long count(Long userId);

    List<Orders> getByPage(OrdersPageQueryDTO ordersPageQueryDTO);


    Long countAll();

    Integer findByStatus(Integer status);

    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> timeOutOrders(Integer status, LocalDateTime orderTime);

    @Select("select * from orders where status = #{status} and estimated_delivery_time < #{estimatedDeliveryTime}")
    List<Orders> alwaysDeliveryOrders(Integer status, LocalDateTime estimatedDeliveryTime);

    List<DailyTurnover> getSum(LocalDateTime begin, LocalDateTime end, Integer status);

    List<DailyOrder> getOrders(LocalDateTime begin, LocalDateTime end);

    List<DailyValidOrder> getValidOrders(LocalDateTime begin, LocalDateTime end, Integer status);

    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);

    Integer getOverViewOrders(LocalDateTime begin, LocalDateTime end, Integer status);
}
