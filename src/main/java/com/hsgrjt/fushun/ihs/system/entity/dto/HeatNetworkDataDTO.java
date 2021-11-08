package com.hsgrjt.fushun.ihs.system.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hsgrjt.fushun.ihs.system.entity.HeatNetworkData;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/10/18 下午8:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeatNetworkDataDTO extends HeatNetworkData {

    private String stationName;

    private String createDate;

}
