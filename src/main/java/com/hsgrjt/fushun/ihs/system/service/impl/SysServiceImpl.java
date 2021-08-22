package com.hsgrjt.fushun.ihs.system.service.impl;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/8/22 下午4:37
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.hsgrjt.fushun.ihs.system.service.SysService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @description:  我要为以后重写一些方法留个位置
 * @created by shili in 2019-05-06 10:00
 * @modify:
 **/
public class SysServiceImpl<M extends BaseMapper<T>, T> implements SysService<T> {

    @Autowired
    protected M baseMapper;

    protected Class<?> currentModelClass() {
        return ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    /**
     * 获取 SqlStatement
     *
     * @param sqlMethod ignore
     * @return ignore
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    /**
     * 批量操作 SqlSession
     */
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

    @Override
    public List<T> findByEntity(T entity){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity);
        return baseMapper.selectList(queryWrapper);
    }

}
