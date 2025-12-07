package com.happy.service;

import com.happy.pojo.Log;
import com.happy.pojo.LogQueryParam;
import com.happy.pojo.PageResult;

public interface LogService {
    PageResult<Log> findLog(LogQueryParam logQueryParam);
}
