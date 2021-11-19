package com.hsgrjt.fushun.ihs.system.service;

import com.hsgrjt.fushun.ihs.system.entity.HeatMachine;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hsgrjt.fushun.ihs.system.entity.User;

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

    HeatMachine findById(Integer id);

    /**
     * 根据用户查询用户可管理都公司下的机组
     * @return
     */
    List<HeatMachine> getMachineByUser(User user);

}
