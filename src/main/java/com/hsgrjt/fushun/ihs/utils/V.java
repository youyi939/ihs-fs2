package com.hsgrjt.fushun.ihs.utils;

import org.codehaus.jettison.json.JSONObject;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @Author: KenChen
 * @Description: 杂七杂八的工具类
 * @Date: Create in  2021/8/22 下午4:30
 */

public class V {

    /**
     * 返回一个字符串 默认值为 一个空字符串
     */
    public static String get(String v) {
        return isEmpty(v) ? "" : v;
    }

    /**
     * 返回一个字符串
     */
    public static String get(String v, String def) {
        return isEmpty(v) ? def : v;
    }

    /**
     * 获取一个int对象
     */
    public static Integer get(Integer v) {
        return v == null ? 0 : v;
    }

    /**
     * 获取一个int对象
     */
    public static Integer get(Integer v, Integer def) {
        return v == null ? def : v;
    }

    /**
     * 获取一个Long对象
     */
    public static Long get(Long v) {
        return v == null ? 0L : v;
    }

    /**
     * 获取一个int对象
     */
    public static Long get(Long v, Long def) {
        return v == null ? def : v;
    }

    /**
     * 判断一个字符串是否为空
     */
    public static boolean isEmpty(String v) {
        return v == null || v.length() == 0;
    }

    /**
     * 判断一个列表是否为空
     */
    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    /**
     * 判断一个对象是否为空
     */
    public static boolean isEmpty(Object v) {
        return v == null;
    }

    /**
     * 判断一个对象是否为空
     */
    public static boolean isEmpty(Object v,boolean isEmpty) {
        if(isEmpty){
            return v == null || "".equals(v.toString());
        }
        return v == null;
    }



    /**
     * 判断一个数组是否为空
     */
    public static boolean isEmpty(Object[] v) {
        return v == null || v.length == 0;
    }

    /**
     * 验证是否为整数
     */
    public static final String REGEX_INTEGER = "^[-\\+]?[\\d]*$";

    /**
     * 验证是否为Double
     */
    public static final String REGEX_DOUBLE= "^[-\\+]?[\\d]*[.][\\d]*$";


    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(16[0-9])|(14[0-9])|(13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 校验字符串是否为整数
     *
     * @param value
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isInteger(String value) {
        if(isEmpty(value)){
            return false;
        }
        return Pattern.matches(REGEX_INTEGER, value);
    }

    /**
     * 校验字符串是否为浮点数
     *
     * @param value
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isDouble(String value) {
        if(isEmpty(value)){
            return false;
        }
        return Pattern.matches(REGEX_DOUBLE, value);
    }

    /**
     * 校验字符串是否布尔型
     *
     * @param value
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isBoolean(String value) {
        if (isEmpty(value)){
            return false;
        }
        if("TRUE".equals(value.toUpperCase())||"FALSE".equals(value.toUpperCase())){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }
}