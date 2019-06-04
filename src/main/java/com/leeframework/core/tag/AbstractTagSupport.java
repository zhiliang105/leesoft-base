package com.leeframework.core.tag;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 自定义标签抽象支持类,基于springmvc环境
 * @author 李志亮 (Lee) <279683131(@qq.com),zhiliang.li@wirelessandon.cn>
 * @date Date:2016年4月27日 Time: 下午11:04:31
 * @version 1.0
 * @since version 1.0
 * @update 2016-06-02 by lee重构
 */
public abstract class AbstractTagSupport extends RequestContextAwareTag {
    private static final long serialVersionUID = 1L;

    /**
     * 创建一个标签内容输出对象
     * @author lee
     * @date 2016年6月2日 下午2:54:00
     * @return {@link JspTagWriter}
     */
    protected JspTagWriter createJspTagWriter() {
        return new JspTagWriter(this.pageContext);
    }

    /**
     * 获取ContextPath
     * @date 2016年4月27日 下午10:09:06
     */
    protected String getContextPath() {
        return getRequestContext().getContextPath();
    }

    /**
     * 获取国际化资源信息
     * @date 2016年4月27日 下午9:57:07
     * @param key i18n文件中配置的key
     */
    protected String getI18NMessage(String key) {
        String msg = getRequestContext().getMessage(key);
        if (msg == null || msg.equals("null")) {
            return "";
        }
        return msg;
    }

    /**
     * 获取Locale
     * @see Locale#getLanguage()
     * @see Locale#getCountry()
     */
    protected Locale getLocale() {
        return getRequestContext().getLocale();
    }

    /**
     * 获取HttpServletRequest
     * @date 2016年4月27日 下午9:58:54
     */
    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) pageContext.getRequest();
    }

    /**
     * 获取HttpSession,实际为shiro的session
     * @date 2016年4月27日 下午9:59:53
     */
    protected HttpSession getSession() {
        HttpSession session = getRequest().getSession();
        return session;
    }

}
