package com.happy.controller;

import com.happy.pojo.Result;
import com.happy.utils.AliyunOSSOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {
    @Autowired
    private AliyunOSSOperator AliyunOSSOperator;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        String upload = AliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
        return Result.success(upload);
    }
}
