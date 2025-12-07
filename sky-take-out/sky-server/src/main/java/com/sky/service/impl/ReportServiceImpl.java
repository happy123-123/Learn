package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.*;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WorkspaceService workspaceService;

    /**
     * 获取营业额统计报表数据
     *
     * @param begin 统计开始日期
     * @param end   统计结束日期
     * @return 营业额统计报表数据对象，包含日期列表和对应的营业额列表
     */
    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        // 生成日期列表
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);

        LocalDateTime beginTime = LocalDateTime.of(begin, LocalDateTime.MIN.toLocalTime());

        while (!begin.equals(end)) {
            begin = begin.plusDays(1);//日期计算，获得指定日期后1天的日期
            dateList.add(begin);
        }

        // 查询指定日期范围内的营业额数据
        List<Double> turnoverList = new ArrayList<>();
        LocalDateTime endTime = LocalDateTime.of(end, LocalDateTime.MAX.toLocalTime());
        List<DailyTurnover> sum = orderMapper.getSum(beginTime, endTime, Orders.COMPLETED);

        // 将查询结果转换为Map，便于按日期查找
        Map<LocalDate, Double> map = new HashMap<>();
        for (DailyTurnover dailyTurnover : sum) {
            map.put(dailyTurnover.getOrderDate(), dailyTurnover.getDailyAmount());
        }

        // 根据日期列表构建营业额列表，如果某天没有数据则填充0
        for (LocalDate date : dateList) {
            if (map.containsKey(date)) {
                turnoverList.add(map.get(date));
            } else {
                turnoverList.add(0.0);
            }
        }

        // 构造返回结果对象
        TurnoverReportVO turnoverReportVO = new TurnoverReportVO();
        turnoverReportVO.setDateList(StringUtils.join(dateList, ","));
        turnoverReportVO.setTurnoverList(StringUtils.join(turnoverList, ","));

        return turnoverReportVO;
    }

    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalDateTime.MIN.toLocalTime());
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        LocalDateTime endTime = LocalDateTime.of(end, LocalDateTime.MAX.toLocalTime());
        // 查询指定日期范围内的新增用户数
        List<Integer> newUserList = new ArrayList<>();

        List<DailyUserStatistics> newUsers = userMapper.getNewUsers(beginTime, endTime);

        Map<LocalDate, Integer> newUserMap = new HashMap<>();
        for (DailyUserStatistics newUser : newUsers) {
            newUserMap.put(newUser.getOrderDate(), newUser.getUserCount());
        }
        for (LocalDate date : dateList) {
            if (newUserMap.containsKey(date)) {
                newUserList.add(newUserMap.get(date));
            } else {
                newUserList.add(0);
            }
        }

        // 查询指定日期范围内的总用户数
        List<Integer> totalUserList = new ArrayList<>();

        List<DailyUserStatistics> totalUsers = userMapper.getNewUsers(null, endTime);

        Map<LocalDate, Integer> newTotalMap = new HashMap<>();
        for (DailyUserStatistics newUser : totalUsers) {
            newTotalMap.put(newUser.getOrderDate(), newUser.getUserCount());
        }

        Integer total = 0;
        for (LocalDate date : dateList) {
            if (newTotalMap.containsKey(date)) {
                total += newTotalMap.get(date);
                totalUserList.add(total);
            } else {
                totalUserList.add(total);
            }
        }

        UserReportVO userReportVO = new UserReportVO();
        userReportVO.setDateList(StringUtils.join(dateList, ","));
        userReportVO.setNewUserList(StringUtils.join(newUserList, ","));
        userReportVO.setTotalUserList(StringUtils.join(totalUserList, ","));

        return userReportVO;
    }

    @Override
    public OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalDateTime.MIN.toLocalTime());
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        LocalDateTime endTime = LocalDateTime.of(end, LocalDateTime.MAX.toLocalTime());

        // 查询指定日期范围内的总订单数列表
        List<Integer> orderList = new ArrayList<>();
        // 获取总订单数
        Integer totalOrderCount = 0;

        List<DailyOrder> orders = orderMapper.getOrders(beginTime, endTime);
        Map<LocalDate, Integer> orderMap = new HashMap<>();
        for (DailyOrder order : orders) {
            orderMap.put(order.getOrderDate(), order.getOrderCount());
        }

        for (LocalDate date : dateList) {
            if (orderMap.containsKey(date)) {
                totalOrderCount += orderMap.get(date);
                orderList.add(totalOrderCount);
            } else {
                orderList.add(totalOrderCount);
            }
        }

        // 查询指定日期范围内的有效订单数列表
        List<Integer> validOrderList = new ArrayList<>();
        // 获取有效订单数
        Integer validOrderCount = 0;

        List<DailyValidOrder> validOrders = orderMapper.getValidOrders(beginTime, endTime, Orders.COMPLETED);
        Map<LocalDate, Integer> validOrderMap = new HashMap<>();
        for (DailyValidOrder validOrder : validOrders) {
            validOrderMap.put(validOrder.getOrderDate(), validOrder.getValidOrderCount());
        }
        for (LocalDate date : dateList) {
            if (validOrderMap.containsKey(date)) {
                validOrderCount += validOrderMap.get(date);
                validOrderList.add(validOrderCount);
            } else {
                validOrderList.add(validOrderCount);
            }
        }

        OrderReportVO orderReportVO = new OrderReportVO();
        orderReportVO.setDateList(StringUtils.join(dateList, ","));
        orderReportVO.setOrderCountList(StringUtils.join(orderList, ","));
        orderReportVO.setValidOrderCountList(StringUtils.join(validOrderList, ","));
        orderReportVO.setTotalOrderCount(totalOrderCount);
        orderReportVO.setValidOrderCount(validOrderCount);
        orderReportVO.setOrderCompletionRate(validOrderCount * 1.0 / totalOrderCount);

        return orderReportVO;
    }

    @Override
    public SalesTop10ReportVO getTop10(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalDateTime.MIN.toLocalTime());
        LocalDateTime endTime = LocalDateTime.of(end, LocalDateTime.MAX.toLocalTime());
        List<GoodsSalesDTO> salesTop10 = orderMapper.getSalesTop10(beginTime, endTime);

        List<String> nameList = new ArrayList<>();
        List<Integer> numberList = new ArrayList<>();
        for (GoodsSalesDTO salesTop : salesTop10) {
            nameList.add(salesTop.getName());
            numberList.add(salesTop.getNumber());
        }
        SalesTop10ReportVO salesTop10ReportVO = new SalesTop10ReportVO();
        salesTop10ReportVO.setNameList(StringUtils.join(nameList, ","));
        salesTop10ReportVO.setNumberList(StringUtils.join(numberList, ","));

        return salesTop10ReportVO;
    }

    @Override
    public void export(HttpServletResponse response) {
        LocalDate begin = LocalDate.now().minusDays(30);
        LocalDate end = LocalDate.now().minusDays(1);

        //查询概览运营数据
        BusinessDataVO businessData = workspaceService.getBusinessData(LocalDateTime.of(begin, LocalDateTime.MIN.toLocalTime()), LocalDateTime.of(end, LocalDateTime.MAX.toLocalTime()));

        //读取excel模版
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");
        try {
            //通过poi 创建excel对象
            XSSFWorkbook excel = new XSSFWorkbook(in);

            XSSFSheet sheet = excel.getSheet("Sheet1");

            sheet.getRow(1).getCell(1).setCellValue("时间:" + begin + "至" + end);

            XSSFRow row = sheet.getRow(3);
            row.getCell(2).setCellValue(businessData.getTurnover());
            row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
            row.getCell(6).setCellValue(businessData.getNewUsers());

            row = sheet.getRow(4);
            row.getCell(2).setCellValue(businessData.getValidOrderCount());
            row.getCell(4).setCellValue(businessData.getUnitPrice());

            //获取并填充明细数据
            for (int i = 0; i < 30; i++) {
                LocalDate date = begin.plusDays(i);
                businessData = workspaceService.getBusinessData(LocalDateTime.of(date, LocalDateTime.MIN.toLocalTime()), LocalDateTime.of(date, LocalDateTime.MAX.toLocalTime()));
                row = sheet.getRow(i + 7);
                row.getCell(1).setCellValue(date.toString());
                row.getCell(2).setCellValue(businessData.getTurnover());
                row.getCell(3).setCellValue(businessData.getValidOrderCount());
                row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
                row.getCell(5).setCellValue(businessData.getUnitPrice());
                row.getCell(6).setCellValue(businessData.getNewUsers());
            }
            //通过输出流将poi创建的excel文件进行下载
            ServletOutputStream out = response.getOutputStream();
            excel.write(out);

            out.close();
            excel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
