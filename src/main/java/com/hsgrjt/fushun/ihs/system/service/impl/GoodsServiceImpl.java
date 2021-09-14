package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.system.entity.Goods;
import com.hsgrjt.fushun.ihs.system.mapper.GoodsMapper;
import com.hsgrjt.fushun.ihs.system.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/9/13 下午4:58
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public void save(Goods entity) {
        goodsMapper.insert(entity);
    }

    @Override
    public void updateById(Goods body) {
        goodsMapper.updateById(body);
    }

    @Override
    public void removeById(Long id) {
        goodsMapper.deleteById(id);
    }

    @Override
    public IPage<Goods> findByPage(Page<Goods> page) {
        return goodsMapper.selectPage(page,new QueryWrapper<Goods>());
    }
}
