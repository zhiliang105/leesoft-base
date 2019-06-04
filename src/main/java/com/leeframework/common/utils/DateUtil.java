package com.leeframework.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间操作工具类
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月25日 下午9:07:06
 */
public final class DateUtil extends org.apache.commons.lang3.time.DateUtils {

    /**
     * 获取当前时间与指定时间相隔的分钟
     * @author lee
     * @date 2016年6月8日 上午11:06:53
     * @param theDate 指定的时间
     * @return 如果theDate==null,则返回0,否则返回当前时间与指定时间的差值(分钟)
     */
    public static long currentMinite(Date theDate) {
        if (theDate != null) {
            long longs = new Date().getTime() - theDate.getTime();
            return longs / MILLIS_PER_MINUTE;
        }
        return 0L;
    }

    /**
     * 默认方式表示的系统当前日期，具体格式：yyyy-MM-dd
     * @author lee
     * @date 2016年4月22日 上午12:00:23
     * @return 返回当前日期的yyyy-MM-dd格式
     */
    public static String dateFormat() {
        return dateFormat(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
    }

    /**
     * 日期转换为字符串
     * @author lee
     * @date 2016年4月21日 下午11:57:12
     */
    public static String dateFormat(Date date, SimpleDateFormat format) {
        if (date != null) {
            return format.format(date);
        }
        return null;
    }

    /**
     * 当前日期转换为字符串
     * @author lee
     * @date 2016年4月21日 下午11:57:28
     */
    public static String dateFormat(SimpleDateFormat format) {
        return format.format(new Date());
    }

    /**
     * 格式化时间
     * @author lee
     * @date 2016年4月21日 下午11:59:06
     * @param date 字符串格式日期
     * @param format 格式化
     */
    public static String dateFormat(String date, String format) {
        SimpleDateFormat sformat = new SimpleDateFormat(format);
        Date _date = null;
        try {
            _date = sformat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sformat.format(_date);
    }

    /**
     * 默认方式表示的系统当前日期，具体格式：yyyy-MM-dd HH:mm:ss
     * @author lee
     * @date 2016年4月22日 上午12:00:23
     * @return 返回当前日期的yyyy-MM-dd HH:mm:ss格式
     */
    public static String datetimeFormat() {
        return dateFormat(new Date(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 将毫秒转换成对应的日期类型字符串
     * @author lee
     * @date 2016年5月18日 下午11:57:55
     * @param mils
     */
    public static String datetimeFormat(long mils) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mils);
    }

    /**
     * 获取当前年份
     * @datetime 2018年5月15日 上午10:38:41
     */
    public static String getCurrentYear() {
        return dateFormat(new SimpleDateFormat("yyyy"));
    }

    /**
     * 获取当前月份
     * @datetime 2018年6月10日 下午11:31:30
     */
    public static String getCurrentMonth() {
        return dateFormat(new SimpleDateFormat("MM"));
    }
    /**
     * 获取当前月份
     * @datetime 2018年6月10日 下午11:31:30
     */
    public static String getCurrentDay() {
        return dateFormat(new SimpleDateFormat("dd"));
    }
}
