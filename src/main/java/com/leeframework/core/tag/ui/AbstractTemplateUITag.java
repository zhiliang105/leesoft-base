package com.leeframework.core.tag.ui;

import java.util.Map;

import javax.servlet.jsp.JspException;

import com.leeframework.core.tag.JspTagWriter;
import com.leeframework.core.tag.JspTemplateWriter;

/**
 * 内容基于模板生成的UI标签
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月3日 下午2:18:52
 */
public abstract class AbstractTemplateUITag extends AbstractUITag {
    private static final long serialVersionUID = 1L;

    private JspTemplateWriter jspTemplateWriter;

    @Override
    protected final JspTagWriter createJspTagWriter() {
        String name = getTemplateName();
        JspTemplateWriter jspTemplateWriter = new JspTemplateWriter(this.pageContext, name);
        return jspTemplateWriter;
    }

    @Override
    protected final void doStartTagInvoke(JspTagWriter jspTagWriter) throws JspException {
        jspTemplateWriter = (JspTemplateWriter) jspTagWriter;
        try {
            jspTemplateWriter.process(createRoot());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JspException(e.getMessage());
        }
    }

    /**
     * 子类实现,创建freemarker模板需要的数据内容
     * @datetime 2018年6月3日 下午2:40:44
     */
    protected abstract Map<String, Object> createRoot();

    /**
     * 子类中指定模板的名称
     * @datetime 2018年6月3日 下午2:22:47
     * @return
     */
    protected abstract String getTemplateName();

    @Override
    public void doFinally() {
        super.doFinally();
        this.jspTemplateWriter = null;
    }

}
