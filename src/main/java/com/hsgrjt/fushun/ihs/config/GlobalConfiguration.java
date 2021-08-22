package com.hsgrjt.fushun.ihs.config;

import java.util.Calendar;
import java.util.Date;

public abstract class GlobalConfiguration {

    public static final long[] GLOBAL_SUPER_USER_IDS = { 1, 2 };

    public static final int COOKIE_SAVE_SECONDS = 43200;
    public static final int COOKIE_SAVE_SECONDS_REMEMBER = 2592000;
    public static final int TIME_ZONE_HOURS_OFFSET = 8;

    // 统计每日销售时的偏移小时数，避免0点以后的接待记录被计算到下一天
    public static final int HOURS_OF_DAY_OFFSET_FOR_RETAIL = 7;

    private static void dayStart(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.add(Calendar.HOUR_OF_DAY, HOURS_OF_DAY_OFFSET_FOR_RETAIL);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
    }

    public static Date todayStart() {
        Calendar start = Calendar.getInstance();
        dayStart(start);
        return start.getTime();
    }

    public static Date todayEnd() {
        Calendar end = Calendar.getInstance();
        dayStart(end);
        end.add(Calendar.HOUR_OF_DAY, 24);
        return end.getTime();
    }

}
