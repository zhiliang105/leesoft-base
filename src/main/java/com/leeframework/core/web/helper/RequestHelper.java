package com.leeframework.core.web.helper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

import com.leeframework.common.utils.properties.SysConfigProperty;

/**
 * HttpServletRequest访问请求辅助类
 * @author 李志亮 (Lee) <279683131(@qq.com),zhiliang.li@wirelessandon.cn>
 * @date Date:2016年4月21日 Time: 下午3:34:59
 * @version 1.0
 * @since version 1.0
 * @update
 */
public final class RequestHelper {
    private static final Logger log = LoggerFactory.getLogger(RequestHelper.class);

    /**
     * 本机默认IP地址
     */
    public static final String LOCAL_IP = "127.0.0.1";

    private RequestHelper() {
    }

    /**
     * 获取当前的请求对象
     * @author lee
     * @date 2016年6月8日 上午12:51:02
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        } catch (Exception e) {
            log.error("Can not get the HttpServletRequest from current request");
            return null;
        }
    }

    /**
     * 获取系统访问的基础路径
     * @author lee
     * @date 2016年4月21日 下午3:36:15
     * @param request HttpServletRequest
     * @return 根路径：例如:http://127.0.0.1:8080/minimes
     */
    public static String getBasePath(HttpServletRequest request) {
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        return basePath;
    }

    /**
     * 获取网络请求端的IP地址
     * @author lee
     * @date 2016年4月21日 下午3:37:57
     * @param request HttpServletRequest
     * @return
     */
    public static String getIPAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = LOCAL_IP;
        }
        return ip;
    }

    /**
     * 获取springMVC的RequestContext上下文
     * @author lee
     * @date 2016年4月27日 下午11:36:52
     * @param request HttpServletRequest
     * @return
     */
    public static RequestContext getMVCRequestContext(HttpServletRequest request) {
        return new RequestContext(request);
    }

    /**
     * 获取ServletContext上下文
     * @author lee
     * @date 2016年4月22日 下午11:45:12
     * @param request HttpServletRequest
     * @return
     */
    public static ServletContext getServletContext(HttpServletRequest request) {
        return request.getSession().getServletContext();
    }

    /**
     * 判断请求是否为AJAX请求
     * @author lee
     * @date 2016年4月21日 下午3:35:37
     * @param request HttpServletRequest
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header))
            return true;
        else
            return false;
    }

    /**
     * 判断该次访问是否为访问静态文件<br>
     * 静态文件在系统sysConfig属性文件中定义
     * @author lee
     * @date 2016年6月8日 上午1:04:04
     * @param request HttpServletRequest请求对象
     * @return 如果request为空,默认不是静态访问,否则进行访问的后缀对比判定
     */
    public static boolean isStaticFileRequest(HttpServletRequest request) {
        if (request != null) {
            String uri = request.getRequestURI();
            String staticDir = SysConfigProperty.getStaticDir() + "/";
            return uri.indexOf(staticDir) >= 0;
        }
        return false;
    }

}
