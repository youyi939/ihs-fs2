package com.hsgrjt.fushun.ihs.system.service;

import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: KenChen
 * @Description: oss service层
 * @Date: Create in  2021/8/20 下午10:57
 */
public interface CommonService {
    /**
     * 上传文件至阿里云 oss
     *
     * @param file
     * @param uploadKey
     * @return
     * @throws Exception
     */
    R uploadOSS(MultipartFile file, String uploadKey) throws Exception;
}
