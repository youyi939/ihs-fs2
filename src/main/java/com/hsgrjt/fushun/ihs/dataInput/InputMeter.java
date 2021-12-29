package com.hsgrjt.fushun.ihs.dataInput;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/12/29 下午10:57
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InputMeter {

    private Integer machineId;
    private double[] water;
    private double[] power;
    private double[] heat;

}
