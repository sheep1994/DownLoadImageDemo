package com.talent.util;

import com.talent.enums.DateTimeEnum;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: DownLoadImageDemo
 * @author: Mr.Guo
 * @description: 日期工具类
 * @create: 2019-04-23 11:44
 */
public class DateUtil {

    private DateUtil() {}

    public static Date convertStrToDate(String str, DateTimeEnum dateTimeEnum) throws Exception {
        DateFormat df = new SimpleDateFormat(dateTimeEnum.getFormatCode());
        // 校验日期正则表达式
        validateTime(str, dateTimeEnum.getPatterCode());
        if (dateTimeEnum == DateTimeEnum.TIME) {
            return getDateTime(new Date(), str);
        }
        return df.parse(str);
    }

    /**
     * 获取时间
     * @param str
     * @param date
     * @return
     */
    private static Date getDateTime(Date date, String str) throws Exception {
        // 正则表达式校验时间
        validateTime(str, DateTimeEnum.TIME.getPatterCode());
        int hours = Integer.parseInt(str.split(":")[0]);
        int minute = Integer.parseInt(str.split(":")[1]);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getIntegerDate(date, Calendar.HOUR_OF_DAY, false) );
        calendar.set(Calendar.HOUR_OF_DAY,hours);
        calendar.set(Calendar.MINUTE,minute);
        return calendar.getTime();
    }

    /**
     * 根据输入获取整的时间
     * @param sourceDate
     * @param field 取整单位 (Calendar.DAY_OF_MONTH,Calendar.HOUR_OF_DAY,Calendar.MINUTE,Calendar.SECOND,Calendar.MILLISECOND)
     * @param only true-下级单位不会清零,false-下级单位会清零
     * @return
     */
    public static Date getIntegerDate(Date sourceDate,int field,boolean only){
        Calendar ca=Calendar.getInstance();
        ca.setTime(sourceDate);
        switch (field){
            case Calendar.DAY_OF_MONTH:
                ca.set(Calendar.DAY_OF_MONTH,1);
                if(only){ break;}
            case Calendar.HOUR_OF_DAY:
                ca.set(Calendar.HOUR_OF_DAY,0);
                if(only){ break;}
            case Calendar.MINUTE:
                ca.set(Calendar.MINUTE,0);
                if(only){ break;}
            case Calendar.SECOND:
                ca.set(Calendar.SECOND,0);
                if(only){ break;}
            case Calendar.MILLISECOND:
                ca.set(Calendar.MILLISECOND,0);
                break;
            default:
        }
        return ca.getTime();
    }

    /**
     * 校验日期正则表达式是否正确
     * @param str 日期字符串
     * @param patterCode 日期格式
     */
    private static void validateTime(String str, String patterCode) throws Exception {
        Pattern pattern = Pattern.compile(patterCode);
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            throw new Exception("日期格式不匹配");
        }
    }
}
