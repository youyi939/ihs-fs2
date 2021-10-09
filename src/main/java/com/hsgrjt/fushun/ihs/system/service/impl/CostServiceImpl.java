package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.system.entity.Cost;
import com.hsgrjt.fushun.ihs.system.entity.Goods;
import com.hsgrjt.fushun.ihs.system.entity.HeatMachine;
import com.hsgrjt.fushun.ihs.system.entity.dto.CostDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.mapper.CostMapper;
import com.hsgrjt.fushun.ihs.system.mapper.GoodsMapper;
import com.hsgrjt.fushun.ihs.system.service.CostService;
import com.hsgrjt.fushun.ihs.system.service.GoodsService;
import com.hsgrjt.fushun.ihs.system.service.HeatMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    HeatMachineService machineService;

    @Override
    public void save(Cost entity) {
        Goods goods = goodsMapper.selectById(entity.getGoodsId());
        entity.setPrice(goods.getPrice());
        costMapper.insert(entity);
    }

    @Override
    public void removeByIds(List<Integer> ids) {
        costMapper.deleteBatchIds(ids);
    }

    @Override
    public R<List<CostDTO>> findCost( String type,String ids) {
        String[] temp = ids.split(",");
        List<CostDTO> dtoList = new ArrayList<>();

        for (String s : temp) {
            QueryWrapper<Cost> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Cost::getType,type).eq(Cost::getMachineId,Integer.parseInt(s));
            HeatMachine machine = machineService.findById(Integer.parseInt(s));
            CostDTO costDTO = new CostDTO();
            costDTO.setMachineName(machine.getName());
            List<Cost> costList = new ArrayList<>(costMapper.selectList(queryWrapper));
            costDTO.setCostList(costList);
            dtoList.add(costDTO);
        }

        return R.ok("查询成功").putData(dtoList);
    }


}
