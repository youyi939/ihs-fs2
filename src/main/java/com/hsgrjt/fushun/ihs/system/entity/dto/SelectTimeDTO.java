package com.hsgrjt.fushun.ihs.system.entity.dto;

import com.hsgrjt.fushun.ihs.system.entity.SelectTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/12/1 上午12:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectTimeDTO {

    private String msg;

    private SelectTime selectTime;

}
