package com.hsgrjt.fushun.ihs.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
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
 * @since 2021-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="HeatMachine对象", description="")
public class HeatMachine implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private String company;

    private String serviceDep;

    private String centerStation;

    private String station;

    private String opcPrefix;

    private String opcPostfix;

    private String phone1;

    private String phone2;

    private String address;

    private Float longitude;

    private Float latitude;

    private Integer sortId;

    private Integer manager;

    private String remark;

    @Version
    private String version;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}
