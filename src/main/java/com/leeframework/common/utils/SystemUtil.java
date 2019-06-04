package com.leeframework.common.utils;

import java.util.UUID;

/**
 * 系统工具类
 * @author 李志亮 (Lee) <279683131(@qq.com)
 * @date Date:2016年5月26日 Time: 下午10:40:42
 * @version 1.0
 * @since version 1.0
 * @update
 */
public abstract class SystemUtil {

    private SystemUtil() {
    }

    /**
     * 获取系统自定义的UUID<br>
     * 将产生32位的UUID,并且全部转换为大写字母
     * @author lee
     * @date 2016年5月26日 下午10:41:56
     * @return UUID字符串
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        return uuid;
    }

    /**
     * 获取系统当前时间
     * @author lee
     * @date 2016年6月8日 下午11:56:45
     * @return
     */
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
