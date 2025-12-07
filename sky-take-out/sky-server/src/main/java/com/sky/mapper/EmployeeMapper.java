package com.sky.mapper;

import com.sky.anno.Annotation;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username
     * @return
     */
    @Select("select * from sky_take_out.employee where username = #{username}")
    Employee getByUsername(String username);

    @Annotation(value = OperationType.INSERT)
    void addAll(Employee employee);

    @Select("select count(*) from sky_take_out.employee")
    Long count();

    List<Employee> page(EmployeePageQueryDTO employeePageQueryDTO);

    @Annotation(value = OperationType.UPDATE)
    void controlEmpStatus(Employee employee);

    Employee findById(Long id);

    @Annotation(value = OperationType.UPDATE)
    void update(Employee employee);
}
