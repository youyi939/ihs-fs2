package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hsgrjt.fushun.ihs.system.entity.IhsFile;
import com.hsgrjt.fushun.ihs.system.entity.dto.IhsFileAddDTO;
import com.hsgrjt.fushun.ihs.system.mapper.IhsFileMapper;
import com.hsgrjt.fushun.ihs.system.service.IhsFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/8/22 下午4:27
 */
@Service
public class IhsFileServiceImpl extends SysServiceImpl<IhsFileMapper, IhsFile> implements IhsFileService {



    @Override
    public IPage<IhsFile> queryList(IPage<IhsFile> page, String name) {
        return null;
    }

    @Override
    public void save(IhsFileAddDTO entity) {

        IhsFile ihsFile  = new IhsFile();
        BeanUtils.copyProperties(entity,ihsFile);

        ihsFile.setGmtCreate(new Date());
        ihsFile.setGmtModified(new Date());

        System.out.println("**************************************   "+ihsFile.toString());
        baseMapper.insert(ihsFile);
    }

    @Override
    public void updateById(IhsFile body) {

    }

    @Override
    public void removeById(Long id) {

    }

}
