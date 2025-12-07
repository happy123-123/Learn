package com.happy.service;

import com.happy.mapper.LogMapper;
import com.happy.pojo.Log;
import com.happy.pojo.LogQueryParam;
import com.happy.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public PageResult<Log> findLog(LogQueryParam logQueryParam) {
        Long total = logMapper.findAll();
        logQueryParam.setPage((logQueryParam.getPage() - 1) * logQueryParam.getPageSize());
        List<Log> rows = logMapper.find(logQueryParam);
        return new PageResult<>(total, rows);
    }
}
