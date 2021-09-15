package com.hsgrjt.fushun.ihs.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.system.entity.Goods;
import com.hsgrjt.fushun.ihs.system.entity.IhsFile;
import com.hsgrjt.fushun.ihs.system.entity.dto.IhsFileAddDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: KenChen
 * @Description: 物料统计表service
 * @Date: Create in  2021/8/22 下午4:27
 */
@Service
public interface GoodsService {

    /**
     * 新建物料
     * @param entity
     */
    void save(Goods entity);


    /**
     * 更新物料
     * @param body
     */
    void updateById(Goods body);


    /**
     * 移除物料
     * @param ids
     */
    void removeByIds(List<Integer> ids);

    IPage<Goods> findByPage(Page<Goods> page);


}
