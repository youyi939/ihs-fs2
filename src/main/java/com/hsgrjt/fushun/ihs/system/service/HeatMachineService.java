package com.hsgrjt.fushun.ihs.system.service;

import com.hsgrjt.fushun.ihs.system.entity.HeatMachine;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 晟翼科技
 * @since 2021-08-11
 */
public interface HeatMachineService extends IService<HeatMachine> {

    List<HeatMachine> selectMachineByUser(String ids);


}
