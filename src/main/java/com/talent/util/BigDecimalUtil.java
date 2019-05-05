package com.talent.util;

import java.math.BigDecimal;

/**
 * @program: DownLoadImageDemo
 * @author: Mr.Guo
 * @description: BigDecimal 工具类
 * @create: 2019-05-05 14:27
 */
public class BigDecimalUtil {

    private BigDecimalUtil() {}

    /**
     * 除以100，保留2位小数
     * @param bigDecimal
     * @return
     */
    public static BigDecimal divide100(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        return bigDecimal.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 除以1000，保留2位小数
     * @param bigDecimal
     * @return
     */
    public static BigDecimal divide1000(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        return bigDecimal.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 除以100，不指定精确度
     * @param bigDecimal
     * @return
     */
    public static BigDecimal divide100noScale(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        return bigDecimal.divide(new BigDecimal(100));
    }

    /**
     * 除以10的幂次方，保留2位小数
     * @param bigDecimal
     * @param index
     * @return
     */
    public static BigDecimal divideNum(BigDecimal bigDecimal, int index) {
        if (bigDecimal == null) {
            return null;
        }
        return bigDecimal.divide(new BigDecimal(Math.pow(10, index)), 2, BigDecimal.ROUND_HALF_UP);
    }

}
