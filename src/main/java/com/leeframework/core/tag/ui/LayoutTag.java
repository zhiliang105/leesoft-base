package com.leeframework.core.tag.ui;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import com.leeframework.common.utils.StringUtil;
import com.leeframework.core.tag.JspTagWriter;

/**
 * 页面布局标签
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月20日 下午10:45:42
 */
public abstract class LayoutTag extends AbstractUITag {
    private static final long serialVersionUID = 1L;

    private JspTagWriter jspTagWriter;

    @Override
    protected final void doStartTagInvoke(JspTagWriter jspTagWriter) throws JspException {
        this.jspTagWriter = jspTagWriter;

        Class<?> containerClass = getContainerClass();
        String startHtml = getStartHtml();

        if (containerClass == null) {
            this.jspTagWriter.write(startHtml);
        } else {
            Tag parentTag = getParent();
            if (parentTag == null || !parentTag.getClass().equals(containerClass)) {
                throw new JspException("The area layout must be in the container");
            } else {
                this.jspTagWriter.write(startHtml);
            }
        }
    }

    @Override
    protected final void doEndTagInvoke() throws JspException {
        String end = getEndHtml();
        if (StringUtil.isNotEmpty(end)) {
            this.jspTagWriter.write(end);
        }
    }

    protected abstract String getStartHtml();

    protected abstract String getEndHtml();

    /**
     * Layout布局分为容器和区域,子类表明是否为容器
     * @datetime 2018年6月20日 下午10:48:21
     */
    protected abstract Class<?> getContainerClass();
}
