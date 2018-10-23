package com.jangni.fastdfs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class UploadController {

    @RequestMapping("fileUpload")
    @ResponseBody
    public String upload(@RequestParam("fileName") MultipartFile file, Map<String, Object> map) {
        // 显示图片
        map.put("fileName", file.getOriginalFilename());
        UploadFileUtil uploadFileUtil = new UploadFileUtil();
        String url = "";
        try {
            url = "192.168.0.121:8089/" + uploadFileUtil.upload(file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(url);

        return url;
    }
}
