package vben.common.core.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;

import java.util.Date;

public class DateUtils {

    public static String now() {
        return DateUtil.formatDateTime(new DateTime());
    }

    public static TimeInterval timer() {
        return new TimeInterval();
    }

    public static DateTime parse(CharSequence dateCharSequence) {
        return DateUtil.parse(dateCharSequence);
    }

    public static String format(Date date, String format) {
        return DateUtil.format(date, format);
    }

    public static DateTime date() {
        return new DateTime();
    }

    public static long betweenDay(Date beginDate, Date endDate, boolean isReset) {
       return DateUtil.betweenDay(beginDate, endDate, isReset);
    }
}
