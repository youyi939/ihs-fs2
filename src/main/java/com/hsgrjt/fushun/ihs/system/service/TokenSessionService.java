package com.hsgrjt.fushun.ihs.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hsgrjt.fushun.ihs.system.entity.TokenSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 晟翼科技
 * @since 2021-08-10
 */
public interface TokenSessionService extends IService<TokenSession> {

    boolean deleteToken(String token);

    TokenSession getTokenDetails(String token);

}
