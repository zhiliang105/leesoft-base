package com.leeframework.core.tag.ui;

import javax.servlet.jsp.JspException;

import com.leeframework.core.tag.AbstractHtmlElementTag;
import com.leeframework.core.tag.JspTagWriter;

/**
 * UI标签基类
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月3日 下午2:28:33
 */
public abstract class AbstractUITag extends AbstractHtmlElementTag {
    private static final long serialVersionUID = 1L;

    @Override
    protected final int writeContentInStartTag(JspTagWriter jspTagWriter) throws JspException {
        try {
            this.doStartTagInvoke(jspTagWriter);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JspException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public final int doEndTag() throws JspException {
        try {
            this.doEndTagInvoke();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JspException(e.getMessage());
        }
        return EVAL_PAGE;
    }

    /**
     * 所有子类可以实现该方法用于实现在{@link #writeContentInStartTag()}中的操作<br>
     * {@link #writeContentInStartTag()}方法被{@link #doStartTagInternal()}方法调用<br>
     * 继而在 {@link #doStartTag()}方法中表达出具体实现
     * @datetime 2018年6月3日 下午2:22:19
     * @throws Exception
     */
    protected abstract void doStartTagInvoke(JspTagWriter jspTagWriter) throws JspException;

    /**
     * 被doEndTag方法调用的实际要实现的操作
     * @throws Exception 方法执行中发生的任何异常,将在doStartTag的方法中被调用的时候捕获
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag
     * @datetime 2018年6月3日 下午2:22:29
     */
    protected void doEndTagInvoke() throws JspException {
    }

}
