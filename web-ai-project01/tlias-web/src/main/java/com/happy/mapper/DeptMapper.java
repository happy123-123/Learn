package com.happy.mapper;

import com.happy.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {
//方式一:使用@Results注解,手动结果映射
//    @Results({
//            @Result(column = "current_time", property = "currentTime"),
//            @Result(column = "update_time", property = "updateTime")
//    })

//方式二:起别名
//    @Select("select id,name,current_date currentTime,update_time updateTime from dept")

//方式三:用mybatis的驼峰命名映射

    @Select("select * from dept order by update_time desc")
    public List<Dept> find();

    @Delete("delete from dept where id=#{id}")
    public void delete(Integer id);

    @Insert("insert into dept(name,create_time,update_time) values(#{name},#{createTime},#{updateTime})")
    void insertAll(Dept dept);

    @Select("select * from dept where id=#{id}")
    public Dept find01(Integer id);

    @Update("update dept set name=#{name},update_time=#{updateTime} where id=#{id}")
    void updateAll(Dept dept);

    @Select("select count(*) from dept left join emp on dept.id=emp.dept_id where dept_id=#{id}")
    Integer linkDept(Integer id);
}
