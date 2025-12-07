package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/admin/employee/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/admin/employee/logout")
    public Result<String> logout() {

        return Result.success();
    }

    /*
    * 新增员工
    * */
    @PostMapping("/admin/employee")
    public Result add(@RequestBody EmployeeDTO employeeDTO){
        employeeService.addEmp(employeeDTO);
        return Result.success();
    }

    /*
    * 员工分页查询
    * */
    @GetMapping("/admin/employee/page")
    public Result findByPage(EmployeePageQueryDTO employeePageQueryDTO){
        PageResult page = employeeService.findPage(employeePageQueryDTO);
        return Result.success(page);
    }

    @PostMapping("/admin/employee/status/{status}")
    public Result controlStatus(@PathVariable Integer status,Long id){
        employeeService.controlStatus(status,id);
        return Result.success();
    }

//    @annotation
    @PutMapping("/admin/employee")
    public Result updateEmp(@RequestBody EmployeeDTO employeeDTO){
        employeeService.updateEmp(employeeDTO);
        return Result.success();
    }

    @GetMapping("/admin/employee/{id}")
    public Result getById(@PathVariable("id") Long id){
        Employee employee = employeeService.findById(id);
        return Result.success(employee);
    }
}
