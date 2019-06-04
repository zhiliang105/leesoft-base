package com.leeframework.common.utils;

import java.text.DecimalFormat;

/**
 * 工具类
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月3日 上午1:11:03
 */
public class NumberUtil {

    public static double formatDouble(double d) {
        DecimalFormat formater = new DecimalFormat("#0.##");
        return Double.parseDouble(formater.format(d));
    }
}
