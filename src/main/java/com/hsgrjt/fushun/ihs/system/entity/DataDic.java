package com.hsgrjt.fushun.ihs.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 晟翼科技
 * @since 2021-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="DataDic对象", description="")
public class DataDic implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String dataItem;

    private String name;

    private Integer sortId;

    private String category;

}
