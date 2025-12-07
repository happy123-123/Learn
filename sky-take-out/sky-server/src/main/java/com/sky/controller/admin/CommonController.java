package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/admin/common/upload")
    public Result upload(MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();
        int index = file.getOriginalFilename().lastIndexOf(".");
        String s =uuid + file.getOriginalFilename().substring(index);
        String upload = aliOssUtil.upload(file.getBytes(),s);
        return Result.success(upload);
    }
}
