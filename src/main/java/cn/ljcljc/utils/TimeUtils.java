package cn.ljcljc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimeUtils {
    public static String addTime(String time) {
        // 解析传入的时间字符串
        int amount = Integer.parseInt(time.replaceAll("[^0-9]", ""));
        String unit = time.replaceAll("[0-9]", "");

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 计算新时间
        LocalDateTime newTime = now.plus(amount, getTimeUnit(unit));

        // 格式化成字符串并返回
        return newTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private static ChronoUnit getTimeUnit(String unit) {
        switch (unit) {
            case "秒":
                return ChronoUnit.SECONDS;
            case "分钟":
                return ChronoUnit.MINUTES;
            case "小时":
                return ChronoUnit.HOURS;
            case "天":
                return ChronoUnit.DAYS;
            case "周":
                return ChronoUnit.WEEKS;
            case "月":
                return ChronoUnit.MONTHS;
            case "年":
                return ChronoUnit.YEARS;
            default:
                throw new IllegalArgumentException("不支持的时间单位：" + unit);
        }
    }

    public static String calcTimeDiff(String dateStart, String dateEnd) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1=df.parse(dateStart);
            Date d2=df.parse(dateEnd);
            //毫秒ms
            long diff = d2.getTime() - d1.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            return Long.toString(diffDays);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
