package com.leeframework.core.tag.ui;

import java.util.Arrays;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import com.leeframework.common.model.BeanProperty;
import com.leeframework.common.model.DynamicBean;
import com.leeframework.common.utils.clazz.BeanUtil;
import com.leeframework.core.tag.JspTagWriter;

/**
 * Bootstrap-table列自定义标签
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月3日 下午3:58:19
 */
public class BootstrapTableColumnTag extends AbstractUITag {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doStartTagInvoke(JspTagWriter jspTagWriter) throws JspException {

    }

    @Override
    protected void doEndTagInvoke() throws JspException {
        Tag parentTag = getParent();
        if (parentTag == null) {
            log.warn("Custom tag {} cannot be used alone, it must be as a child tags", getClass().getName());
        } else {
            if (!(parentTag instanceof BootstrapTableTag)) {
                log.warn("Custom tag {} must be {} child tags, otherwise cannot resolve", getClass().getName(), BootstrapTableTag.class.getName());
            } else {
                BootstrapTableTag parent = (BootstrapTableTag) getParent();
                DynamicBean bean = parseToColumnObject();
                parent.setColumn(bean);
            }
        }
    }

    /**
     * 将参数解析成动态的列对象
     * @datetime 2018年6月3日 下午4:01:44
     */
    private DynamicBean parseToColumnObject() throws JspException {
        List<BeanProperty> properties = BeanUtil.getBeanPropertiesIncludeSuper(this, Arrays.asList("title", "width"));
        return new DynamicBean(properties);
    }

}
