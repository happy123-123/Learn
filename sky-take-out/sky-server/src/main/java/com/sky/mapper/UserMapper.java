package com.sky.mapper;

import com.sky.entity.DailyUserStatistics;
import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserMapper {
    /*
    * 根据微信openid查询用户
    * */
    @Select("select * from user where openid=#{openid}")
    User getByOpenid(String openid);

    @Insert("insert into user (openid, name, phone, sex, id_number, avatar, create_time) " +
            "values (#{openid}, #{name}, #{phone}, #{sex}, #{idNumber}, #{avatar}, #{createTime})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insert(User build);

    @Select("select * from user where id=#{id}")
    User getById(Long userId);

    List<DailyUserStatistics> getNewUsers(LocalDateTime begin, LocalDateTime end);
}
