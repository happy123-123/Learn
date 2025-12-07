package com.happy.service;

import com.happy.mapper.EmpEmprMapper;
import com.happy.mapper.EmpMapper;
import com.happy.pojo.*;
import com.happy.utils.JwtUtils;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpEmprMapper empEmprMapper;

    @Override
    public PageResult<Emp> divideFind(EmpQueryParam empQueryParam) {
        Long total = empMapper.findCount();
        Integer page = (empQueryParam.getPage() - 1) * empQueryParam.getPageSize();
        empQueryParam.setPage(page);
        List<Emp> rows = empMapper.findALL(empQueryParam);
        return new PageResult<Emp>(total, rows);
    }

    @Override
    public void insertEmp(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insertEmp(emp);
        List<EmpExpr> empExprList = emp.getEmpExprList();
        if (!CollectionUtils.isEmpty(empExprList)) {
            empExprList.forEach(empExpr -> empExpr.setEmpId(emp.getId()));
            empEmprMapper.insert(empExprList);
        }
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        empMapper.deleteEmp(ids);

        empEmprMapper.deleteEmpr(ids);
    }

    @Override
    public Emp findBack(Integer id) {
        Emp emp = empMapper.findBackEmp(id);
        emp.setEmpExprList(empEmprMapper.findBackEmpr(id));
        return emp;
    }

    @Override
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateEmp(emp);
        List<EmpExpr> empExprList = emp.getEmpExprList();
        if (!CollectionUtils.isEmpty(empExprList)) {
            empEmprMapper.updateEmpr(empExprList);
        }
    }

    @Override
    public List<Emp> findEmpList() {
        List<Emp> list = empMapper.findEmpList();
        return list;
    }

    @Override
    public LoginInfo loginEmp(Emp emp) {
        LoginInfo info = empMapper.loginEmp(emp);
        if (info == null) {
            throw new RuntimeException("用户名或密码输入有误");
        }else {
            Map<String,Object> claims=new HashMap<>();
            claims.put("id",info.getId());
            claims.put("username",info.getUsername());
            String s = JwtUtils.generateJwt(claims);
            info.setToken(s);
            return info;
        }
    }
}
