package com.hsgrjt.fushun.ihs.system.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: KenChen
 * @Description: 通用返回对象
 * @Date: Create in  2021/8/20 下午10:59
 */


@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "是否成功")
    private Boolean success = false;

    @ApiModelProperty(value = "状态码",notes="有时处理逻辑时需要")
    private String code = "0";

    @ApiModelProperty(value = "服务器消息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private T data;

    @ApiModelProperty(value = "时间戳")
    private Long timestamp = System.currentTimeMillis();

    public R() {
        this.success = true;
        this.msg = "";
    }

    /**
     * 返回错误
     * @param msg 消息
     * @return
     */
    public static R error(String msg) {
        R r = new R();
        r.setSuccess(false);
        r.setCode("500");
        r.setMsg(msg);
        return r;
    }

    /**
     * 返回错误
     * @param code 消息状态码
     * @param msg 消息
     * @return
     */
    public static R error(String code, String msg) {
        R r = new R();
        r.setSuccess(false);
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    /**
     * 返回成功
     * @param msg 消息
     * @return
     */
    public static R ok(String msg)
    {
        R r = new R();
        r.setSuccess(true);
        r.setCode("200");
        r.setMsg(msg);
        return r;
    }

    /**
     * 返回成功
     * @param code 逻辑状态码
     * @param msg 消息
     * @return
     */
    public static R ok(String code,String msg)
    {
        R r = new R();
        r.setCode(code);
        r.setSuccess(true);
        r.setMsg(msg);
        return r;
    }

    /**
     * 返回成功 无消息 不常用
     * @return
     */
    public static R ok() {
        return new R ();
    }

    /**
     * 链式调用时 插入数据
     * @param value 数据内容
     * @return
     */
    public R putData(T value) {
        setData(value);
        return this;
    }


}
