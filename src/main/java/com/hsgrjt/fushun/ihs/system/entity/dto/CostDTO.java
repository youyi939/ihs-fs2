package com.hsgrjt.fushun.ihs.system.entity.dto;

import com.hsgrjt.fushun.ihs.system.entity.Cost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: KenChen
 * @Description: 责任成本DTO
 * @Date: Create in  2021/9/28 下午1:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostDTO {

    private String machineName;

    private List<Cost> costList;

}
