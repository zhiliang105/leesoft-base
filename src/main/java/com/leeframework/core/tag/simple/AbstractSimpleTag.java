package com.leeframework.core.tag.simple;

import javax.servlet.jsp.JspException;

import com.leeframework.core.tag.AbstractTagSupport;
import com.leeframework.core.tag.JspTagWriter;

/**
 * 简易标签支持类,只用于输出简单的信息
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年12月2日 下午4:04:07
 */
public abstract class AbstractSimpleTag extends AbstractTagSupport {
    private static final long serialVersionUID = 1L;

    @Override
    protected final int doStartTagInternal() throws JspException {
        JspTagWriter out = createJspTagWriter();
        try {
            out.write(getOutput());
        } catch (JspException e) {
            log.error(e.getMessage());
            throw new JspException(e.getMessage());
        }
        return EVAL_PAGE;
    }

    /**
     * 获取输出的信息
     * @author lee
     * @date 2016年6月2日 上午11:08:46
     * @return 返回输出的信息字符串
     */
    public abstract String getOutput();
}
