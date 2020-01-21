package com.leileikang.batchqueryorinsertnativeproject.utils;

import com.leileikang.batchqueryorinsertnativeproject.common.Constants;
import com.leileikang.batchqueryorinsertnativeproject.domain.QueryTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static com.leileikang.batchqueryorinsertnativeproject.utils.HStringUtils.maxStringDate;
import static com.leileikang.batchqueryorinsertnativeproject.utils.HStringUtils.minStringDate;

/**
 * 处理时间工具类
 *
 * @Author kangleilei
 * @Date 2020/1/21
 **/
public class DateUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final String YEAR_FORMAT = "yyyy";
    private static final String MONTH_FORMAT = "yyyy-MM";


    /**
     * 补全日期
     * @param date 日期
     * @return 补全后
     */
    public static String completeDate(String date) {
        if (DateUtil.isValidDate(date, YEAR_FORMAT)) {
            date = date + "-01-01";
        } else if (DateUtil.isValidDate(date, MONTH_FORMAT)) {
            date = date + "-01";
        }
        return date;
    }

    /**
     * 校验日期字符串是否满足指定格式
     * @param dateString    日期字符串
     * @param format        指定格式
     * @return  是否符合
     */
    public static boolean isValidDate(String dateString, String format) {
        if (HStringUtils.isblank(format)) {
            format = Constants.DEFAULT_DATE_FORMAT;
        }
        boolean convertSuccess;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            //采用严格的解析方式，防止类似 “2018-05-35” 类型的字符串通过
            simpleDateFormat.setLenient(false);
            Date date = simpleDateFormat.parse(dateString);
            //重复比对一下，防止前后格式不一致
            String newDateString = simpleDateFormat.format(date);
            convertSuccess = dateString.equals(newDateString);
        } catch (Exception e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 获取昨天日期yyyy-MM-dd String
     *
     * @return
     */
    public static String getYesterday() {
        return getBeforeDateStr(DateUtil.getToday(), 1);
    }

    /**
     * 获取昨天日期yyyy-MM-dd Date
     *
     * @return
     */
    public static Date getYesterdayDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String str = getBeforeDateStr(DateUtil.getToday(), 1);
            date = formatter.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取昨天日期最小值yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getYesterdayMinDate() {
        Date date = minStringDate(getBeforeDateStr(DateUtil.getToday(), 1));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * 获取昨天日期最大值yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getYesterdayMaxDate() {
        Date date = maxStringDate(getBeforeDateStr(DateUtil.getToday(), 1));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * 获取今天日期yyyy-MM-dd
     *
     * @return
     */
    public static String getToday() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static String getBeforeDateStr(String currentDateStr, int n) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar instance = Calendar.getInstance();
        String format = "";
        try {
            instance.setTime(simpleDateFormat.parse(currentDateStr));
            instance.add(Calendar.DATE, -1 * n);
            Date time = instance.getTime();
            format = simpleDateFormat.format(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 字符串转成时间
     *
     * @param str
     * @return
     */
    public static Date StringDate(String str) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formatter.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取前若干天日期
     *
     * @param n 个数
     * @return yyyy-MM-dd
     */
    public static String getCurrentDateBefore(int n) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -n);
        return sdf.format(c.getTime());
    }

    /**
     * 获取之前若干星期的星期一
     *
     * @param n 个数
     * @return yyyy-MM-dd
     */
    public static String getPreviousMonday(int n) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -n * 7);
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            c.add(Calendar.DATE, -1);
        }
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return sdf.format(c.getTime());
    }

    /**
     * 获取最近的n天
     *
     * @return 时间范围实体
     */
    public static QueryTime getNearDays(int n) {
        String beginTime = getCurrentDateBefore(n);
        String endTime = getCurrentDateBefore(0);
        return new QueryTime(beginTime, endTime);
    }

    /**
     * 获取前若干月日期
     *
     * @param n 个数
     * @return  yyyy-MM
     */
    public static String getCurrentMonthBefore(int n) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -n);
        return sdf.format(c.getTime());
    }

    /**
     * 获取月份起始日期
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMinMonthDate(String date) throws ParseException {
        date = completeDate(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获取月份最后日期
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMaxMonthDate(String date) throws ParseException {
        date = completeDate(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 前几月
     *
     * @param n 自然数
     * @return 开始-结束时间
     */
    public static QueryTime getAgoMonth(int n) throws ParseException {
        getCurrentMonthBefore(n);
        String beginTime = getMinMonthDate(getCurrentMonthBefore(n));
        String endTime = getMaxMonthDate(getCurrentMonthBefore(n));
        return new QueryTime(beginTime, endTime);
    }

}
