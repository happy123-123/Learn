package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.*;
import com.sky.entity.*;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.OrderBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.*;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.utils.HttpClientUtil;
import com.sky.utils.WeChatPayUtil;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import com.sky.websocket.WebSocketServer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Autowired
    private ShoppingCardMapper shoppingCardMapper;

    @Autowired
    private WeChatPayUtil weChatPayUtil;

    @Autowired
    private UserMapper userMapper;

    @Value("${sky.shop.address}")
    private String shopAddress;

    @Value("${sky.baidu.ak}")
    private String ak;

    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 用户下单
     *
     * @param ordersSubmitDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO) {
        //处理各种业务异常（地址簿为空、购物车为空）
        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        if (addressBook == null) {
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        checkOutOfRange(addressBook.getCityName()+addressBook.getDistrictName()+addressBook.getDetail());
        List<ShoppingCart> shoppingCard = shoppingCardMapper.getShoppingCard(BaseContext.getCurrentId());
        if (shoppingCard == null && shoppingCard.size() == 0) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }
        //向订单表中插入1条数据
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO, orders);
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());
        orders.setUserId(BaseContext.getCurrentId());
        orders.setAddress(addressBook.getDetail());
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setPayStatus(Orders.UN_PAID);
        orders.setOrderTime(LocalDateTime.now());
        orders.setNumber(UUID.randomUUID().toString());

        orderMapper.insert(orders);

        //插入订单详情
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (ShoppingCart shoppingCart : shoppingCard) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(shoppingCart, orderDetail);
            orderDetail.setOrderId(orders.getId());
            orderDetails.add(orderDetail);
        }
        orderDetailMapper.insertDetails(orderDetails);

        //清空购物车
        shoppingCardMapper.deleteAll(BaseContext.getCurrentId());

        //返回VO
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(orders.getId())
                .orderNumber(orders.getNumber())
                .orderAmount(orders.getAmount())
                .orderTime(orders.getOrderTime())
                .build();
        return orderSubmitVO;
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
//        // 当前登录用户id
//        Long userId = BaseContext.getCurrentId();
//        User user = userMapper.getById(userId);
//
//        //调用微信支付接口，生成预支付交易单
//        JSONObject jsonObject = weChatPayUtil.pay(
//                ordersPaymentDTO.getOrderNumber(), //商户订单号
//                new BigDecimal(0.01), //支付金额，单位 元
//                "苍穹外卖订单", //商品描述
//                user.getOpenid() //微信用户的openid
//        );
//
//        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
//            throw new OrderBusinessException("该订单已支付");
//        }
//
//        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
//        vo.setPackageStr(jsonObject.getString("package"));

        paySuccess(ordersPaymentDTO.getOrderNumber());

        return new OrderPaymentVO();
    }

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo
     */
    public void paySuccess(String outTradeNo) {

        // 根据订单号查询订单
        Orders ordersDB = orderMapper.getByNumber(outTradeNo);

        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();

        orderMapper.update(orders);

        Map map=new HashMap();
        map.put("type",1);
        map.put("orderId",ordersDB.getId());
        map.put("content","订单号"+outTradeNo);

        String json = JSONObject.toJSONString(map);
        webSocketServer.sendToAllClient(json);
    }

    /**
     * 根据订单ID获取订单详情
     * @param id 订单ID
     * @return 订单详情VO对象，包含订单基本信息和订单明细列表
     */
    @Override
    public OrderVO getOrderDetailById(Long id) {
        // 查询订单基本信息
        Orders order = orderMapper.getById(id);

        // 将订单信息复制到VO对象中
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);

        // 查询订单明细并设置到VO对象中
        List<OrderDetail> orderDetail = orderDetailMapper.getOrderDetailListByOrderId(order.getId());
        orderVO.setOrderDetailList(orderDetail);

        return orderVO;
    }

    /**
     * 分页查询历史订单信息
     * @param page 页码
     * @param pageSize 每页大小
     * @param status 订单状态
     * @return 分页结果，包含订单总数和当前页的订单记录
     */
    @Override
    public PageResult devideHistoryOrders(int page, int pageSize, Integer status) {
        // 查询用户订单总数量
        Long total = orderMapper.count(BaseContext.getCurrentId());
        List<OrderVO> records = new ArrayList<>();

        // 构造分页查询条件
        OrdersPageQueryDTO ordersPageQueryDTO = new OrdersPageQueryDTO();
        ordersPageQueryDTO.setStatus(status);
        ordersPageQueryDTO.setPageSize(pageSize);
        ordersPageQueryDTO.setUserId(BaseContext.getCurrentId());
        ordersPageQueryDTO.setPage((page - 1) * ordersPageQueryDTO.getPageSize());

        // 执行分页查询获取订单列表
        List<Orders> orders = orderMapper.getByPage(ordersPageQueryDTO);

        // 如果存在订单数据，则封装订单详情信息
        if (total > 0 && orders != null) {
            for (Orders order : orders) {
                // 查询订单详情列表
                List<OrderDetail> list = orderDetailMapper.getOrderDetailListByOrderId(order.getId());
                // 将订单信息复制到VO对象并设置详情列表
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(order, orderVO);
                orderVO.setOrderDetailList(list);
                records.add(orderVO);
            }
        }
        // 返回分页结果
        return new PageResult(total, records);
    }

    /**
     * 取消订单
     * @param id 订单ID
     */
    @Override
    public void cancel(Long id) {

        Orders ordersOB = orderMapper.getById(id);
        Orders orders = new Orders();
        //判断订单是否存在
        if (ordersOB == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        // 判断订单状态(大于2则不可直接取消订单 需与商家联系)
        if (ordersOB.getStatus() > 2) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        //判断订单是否支付
        if (ordersOB.getPayStatus() == Orders.PAID || ordersOB.getStatus() == Orders.TO_BE_CONFIRMED) {
//            //调用微信支付退款接口
//            weChatPayUtil.refund(
//                    orders.getNumber(), //商户订单号
//                    orders.getNumber(), //商户退款单号
//                    new BigDecimal(0.01),//退款金额，单位 元
//                    new BigDecimal(0.01));//原订单金额
            orders.setPayStatus(Orders.REFUND);
        }
        // 更新订单状态为已取消
        orders.setId(id);
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelReason("用户取消");
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.update(orders);
    }

    /**
     * 重复订单功能，将指定订单的所有详情复制到购物车中
     * @param id 订单ID，用于查询订单详情
     */
    @Override
    public void repetition(Long id) {
        // 根据订单ID获取订单详情列表
        List<OrderDetail> orderDetails = orderDetailMapper.getOrderDetailListByOrderId(id);

        // 构建购物车列表，将订单详情转换为购物车项
        List<ShoppingCart> shoppingCards = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            ShoppingCart shoppingCart = ShoppingCart.builder()
                    .name(orderDetail.getName())
                    .image(orderDetail.getImage())
                    .userId(BaseContext.getCurrentId())
                    .dishId(orderDetail.getDishId())
                    .setmealId(orderDetail.getSetmealId())
                    .dishFlavor(orderDetail.getDishFlavor())
                    .number(orderDetail.getNumber())
                    .amount(orderDetail.getAmount())
                    .createTime(LocalDateTime.now())
                    .build();

            shoppingCards.add(shoppingCart);
        }
        // 批量插入购物车数据
        shoppingCardMapper.insertList(shoppingCards);
    }

    /**
     * 管理员搜索订单列表
     *
     * @param ordersPageQueryDTO 订单分页查询条件对象，包含页码、页面大小等查询参数
     * @return PageResult 分页结果对象，包含总记录数和当前页的订单数据列表
     */
    @Override
    public PageResult adminSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        // 查询订单总数量
        Long total = orderMapper.countAll();
        List<OrderVO> records = new ArrayList<>();

        // 计算分页起始位置
        ordersPageQueryDTO.setPage((ordersPageQueryDTO.getPage() - 1) * ordersPageQueryDTO.getPageSize());
        // 分页查询订单列表
        List<Orders> orders = orderMapper.getByPage(ordersPageQueryDTO);
        if (total > 0 && orders != null) {
            // 遍历订单列表，转换为VO对象并填充订单菜品信息
            for (Orders order : orders) {
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(order, orderVO);

                // 查询订单详情列表
                List<OrderDetail> list = orderDetailMapper.getOrderDetailListByOrderId(order.getId());
                List<String> orderDishesList = new ArrayList<>();
                // 构造订单菜品字符串列表
                for (OrderDetail detail : list) {
                    String orderDish = detail.getName() + "*" + detail.getNumber();
                    orderDishesList.add(orderDish);
                }
                // 将List转换为String，使用逗号分隔
                String orderDishes = String.join(",", orderDishesList);
                orderVO.setOrderDishes(orderDishes);

                records.add(orderVO);
            }
        }
        // 返回分页结果
        return new PageResult(total, records);
    }

    /**
     * 获取订单统计信息
     * <p>
     * 该方法用于查询不同状态下的订单数量统计，包括待确认、已确认和配送中三种状态的订单
     *
     * @return OrderStatisticsVO 订单统计信息对象，包含各种状态订单的数量
     */
    @Override
    public OrderStatisticsVO getStatistics() {
        OrderStatisticsVO orderStatisticsVO = new OrderStatisticsVO();
        // 查询待确认订单并设置到统计对象中
        orderStatisticsVO.setToBeConfirmed(orderMapper.findByStatus(Orders.TO_BE_CONFIRMED));
        // 查询已确认订单并设置到统计对象中
        orderStatisticsVO.setConfirmed(orderMapper.findByStatus(Orders.CONFIRMED));
        // 查询配送中订单并设置到统计对象中
        orderStatisticsVO.setDeliveryInProgress(orderMapper.findByStatus(Orders.DELIVERY_IN_PROGRESS));

        return orderStatisticsVO;
    }

    /**
     * 确认订单方法
     * 将指定订单的状态更新为已确认状态
     *
     * @param ordersConfirmDTO 订单确认数据传输对象，包含要确认的订单ID
     */
    @Override
    public void confirm(OrdersConfirmDTO ordersConfirmDTO) {
        // 创建订单对象并设置状态为已确认
        Orders orders = new Orders();
        orders.setId(ordersConfirmDTO.getId());
        orders.setStatus(Orders.CONFIRMED);

        // 更新订单状态到数据库
        orderMapper.update(orders);
    }

    /**
     * 拒绝订单处理方法
     *
     * @param ordersRejectionDTO 订单拒绝信息数据传输对象，包含订单ID和拒绝原因
     */
    @Override
    public void rejection(OrdersRejectionDTO ordersRejectionDTO) {
        // 根据订单ID查询订单信息
        Orders ordersOb = orderMapper.getById(ordersRejectionDTO.getId());

        // 验证订单是否存在
        if (ordersOb == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }

        // 验证订单状态是否为待接单状态（状态码2）
        if (ordersOb.getStatus() != 2) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        // 构建订单更新信息
        Orders orders = new Orders();
        // 如果订单已支付，则设置支付状态为退款状态
        if (ordersOb.getPayStatus() == Orders.PAID) {
            orders.setPayStatus(Orders.REFUND);
        }
        orders.setId(ordersRejectionDTO.getId());
        orders.setStatus(Orders.CANCELLED);
        orders.setRejectionReason(ordersRejectionDTO.getRejectionReason());
        orders.setCancelTime(LocalDateTime.now());

        // 更新订单信息
        orderMapper.update(orders);
    }

    /**
     * 管理员取消订单
     * @param ordersCancelDTO 订单取消数据传输对象，包含订单ID和取消原因
     */
    @Override
    public void adminCancel(OrdersCancelDTO ordersCancelDTO) {
        // 根据id查询订单
        Orders ordersDB = orderMapper.getById(ordersCancelDTO.getId());
        if (ordersDB == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }

        Orders orders = new Orders();
        // 如果订单已支付，则设置退款状态
        if (ordersDB.getPayStatus() == Orders.PAID) {
            orders.setPayStatus(Orders.REFUND);
        }
        // 根据订单id更新订单状态、取消原因、取消时间
        orders.setId(ordersCancelDTO.getId());
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelReason(ordersCancelDTO.getCancelReason());
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.update(orders);
    }

    /**
     * 订单配送处理方法
     * 将指定订单状态更新为配送中状态
     *
     * @param id 订单ID，用于标识需要进行配送操作的订单
     */
    @Override
    public void delivery(Long id) {
        // 查询订单信息
        Orders ordersOb = orderMapper.getById(id);
        if (ordersOb == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        // 验证订单状态是否为已确认状态(代派送状态)
        if (ordersOb.getStatus() != Orders.CONFIRMED) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        // 更新订单状态为配送中
        Orders orders = new Orders();
        orders.setId(id);
        orders.setStatus(Orders.DELIVERY_IN_PROGRESS);
        orderMapper.update(orders);
    }

    @Override
    public void complete(Long id) {
        // 查询订单信息
        Orders ordersOb = orderMapper.getById(id);
        if (ordersOb == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        // 验证订单状态是否为派送中状态
        if (ordersOb.getStatus() != Orders.DELIVERY_IN_PROGRESS) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        // 更新订单状态为已完成
        Orders orders = new Orders();
        orders.setId(id);
        orders.setStatus(Orders.COMPLETED);
        orders.setDeliveryTime(LocalDateTime.now());
        orderMapper.update(orders);
    }

    @Override
    public void reminder(Long id) {
        Orders orders = orderMapper.getById(id);
        if (orders == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        Map map = new HashMap();
        map.put("type",2);
        map.put("orderId",id);
        map.put("content","订单号："+orders.getNumber());
        webSocketServer.sendToAllClient(JSON.toJSONString(map));
    }

    /**
     * 检查客户的收货地址是否超出配送范围
     * @param address
     */
    private void checkOutOfRange(String address) {
        Map map = new HashMap();
        map.put("address",shopAddress);
        map.put("output","json");
        map.put("ak",ak);

        //获取店铺的经纬度坐标
        String shopCoordinate = HttpClientUtil.doGet("https://api.map.baidu.com/geocoding/v3", map);

        JSONObject jsonObject = JSON.parseObject(shopCoordinate);
        if(!jsonObject.getString("status").equals("0")){
            throw new OrderBusinessException("店铺地址解析失败");
        }

        //数据解析
        JSONObject location = jsonObject.getJSONObject("result").getJSONObject("location");
        String lat = location.getString("lat");
        String lng = location.getString("lng");
        //店铺经纬度坐标
        String shopLngLat = lat + "," + lng;

        map.put("address",address);
        //获取用户收货地址的经纬度坐标
        String userCoordinate = HttpClientUtil.doGet("https://api.map.baidu.com/geocoding/v3", map);

        jsonObject = JSON.parseObject(userCoordinate);
        if(!jsonObject.getString("status").equals("0")){
            throw new OrderBusinessException("收货地址解析失败");
        }

        //数据解析
        location = jsonObject.getJSONObject("result").getJSONObject("location");
        lat = location.getString("lat");
        lng = location.getString("lng");
        //用户收货地址经纬度坐标
        String userLngLat = lat + "," + lng;

        map.put("origin",shopLngLat);
        map.put("destination",userLngLat);
        map.put("steps_info","0");

        //路线规划
        String json = HttpClientUtil.doGet("https://api.map.baidu.com/directionlite/v1/driving", map);

        jsonObject = JSON.parseObject(json);
        if(!jsonObject.getString("status").equals("0")){
            throw new OrderBusinessException("配送路线规划失败");
        }

        //数据解析
        JSONObject result = jsonObject.getJSONObject("result");
        JSONArray jsonArray = (JSONArray) result.get("routes");
        Integer distance = (Integer) ((JSONObject) jsonArray.get(0)).get("distance");

        if(distance > 5000){
            //配送距离超过5000米
            throw new OrderBusinessException("超出配送范围");
        }
    }
}
