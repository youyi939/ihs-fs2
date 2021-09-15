package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsgrjt.fushun.ihs.system.entity.DataDic;
import com.hsgrjt.fushun.ihs.system.entity.dto.IhsFileAddDTO;
import com.hsgrjt.fushun.ihs.system.mapper.DataDicMapper;
import com.hsgrjt.fushun.ihs.system.service.DataDicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 晟翼科技
 * @since 2021-08-13
 */
@Service
public class DataDicServiceImpl implements DataDicService {

    @Autowired
    DataDicMapper dataDicMapper;


    @Override
    public void save(DataDic entity) {
        dataDicMapper.insert(entity);
    }

    @Override
    public void removeById(Long id) {
        dataDicMapper.deleteById(id);
    }

    @Override
    public List<DataDic> findAll(String key) {
        QueryWrapper<DataDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DataDic::getCategory,key);
        return dataDicMapper.selectList(queryWrapper);
    }

}
