package com.happy.mapper;

import com.happy.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
    @Select("select count(*) from emp e left join dept d on e.dept_id=d.id")
    Long findCount();

    /*
     * 手动注解sql语句
     * */
//    @Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id=d.id " +
//            "where e.name like concat(#{name},'%') and e.gender=#{gender} and e.entry_date between #{begin} and #{end} " +
//            "order by e.update_time desc limit #{page},#{pageSize}")

    List<Emp> findALL(EmpQueryParam empQueryParam);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertEmp(Emp emp);

    void deleteEmp(List<Integer> ids);

    Emp findBackEmp(Integer id);

    void updateEmp(Emp emp);

//    List<JobOption> findJobOption();
    List<Map<String, Object>> findJobOption();

    List<Gender> findGender();

    List<Emp> findEmpList();

    LoginInfo loginEmp(Emp emp);
}
