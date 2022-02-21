package com.wang.wiki.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author Wang
 */
public class DateUtil {

    public static SimpleDateFormat YMDSDF = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 按偏移量获取日期
     *
     * @param today 偏移起始日期
     * @param index 偏移量
     * @return 返回结果
     */
    public static Date getDate(Date today, int index) {
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_MONTH, index);
        return c.getTime();
    }

    /**
     * 按偏移量获取日期
     *
     * @param today 偏移起始日期
     * @param index 偏移量
     * @return 返回结果
     */
    public static String getDateStr(Date today, int index) {
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_MONTH, index);
        return YMDSDF.format(c.getTime());
    }

    /**
     * Str转换日期格式
     *
     * @param date 日期
     * @return 返回结果
     */
    public static Date parse(String date) {
        try {
            return YMDSDF.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parse(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
