package com.hsgrjt.fushun.ihs.system.service;

import java.util.List;

/**
 * 包含实体的BaseService
 * @param <T>
 */
public interface SysService<T> extends SysBaseService {

    List<T> findByEntity(T entity);
}
