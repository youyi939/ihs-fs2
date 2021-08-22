package com.hsgrjt.fushun.ihs.system.service.impl;

import com.hsgrjt.fushun.ihs.config.OSSConfig;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.service.CommonService;
import com.hsgrjt.fushun.ihs.utils.OSSBootUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: KenChen
 * @Description: OSS 上传文件实现类
 * @Date: Create in  2021/8/20 下午10:59
 */
@Service("commonService")
public class CommonServiceImpl implements CommonService {


    @Autowired
    private OSSConfig ossConfig;

    /**
     * 上传文件至阿里云 oss
     */
    @Override
    public R uploadOSS(MultipartFile file, String uploadKey) throws Exception {

        String ossFileUrlSingle = null;

        // 高依赖版本 oss 上传工具
        String ossFileUrlBoot = null;
        ossFileUrlBoot = OSSBootUtil.upload(ossConfig, file, "upload/demo");

        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("ossFileUrlSingle", ossFileUrlSingle);
        resultMap.put("ossFileUrlBoot", ossFileUrlBoot);

        return R.ok().putData(resultMap);
    }
}