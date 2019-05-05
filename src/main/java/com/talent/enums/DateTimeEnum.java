package com.talent.enums;

/**
 * 时间类型枚举类
 */
public enum DateTimeEnum {

    /**
     * 带时分的日期格式
     */
    DATE_TIME("yyyy/MM/dd HH:mm","4位年/2位月/2位日 24小时制两位小时:10为基数增量的分钟","([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})/(((0[13578]|1[02])/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)/(0[1-9]|[12][0-9]|30))|(02/(0[1-9]|[1][0-9]|2[0-8]))) ([0-1][0-9]|2[0-3]):([0-5]0)"),
    /**
     * 只有日期的时间格式
     */
    DATE("yyyy/MM/dd","4位年/2位月/2位日","([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})/(((0[13578]|1[02])/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)/(0[1-9]|[12][0-9]|30))|(02/(0[1-9]|[1][0-9]|2[0-8])))"),
    /**
     * 只有时分的事件格式
     */
    TIME("HH:mm","24小时制两位小时:10为基数增量的分钟","([0-1][0-9]|2[0-3]):([0-5]0)");

    private String formatCode;

    private String desc;

    private String patterCode;

    DateTimeEnum(String formatCode, String desc, String patterCode) {
        this.formatCode = formatCode;
        this.desc = desc;
        this.patterCode = patterCode;
    }

    public String getFormatCode() {
        return formatCode;
    }

    public void setFormatCode(String formatCode) {
        this.formatCode = formatCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPatterCode() {
        return patterCode;
    }

    public void setPatterCode(String patterCode) {
        this.patterCode = patterCode;
    }
}
