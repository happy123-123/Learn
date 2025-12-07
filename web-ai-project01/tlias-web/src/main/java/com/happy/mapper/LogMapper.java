package com.happy.mapper;

import com.happy.pojo.Log;
import com.happy.pojo.LogQueryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Mapper
public interface LogMapper {
    @Select("select count(*) from log")
    Long findAll();

    @Select("select * from log limit #{page},#{pageSize}")
    List<Log> find(LogQueryParam logQueryParam);

    @Insert("insert into log(operate_emp_id,operate_time,class_name,method_name,method_params,return_value,cost_time) " +
            "values(#{operateEmpId},#{operateTime},#{className},#{methodName},#{methodParams},#{returnValue},#{costTime})")
    void insert(Log log);
}
