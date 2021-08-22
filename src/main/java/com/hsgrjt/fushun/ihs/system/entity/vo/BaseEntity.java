package com.hsgrjt.fushun.ihs.system.entity.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

/**
 * @Author: KenChen
 * @Description: 预留基础类
 * @Date: Create in  2021/7/29 上午10:20
 */

@Data
public abstract class BaseEntity {

    /**
     * 逻辑删除字段
     */
    @TableLogic
    private boolean deleteFlag;

}
