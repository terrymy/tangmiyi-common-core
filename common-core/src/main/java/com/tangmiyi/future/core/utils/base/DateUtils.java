package com.tangmiyi.future.core.utils.base;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Slf4j
public class DateUtils {

    public final static String DEFAULT_TIMEZONE = "GMT+8";

    public final static String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public final static String ISO_SHORT_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public final static String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public final static String MONTH_DATE_FORMAT = "yyyy-MM";

    public final static String SHORT_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public final static String FULL_SEQ_FORMAT = "yyyyMMddHHmmssSSS";

    public final static String[] MULTI_FORMAT = {DEFAULT_DATE_FORMAT, DEFAULT_TIME_FORMAT, ISO_DATE_TIME_FORMAT,
            ISO_SHORT_DATE_TIME_FORMAT, SHORT_TIME_FORMAT, "yyyy-MM"};

    public final static String FORMAT_YYYY = "yyyy";

    public final static String FORMAT_YYYYMM = "yyyyMM";

    public final static String FORMAT_YYYYMMDD = "yyyyMMdd";

    public final static String FORMAT_YYYYMMDDHH = "yyyyMMddHH";

    public final static String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public final static String FORMAT_YYYYMMDDHHMMSS_SSS = "yyyyMMddHHmmssSSS";

    public final static String FORMAT_YYYYMMDDHHMM = "yyyyMMddHHmm";

    /**
     * 转化为格式yyyyMMddHHmm的字符串日期
     *
     * @param date
     * @return
     */
    public static String fromtStringShortTime(Date date) {
        DateFormat df = new SimpleDateFormat(FORMAT_YYYYMMDDHHMM);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 转化为格式yyyyMMddHHmmssSSS的字符串日期
     *
     * @param date
     * @return
     */
    public static String fromtStringTime(Date date) {
        DateFormat df = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS_SSS);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 使用默认日期格式(yyyy-MM-dd)输出日期字符串
     *
     * @param date 需要格式化的日期
     * @return 格式化后的字符串日期
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }

        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
    }


    /**
     * 使用默认日期格式(yyyy-MM-dd)输出日期
     *
     * @param date 需要格式化的日期
     * @return 格式化后的字符串日期
     */
    public static Date parseDate(Date date) {
        String time = new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
        try {
            return new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 使用默认日期格式(yyyy-MM)输出日期字符串
     *
     * @param date 需要格式化的日期
     * @return 格式化后的字符串日期
     */
    public static String formatMonth(Date date) {
        if (date == null) {
            return "";
        }

        return new SimpleDateFormat(MONTH_DATE_FORMAT).format(date);
    }

    /**
     * 指定格式输出日期字符串
     *
     * @param date   需要格式化的日期
     * @param format 格式化字符串
     * @return 格式化后的字符串日期
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 将日期格式化为整数，如20160407
     *
     * @param date   日期
     * @param format 格式化字符串，中间不可带非数字符合
     * @return 日期的整数形式
     */
    public static Integer formatDateToInt(Date date, String format) {
        if (date == null) {
            return null;
        }
        return Integer.valueOf(new SimpleDateFormat(format).format(date));
    }

    /**
     * 将日期格式化为长整型，如20160407195400
     *
     * @param date   日期
     * @param format 格式化字符串，中间不可带非数字符合
     * @return 日期的长整型形式
     */
    public static Long formatDateToLong(Date date, String format) {
        if (date == null) {
            return null;
        }
        return Long.valueOf(new SimpleDateFormat(format).format(date));
    }

    /**
     * 使用默认时间格式(yyyy-MM-dd HH:mm:ss)输出带时间字符串
     *
     * @param date 日期
     * @return 带时间的日期字符串
     */
    public static String formatTime(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(DEFAULT_TIME_FORMAT).format(date);
    }

    /**
     * 使用默认时间格式(yyyy-MM-dd HH:mm)输出带时间字符串
     *
     * @param date 日期
     * @return 带时间的日期字符串
     */
    public static String formatShortTime(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(SHORT_TIME_FORMAT).format(date);
    }

    /**
     * 采用默认日期格式格式化当前日期
     *
     * @return 当前日期的字符串形式
     */
    public static String formatDateNow() {
        return formatDate(DateUtils.currentDate());
    }

    /**
     * 采用默认时间格式格式化当前时间
     *
     * @return 当前时间的字符串形式
     */
    public static String formatTimeNow() {
        return formatTime(DateUtils.currentDate());
    }

    /**
     * 格式化日期
     *
     * @param date   日期字符串格式
     * @param format 日期格式
     * @return 格式化后的日期
     */
    public static Date parseDate(String date, String format) {

        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析时间
     *
     * @param date   时间字符串格式
     * @param format 时间格式
     * @return 解析后的时间
     */
    public static Date parseTime(String date, String format) {
        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析日期
     *
     * @param date 字符串格式的日期
     * @return 日期
     */
    public static Date parseDate(String date) {
        return parseDate(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 解析时间
     *
     * @param date 字符串
     * @return 带时间部分的日期
     */
    public static Date parseTime(String date) {
        return parseTime(date, DEFAULT_TIME_FORMAT);
    }


    public static String getHumanDisplayForTimediff(Long diffMillis) {
        if (diffMillis == null) {
            return "";
        }
        long day = diffMillis / (24 * 60 * 60 * 1000);
        long hour = (diffMillis / (60 * 60 * 1000) - day * 24);
        long min = ((diffMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long se = (diffMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        StringBuilder sb = new StringBuilder();
        if (day > 0) {
            sb.append(day + "D");
        }
        DecimalFormat df = new DecimalFormat("00");
        sb.append(df.format(hour) + ":");
        sb.append(df.format(min) + ":");
        sb.append(df.format(se));
        return sb.toString();
    }

    /**
     * 把类似2014-01-01 ~ 2014-01-30格式的单一字符串转换为两个元素数组
     */
    public static Date[] parseBetweenDates(String date) throws ParseException {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        date = date.replace("～", "~");
        Date[] dates = new Date[2];
        String[] values = date.split("~");
        dates[0] = parseMultiFormatDate(values[0].trim());
        dates[1] = parseMultiFormatDate(values[1].trim());
        return dates;
    }

    public static Date parseMultiFormatDate(String date) {
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(date, MULTI_FORMAT);
        } catch (ParseException e) {
            log.error("parseMultiFormatDate failture, date: {}", date, e);
            return null;
        }
    }


    /**
     * N天之后
     *
     * @param n
     * @param date
     * @return
     */
    public static Date nDaysAfter(Integer n, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + n);
        return cal.getTime();
    }

    /**
     * N天之前
     *
     * @param n
     * @param date
     * @return
     */
    public static Date nDaysAgo(Integer n, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - n);
        return cal.getTime();
    }

    private static Date currentDate;

    public static Date setCurrentDate(Date date) {
        // Validation.isTrue(DynamicConfigService.isDevMode(),
        // "当前操作只能在开发测试运行模式才可用");
        if (date == null) {
            currentDate = null;
        } else {
            currentDate = date;
        }
        return currentDate;
    }

    /**
     * 为了便于在模拟数据程序中控制业务数据获取到的当前时间
     * 提供一个帮助类处理当前时间，为了避免误操作，只有在devMode开发模式才允许“篡改”当前时间
     *
     * @return
     */
    public static Date currentDate() {
        if (currentDate == null) {
            return new Date();
        }
        // if (DynamicConfigService.isDevMode()) {
        // return currentDate;
        // } else {
        // return new Date();
        // }
        return new Date();
    }

    /**
     * 获取指定日期在指定年份所处的周数，如传入日期为"2016-01-08"，返回201602
     *
     * @param date 指定日期
     * @return 年份+周数
     */
    public static Integer getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String yearString = formatDate(date, FORMAT_YYYY);
        int weekNum = c.get(Calendar.WEEK_OF_YEAR);
        if (weekNum < 10) {
            yearString = StringUtils.rightPad(yearString, 5, "0");
        }
        return Integer.valueOf(yearString + weekNum);
    }


    /**
     * 传入一个日期 返回当天零点的时期
     */
    public static Date getDayStart(Date date) {
        date = date != null ? date : new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        setCalendar(calendar);

        return calendar.getTime();
    }


    /**
     * 传入一个日期 返回当天24点的时期
     */
    public static Date getDayEnd(Date date) {
        date = date != null ? date : new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        setCalendar(calendar);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }


    /**
     * 传入一个日期 返回本周零点的时期
     */
    public static Date getWeekStart(Date date) {
        date = date != null ? date : new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        setCalendar(calendar);
        return calendar.getTime();
    }

    /**
     * 传入一个日期 返回本周零点的时期
     */
    public static Date getWeekEnd(Date date) {
        date = date != null ? date : new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_MONTH, 1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        setCalendar(calendar);
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        System.out.println(DateUtils.getMonthDayEnd(15));

        System.out.println(getWeekStart(null));
        System.out.println(getWeekEnd(null));
    }


    /**
     * 传入一个日期 返回本月1号零点的时期
     */
    public static Date getMonthStart(Date date) {
        date = date != null ? date : new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        setCalendar(calendar);

        //获得当前月第一天
        return calendar.getTime();
    }

    /**
     * 传入一个日期 返回下个月1号零点的时期
     */
    public static Date getMonthEnd(Date date) {
        date = date != null ? date : new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        setCalendar(calendar);

        //将当前月加1；
        calendar.add(Calendar.MONTH, 1);
        //在当前月的下一月基础上减去1毫秒
        calendar.add(Calendar.MILLISECOND, -1);
        //获得当前月最后一天
        return calendar.getTime();
    }


    /**
     * 传入一个日期 返回上个月1号零点的时期
     */
    public static Date getLastMonthStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        setCalendar(calendar);
        //获得当前月第一天
        return calendar.getTime();
    }

    /**
     * 传入一个日期 返回上个月月末
     */
    public static Date getLastMonthEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        setCalendar(calendar);

        //将当前天加1；
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        //在当前月的下一月基础上减去1毫秒
        calendar.add(Calendar.MILLISECOND, -1);

        return calendar.getTime();
    }

    private static Calendar setCalendar(Calendar calendar) {
        //将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        //将秒至0
        calendar.set(Calendar.SECOND, 0);
        //将毫秒至0
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }


    /**
     * 获取某个日期上个月的时间
     */
    public static Date getLastMonthDate() {
        return getLastMonthDate(new Date());
    }

    /**
     * 获取某个日期上个月的时间
     */
    public static Date getLastMonthDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 获取某个日期昨天的时间
     */
    public static Date getYestodayDate() {
        return getYestodayDate(new Date());
    }

    /**
     * 获取某个日期昨天的时间
     */
    public static Date getYestodayDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 计算两个日期的间隔天数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getDayInterval(Date startTime, Date endTime) {
        long intervalMilli = endTime.getTime() - startTime.getTime();
        return (int) (intervalMilli / (24 * 60 * 60 * 1000));
    }

    /**
     * 间距日期间隔获取增加的日期
     *
     * @param startTime
     * @param endTime
     * @param num
     * @return
     */
    public static String formatDiffDate(Date startTime, Date endTime, int num) {
        int diff = getDayInterval(startTime, endTime);
        if (diff > 30 && diff <= 365) {
            return formatDate(org.apache.commons.lang3.time.DateUtils.addMonths(startTime, num), MONTH_DATE_FORMAT);
        }
        if (diff > 365) {
            return formatDate(org.apache.commons.lang3.time.DateUtils.addYears(startTime, num), FORMAT_YYYY);
        }
        return formatDate(org.apache.commons.lang3.time.DateUtils.addDays(startTime, num), DEFAULT_DATE_FORMAT);
    }

    /**
     * 获取当前日期并设置某一天的结束时间
     */
    public static Date getMonthDayEnd(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        setCalendar(calendar);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        //在当前月的下一月基础上减去1毫秒
        calendar.add(Calendar.MILLISECOND, -1);

        return calendar.getTime();
    }

    /**
     * 获取入参日期并设置某一天的结束时间
     */
    public static Date getMonthDayEnd(Date time, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        setCalendar(calendar);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        //在当前月的下一月基础上减去1毫秒
        calendar.add(Calendar.MILLISECOND, -1);

        return calendar.getTime();
    }

    /**
     * 传入一个日期 返回下个月1号零点的时期
     */
    public static Date getLastMonthEnd(Date date) {
        date = date != null ? date : new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        setCalendar(calendar);

        //将当前月加1；
        calendar.add(Calendar.MONTH, 1);
        //在当前月的下一月基础上减去1毫秒
        calendar.add(Calendar.MILLISECOND, -1);
        //获得当前月最后一天
        return calendar.getTime();
    }


    /**
     * 计算两个日期之间相差的天数
     *
     * @param beginDate 较小的时间
     * @param endDate  较大的时间
     * @return 相差天数
     */
    public static long daysBetween(Date beginDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        long beginTime = calendar.getTimeInMillis();

        calendar.setTime(endDate);
        long endTime = calendar.getTimeInMillis();
        long betweenDays = (endTime - beginTime) / (1000 * 3600 * 24);
        if((endTime - beginTime) % (1000 * 3600 * 24) > 0) {
            betweenDays++;
        }

        return betweenDays;
    }

    /**
     * 获取当前时间距离当天0点0分0秒的毫秒
     */
    public static long timeMillions(long currentMillions) {
        Date now = new Date(currentMillions);
        Date beginOfDay = beginOfDate(now);
        long beginOfDayMilliSeconds = beginOfDay.getTime();
        return currentMillions - beginOfDayMilliSeconds;
    }

    /**
     * 2016-11-10 07:33:23, 则返回2016-11-10 00:00:00
     */
    public static Date beginOfDate(final Date date) {
        return org.apache.commons.lang3.time.DateUtils.truncate(date, Calendar.DATE);
    }
}
