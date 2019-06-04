package com.leeframework.common.utils.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * sysConfig属性文件属性操作工具类
 * @author 李志亮 (Lee) <279683131(@qq.com),zhiliang.li@wirelessandon.cn>
 * @date Date:2016年5月16日 Time: 上午9:37:17
 * @version 1.0
 * @since version 1.0
 * @update
 */
public class SysConfigProperty extends PropertiesSupport {

    /**
     * 获取所有url中支持的前缀<br>
     * 返回map, 例如： path.admin = /admin
     * @datetime 2018年6月11日 下午5:49:45
     */
    public static Map<String, String> getPathMap() {
        Properties properties = LOADER.getProperties();
        Set<String> names = properties.stringPropertyNames();
        Map<String, String> map = new HashMap<String, String>();
        for (String name : names) {
            if (name != null && name.startsWith("path")) {
                String value = LOADER.getProperty(name);
                map.put(name, value);
            }
        }
        return map;
    }

    public static boolean getDepDebug() {
        return LOADER.getBoolean("dep.debug");
    }

    public static String getDepPassword() {
        return LOADER.getProperty("dep.passwork");
    }

    public static String getDepUserName() {
        return LOADER.getProperty("dep.username");
    }

    /**
     * 获取shiro定义的MD5散列的次数,主要用户用户的密码加密
     * @datetime 2017年12月3日 下午2:37:10
     */
    public static int getMD5HashIterations() {
        return LOADER.getInteger("shiro.auth.hashIterations");
    }

    /**
     * 获取静态资源目录
     * @datetime 2017年12月9日 下午4:46:03
     */
    public static String getStaticDir() {
        String dir = LOADER.getProperty("web.staticDir");
        if (dir == null || dir.trim().equals("")) {
            dir = "/static";
        }
        return dir;
    }

    /**
     * 获取Web端访问的静态文件的后缀名集合
     * @author lee
     * @date 2016年6月8日 上午12:57:37
     * @return 静态文件后缀名集合<Code>ArrayList<Code>对象
     */
    public static List<String> getStaticFileSuffix() {
        List<String> list = new ArrayList<String>();
        String str = LOADER.getProperty("web.staticFile");
        if (str != null && !"".equals(str.trim())) {
            String[] strs = str.split(",");
            list = Arrays.asList(strs);
        }
        return list;
    }

    /**
     * 判断是否要检测浏览器版本(PC端控制)
     * @datetime 2017年12月9日 下午4:14:21
     */
    public static boolean isCheckeBrowserVersion() {
        return LOADER.getBoolean("web.browser.check");
    }

}
