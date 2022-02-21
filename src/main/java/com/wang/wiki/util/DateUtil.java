package com.wang.wiki.util;

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
}
