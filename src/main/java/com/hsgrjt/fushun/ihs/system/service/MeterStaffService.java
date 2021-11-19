package com.hsgrjt.fushun.ihs.system.service;

import com.hsgrjt.fushun.ihs.system.entity.MeterStaff;
import com.hsgrjt.fushun.ihs.system.entity.dto.IhsFileAddDTO;
import org.springframework.stereotype.Service;

/**
 * @Author: KenChen
 * @Description: 水电热表的service类
 * @Date: Create in  2021/11/19 下午2:40
 */
@Service
public interface MeterStaffService {

    /**
     * 新建
     * @param entity
     */
    void save(MeterStaff entity);

}
