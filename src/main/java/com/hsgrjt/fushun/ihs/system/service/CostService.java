package com.hsgrjt.fushun.ihs.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.system.entity.Cost;
import com.hsgrjt.fushun.ihs.system.entity.Goods;
import com.hsgrjt.fushun.ihs.system.entity.dto.CostDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: KenChen
 * @Description: 责任成本
 * @Date: Create in  2021/8/22 下午4:27
 */
@Service
public interface CostService {

    /**
     * 新建责任成本
     * @param entity
     */
    void save(Cost entity);

    /**
     * 删除责任成本记录
     * @param ids
     */
    void removeByIds(List<Integer> ids);


    /**
     * 查询责任成本记录
     * @return
     */
    R<List<CostDTO>> findCost(String type, String ids);

}
