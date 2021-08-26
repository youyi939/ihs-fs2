package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hsgrjt.fushun.ihs.system.entity.TokenSession;
import com.hsgrjt.fushun.ihs.system.mapper.TokenSessionMapper;
import com.hsgrjt.fushun.ihs.system.service.TokenSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 晟翼科技
 * @since 2021-08-10
 */
@Service
public class TokenSessionServiceImpl extends ServiceImpl<TokenSessionMapper, TokenSession> implements TokenSessionService {

    @Autowired
    private TokenSessionMapper tokenMapper;

    @Override
    public boolean deleteToken(String token) {
        tokenMapper.delete(new QueryWrapper<TokenSession>().eq("token", token));
        return true;
    }

    @Override
    public TokenSession getTokenDetails(String token) {
        return tokenMapper.selectOne(new QueryWrapper<TokenSession>().eq("token", token));
    }

}
