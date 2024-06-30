package com.eric.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间工具类
 *
 * @author zhimin
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    private static final Logger log = LoggerFactory.getLogger(DateUtils.class);

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYYMMDD = "yyyyMMdd";
    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String YYYY_MM_DD_00_00_00 = "yyyy-MM-dd 00:00:00";

    public static String UTC_SSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    public static String HH_MM = "HH:mm";

    /**
     * 获取当前时间的时分秒字符串
     *
     * @return 当前时间的时分秒字符串
     */
    public static String getCurrentTimeStr() {
        log.info("getCurrentTimeStr begin.");

        // 获取当前时间
        LocalTime currentTime = LocalTime.now();

        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // 格式化时间
        String formattedTime = currentTime.format(formatter);

        log.info("getCurrentTimeStr end. formattedTime: {}", formattedTime);

        return formattedTime;
    }

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String getDateTimeByNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
        return LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
    }

    /**
     * 当前时间戳
     *
     * @return
     */
    public static final String getNowTimeInMillis() {
        return String.valueOf(Calendar.getInstance().getTimeInMillis());
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算相差天数
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
    }

    /**
     * 计算相差秒数
     */
    public static long differentSecByMillisecond(Date startTime, Date endTime) {
        return (endTime.getTime() - startTime.getTime()) / 1000;
    }

    /**
     * 计算时间差
     *
     * @param endDate   最后时间
     * @param startTime 开始时间
     * @return 时间差（天/小时/分钟）
     */
    public static String timeDistance(Date endDate, Date startTime) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startTime.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    public static long timeDistanceSecond(Date endDate, Date startTime) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;

        long diff = endDate.getTime() - startTime.getTime();

        // 计算差多少秒//输出结果
        return diff % nd % nh % nm / ns;
    }

    /**
     * 增加 LocalDateTime ==> Date
     */
    public static Date toDate(LocalDateTime temporalAccessor) {
        ZonedDateTime zdt = temporalAccessor.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 增加 LocalDate ==> Date
     */
    public static Date toDate(LocalDate temporalAccessor) {
        LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 获取UTC格式的过期时间
     * @param expectedExpiredTime 范围为5分钟到15天，请换算为分钟
     * @return UTC时间
     */
    public static String payTradeExpireUTCTimeStr(int expectedExpiredTime) {
        SimpleDateFormat formatter = new SimpleDateFormat(UTC_SSSZ);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(payTradeExpireTime(expectedExpiredTime));
    }

    /**
     * 获取UTC格式的过期时间
     * @param expectedExpiredTime 范围为5分钟到15天，请换算为分钟
     * @return UTC时间
     */
    public static Date payTradeExpireTime(int expectedExpiredTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expectedExpiredTime);
        return calendar.getTime();
    }

    public static Date convertUtcTime(String utcTime) {
        if (StringUtils.isEmpty(utcTime)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(UTC_SSSZ);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdf.parse(utcTime);
        } catch (ParseException pe) {
            return null;
        }

    }

    /**
     * 获取明天的字符串
     *
     * @param format
     * @return 字符串
     */
    public static String getTomorrowStr(String format) {
        LocalDate today = LocalDate.now();  // 获取当前日期
        LocalDate tomorrow = today.plusDays(1);  // 获取明天的日期

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);  // 创建格式化对象
        String formattedTomorrow = tomorrow.format(formatter);  // 将明天的日期格式化为你需要的格式

        return formattedTomorrow;
    }

    /**
     * 时间字符串转时间
     *
     * @param dateStr 时间字符串
     * @param format 时间格式
     * @return 时间
     */
    public static Date strToDate(String dateStr, String format) {
        if (StringUtils.isAnyBlank(dateStr, format)) {
            return null;
        }

        try {
            return parseDate(dateStr, format);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取明年的今天
     *
     * @return 明年的今天
     */
    public static String getNextYearToday() {
        LocalDate today = LocalDate.now();
        LocalDate nextYear = today.plusYears(1);

        // 创建格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");

        // 格式化时间
        String formattedDateTime = nextYear.format(formatter);
        return formattedDateTime;
    }

    /**
     * 获取明年的明天
     *
     * @return 明年的明天
     */
    public static String getNextYearTomorrow() {
        LocalDate today = LocalDate.now();
        LocalDate nextYear = today.plusDays(1).plusYears(1);

        // 创建格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");

        // 格式化时间
        String formattedDateTime = nextYear.format(formatter);
        return formattedDateTime;
    }

    /**
     * 校验日期时间格式
     *
     * @param dateTimeStr 日期时间字符串
     * @param format 格式
     * @return true:校验通过；false:校验失败
     */
    public static boolean validateFormat(String dateTimeStr, String format) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(format)
                .parseDefaulting(ChronoField.ERA, 1)
                .toFormatter()
                .withChronology(IsoChronology.INSTANCE)
                .withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalDate.parse(dateTimeStr, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 日期是否小于今天
     *
     * @param dateStr 日期字符串
     * @return true:小于今天；false:大于等于今天
     */
    public static boolean isDateLessThanToday(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        return date.isBefore(LocalDate.now());
    }

    /**
     * 前一个日期是否小于后一个日期
     *
     * @param dateStr1 前一个日期
     * @param dateStr2 后一个日期
     * @return true:前一个日期 小于 后一个日期；false:前一个日期 大于等于 后一个日期
     */
    public static boolean isDateBefore(String dateStr1, String dateStr2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(dateStr1, formatter);
        LocalDate date2 = LocalDate.parse(dateStr2, formatter);
        return date1.isBefore(date2);
    }

    /**
     * 计算两个日期字符串之间的天数
     *
     * @param date1 日期字符串
     * @param date2 日期字符串
     * @return 天数
     */
    public static int calculateDaysBetween(String date1, String date2) {
        LocalDate parsedDate1 = null;
        LocalDate parsedDate2 = null;
        try {
            parsedDate1 = LocalDate.parse(date1);
            parsedDate2 = LocalDate.parse(date2);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }

        Period period = Period.between(parsedDate1, parsedDate2);
        return period.getDays();
    }

    /**
     * 获取日期字符串对应的是周几
     * @param dateStr 日期字符串
     * @param format 日期字符串格式
     * @return 1周日，2周一，3周二，4周三，5周四，6周五，7周六
     */
    public static Integer getDayOfWeek(String dateStr, String format) {
        if (StringUtils.isAnyBlank(dateStr, format)) {
            log.info("getDayOfWeek end, dateStr is blank or format is blank, dateStr: {}, format: {}", dateStr, format);
            return null;
        }

        Integer dayOfWeek = null;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date date = dateFormat.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        } catch (Exception e) {
            log.error("getDayOfWeek failed, dateStr: {}, format: {}, e", dateStr, format, e);
        }

        return dayOfWeek;
    }

    /**
     * 判断时间是否在一个区间内
     *
     * @param time 需要确定的时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 比较结果
     */
    public static boolean between(Date time, Date startTime, Date endTime) {
        return time.after(startTime) && time.before(endTime);
    }

    public static String datePlusDay(String dateStr, String format, int plusDayNum) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        localDate = localDate.plusDays(plusDayNum);
        return localDate.format(formatter);
    }

    public static int[] getDateArray(String arrDate, String depDate) {
        return getDateArray(arrDate, depDate, true);
    }

    public static int[] getDateArray(String arrDate, String depDate, boolean getDays) {
        int[] array = new int[3];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYYMMDD);
        LocalDate localDate = LocalDate.parse(arrDate);
        int bookDateBegin = Integer.parseInt(localDate.format(formatter));
        localDate = LocalDate.parse(depDate);
        localDate = localDate.plusDays(-1);
        int bookDateEnd = Integer.parseInt(localDate.format(formatter));
        array[0] = bookDateBegin;
        array[1] = bookDateEnd;
        if (!getDays) {
            return array;
        }
        int days = 0;
        int date = bookDateBegin;
        while (date <= bookDateEnd) {
            days++;
            localDate = LocalDate.parse(String.valueOf(date), formatter);
            localDate = localDate.plusDays(1);
            date = Integer.valueOf(localDate.format(formatter));
        }
        array[2] = days;
        return array;
    }

    /**
     * 获取今年年数
     *
     * @return 今年年数
     */
    public static int getThisYear() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR);
    }

    /**
     * 获取明年年数
     *
     * @return 明年年数
     */
    public static int getNextYear() {
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        return currentYear + 1;
    }

    /**
     * 前一个日期是否大于后一个日期
     *
     * @param dateStr1 前一个日期
     * @param dateStr2 后一个日期
     * @return true:前一个日期 大于 后一个日期；false:前一个日期 小于等于 后一个日期
     */
    public static boolean isDateAfter(String dateStr1, String dateStr2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(dateStr1, formatter);
        LocalDate date2 = LocalDate.parse(dateStr2, formatter);
        return date1.isAfter(date2);
    }

    /**
     * 获取当前月份的第前n个月的第一天
     *
     * @return 当前月份的第前n个月的第一天
     */
    public static  String getCurrentBeforeMonthFirstDay(Integer preMonths){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -preMonths);
        Date firstDayOfMonth = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(firstDayOfMonth);
    }

}
