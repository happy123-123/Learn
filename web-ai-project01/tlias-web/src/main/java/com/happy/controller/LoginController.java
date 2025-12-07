package com.happy.controller;

import com.happy.pojo.Emp;
import com.happy.pojo.LoginInfo;
import com.happy.pojo.Result;
import com.happy.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private EmpService empService;

    @PostMapping("/login")
    public Result login(@RequestBody Emp emp){
        LoginInfo loginInfo = empService.loginEmp(emp);
        return Result.success(loginInfo);
    }
}
