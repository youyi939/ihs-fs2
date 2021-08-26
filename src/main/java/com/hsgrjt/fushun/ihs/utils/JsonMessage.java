package com.hsgrjt.fushun.ihs.utils;

public abstract class JsonMessage {

    public static final String SUCCESS = "{\"msg\":\"success\"}";

    public static final String EXIST = "{\"msg\":\"exist\"}";
    public static final String NOT_EXIST = "{\"msg\":\"not_exist\"}";

    public static final String ACCESS_DENIED = "{\"msg\":\"access_denied\"}";
    public static final String PARAMETER_ERROR = "{\"msg\":\"parameter_error\"}";
    public static final String DATABASE_ERROR = "{\"msg\":\"database_error\"}";
    public static final String INCORRECT_VERIFICATION_CODE = "{\"msg\":\"incorrect_verification_code\"}";
    public static final String INVALID_MOBILE_NUM = "{\"msg\":\"invalid_mobile_num\"}";
    public static final String LOGIN_FAIL = "{\"msg\":\"login_fail\"}";
    public static final String LOGIN_TIMEOUT = "{\"msg\":\"login_timeout\"}";
    public static final String SMS_SEND_FAIL = "{\"msg\":\"sms_send_fail\"}";
    public static final String DELETE_NOT_ALLOWED = "{\"msg\":\"delete_not_allowed\"}";
    public static final String SELF_REVOKE_FROM_EDIT_STAFF_NOT_ALLOWED = "{\"msg\":\"self_revoke_from_edit_staff_not_allowed\"}";

    public static String getInsertSuccessResult(long id) {
        String result = "{\"msg\":\"success\",\"id\":\"" + id + "\"}";
        return result;
    }

}
