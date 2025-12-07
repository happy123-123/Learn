package com.happy.mapper;

import com.happy.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface EmpEmprMapper {

    void insert(List<EmpExpr> empExprList);

    void deleteEmpr(List<Integer> ids);

    List<EmpExpr> findBackEmpr(Integer id);


    void updateEmpr(List<EmpExpr> empExprList);
}
