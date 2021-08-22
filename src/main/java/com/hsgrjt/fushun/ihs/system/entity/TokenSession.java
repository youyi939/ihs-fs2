package com.hsgrjt.fushun.ihs.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Calendar;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2021-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TokenSession对象", description="")
public class TokenSession implements Serializable {

    private static final long serialVersionUID = 1L;

    public TokenSession() {
    }

    public TokenSession(String token, int saveSeconds, long userId) {
        this.token = token;
        this.saveSeconds = saveSeconds;
        this.userId = userId;
        Calendar exp = Calendar.getInstance();
        exp.add(Calendar.SECOND, saveSeconds);
        this.expirationTime = exp.getTime();
    }

    private Long userId;

    @ApiModelProperty(value = "token字符串")
    @TableId(value = "token", type = IdType.ASSIGN_ID)
    private String token;

    @ApiModelProperty(value = "保存时间秒数")
    private Integer saveSeconds;

    @ApiModelProperty(value = "失效时间")
    private Date expirationTime;

    @ApiModelProperty(value = "必备字段数据首次插入时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;


}
