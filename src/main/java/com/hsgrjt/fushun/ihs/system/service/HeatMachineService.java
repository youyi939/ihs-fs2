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
     * 根据用户查询用户公司下的机组
     * @return 机组list
     */
    List<HeatMachine> getMachineByUser(User user);


    /**
     * 获取当前公司下的中心站集合
     * @param company 公司名称
     * @return 中心站的集合
     */
    List<String>getCenterStation(String company);


    /**
     * 获取当前中心站下的机组
     * @param centerStation 中心站名字
     * @return 机组list
     */
    List<HeatMachine>getMachineByCenterStation(String centerStation);

}
