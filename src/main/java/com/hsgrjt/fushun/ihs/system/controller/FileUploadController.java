package com.hsgrjt.fushun.ihs.system.controller;

import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.service.CommonService;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.logging.Logger;

/**
 * @Author: KenChen
 * @Description: 文件上传/下载
 * @Date: Create in  2021/8/20 下午9:59
 */
@RestController
@RequestMapping("/system/file")
public class FileUploadController {

    @Autowired
    private CommonService commonService;

    @GetMapping(value = "/test")
    public String test(){
        return "hello";
    }


    /**
     * 上传文件至阿里云 oss
     *
     * @param file
     * @param uploadKey
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload/oss", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> uploadOSS(@RequestParam(value = "file") MultipartFile file, String uploadKey) throws Exception {
        R apiResult = commonService.uploadOSS(file, uploadKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(apiResult, headers, HttpStatus.CREATED);
    }


}
