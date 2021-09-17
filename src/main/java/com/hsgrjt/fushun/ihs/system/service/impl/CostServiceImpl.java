package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.system.entity.Cost;
import com.hsgrjt.fushun.ihs.system.entity.Goods;
import com.hsgrjt.fushun.ihs.system.mapper.CostMapper;
import com.hsgrjt.fushun.ihs.system.mapper.GoodsMapper;
import com.hsgrjt.fushun.ihs.system.service.CostService;
import com.hsgrjt.fushun.ihs.system.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/9/13 下午4:58
 */
@Service
public class CostServiceImpl implements CostService {

    @Autowired
    CostMapper costMapper;

    @Override
    public void save(Cost entity) {
        costMapper.insert(entity);
    }

    @Override
    public void removeByIds(List<Integer> ids) {
        costMapper.deleteBatchIds(ids);
    }

    @Override
    public IPage<Cost> findByPage(Page<Cost> page) {
        return costMapper.selectPage(page,new QueryWrapper<Cost>());
    }


}
