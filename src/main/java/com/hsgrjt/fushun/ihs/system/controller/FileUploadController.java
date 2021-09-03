package com.hsgrjt.fushun.ihs.system.controller;

import com.hsgrjt.fushun.ihs.system.entity.dto.IhsFileAddDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.service.CommonService;
import com.hsgrjt.fushun.ihs.system.service.IhsFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @Author: KenChen
 * @Description: 文件上传/下载
 * @Date: Create in  2021/8/20 下午9:59
 */
@Api(tags = {"CORE 文件上传"})
@RestController
@RequestMapping("/system/file")
public class FileUploadController {

    /**
     * 文件上传service
     */
    @Autowired
    private CommonService commonService;

    /**
     * 文件记录上传service
     */
    @Autowired
    IhsFileService ihsFileService;

    /**
     * 上传文件至阿里云 oss
     *
     * @param file
     * @param uploadKey
     * @return
     * @throws Exception
     */
    // TODO: 2021/8/22 解析token获取用户名及id
    @ApiOperation(value="上传文件到oss接口")
    @RequestMapping(value = "/upload/oss/{id}", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> uploadOSS(@RequestParam(value = "file") MultipartFile file, String uploadKey,  @PathVariable String id) throws Exception {
        Map<String,Object> apiResult = commonService.uploadOSS(file, uploadKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        System.out.println("**********************************"+apiResult.toString());


        IhsFileAddDTO ihsFileAddDTO = new IhsFileAddDTO();
        ihsFileAddDTO.setCategory(uploadKey);
        ihsFileAddDTO.setName(file.getOriginalFilename());            //文件名字
        ihsFileAddDTO.setShareToUsers("1,2");    //分享给某某用户的id
        ihsFileAddDTO.setCreateUserId(Integer.parseInt(id));    //上传该文件的用户
        ihsFileAddDTO.setUrl(apiResult.get("ossFileUrlBoot")+"");             //文件的完整url
        System.out.println();
        System.out.println(ihsFileAddDTO.toString());

        ihsFileService.save(ihsFileAddDTO);

        return new ResponseEntity<>(apiResult, headers, HttpStatus.CREATED);
    }


}
