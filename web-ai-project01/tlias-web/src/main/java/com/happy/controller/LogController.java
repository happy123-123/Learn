package com.happy.controller;

import com.happy.pojo.Log;
import com.happy.pojo.LogQueryParam;
import com.happy.pojo.PageResult;
import com.happy.pojo.Result;
import com.happy.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {
    @Autowired
    private LogService logService;

    @GetMapping("/log/page")
    public Result findLogPage(LogQueryParam logQueryParam) {
        PageResult<Log> log = logService.findLog(logQueryParam);
        return Result.success(log);
    }
}
