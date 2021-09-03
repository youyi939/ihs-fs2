package com.hsgrjt.fushun.ihs.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author 晟翼科技
 * @since 2021-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="User对象", description="用户信息表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户名，唯一，可用于登录")
    private String username;

    @ApiModelProperty(value = "密码，经md5加密")
    private String password;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "身份证号码")
    private String idNum;

    @ApiModelProperty(value = "称谓")
    private String appellation;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "性别：男、女、未知")
    private String gender;

    @ApiModelProperty(value = "分组")
    private String userGroup;

    @ApiModelProperty(value = "角色")
    private String userRole;

    @ApiModelProperty(value = "权限")
    private String permission;

    @ApiModelProperty(value = "允许操作的公司")
    private String allowCompanys;

    @ApiModelProperty(value = "允许操作的服务部")
    private String allowServiceDeps;

    @ApiModelProperty(value = "允许操作的中心站")
    private String allowCenterStations;

    @ApiModelProperty(value = "允许操作的换热站列表")
    private String allowStations;

    @ApiModelProperty(value = "允许操作的机组列表")
    private String allowMachines;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "绑定的微信openId")
    private String wechatOpenId;

    @ApiModelProperty(value = "微信昵称")
    private String wechatNickname;

    @ApiModelProperty(value = "主题")
    private String theme;

    @ApiModelProperty(value = "我的记事本内容")
    private String note;

    @ApiModelProperty(value = "自定义设置信息")
    private String customSetting;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "必备字段数据首次插入时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "必备字段数据最后一次修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    /**
     * 逻辑删除字段
     */
    @TableLogic
    private boolean deleteFlag;

    @ApiModelProperty(value = "必备字段乐观锁版本号")
//    @Version
    private Integer version;

}
