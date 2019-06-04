package com.leeframework.core.tag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;

/**
 * html元素基类,提供了一组HTML常见的属性元素
 * @author 李志亮 (Lee) <279683131(@qq.com),18538086795@163.com>
 * @date Date:2016年6月2日 Time: 下午2:09:33
 * @version 1.0
 * @since version 1.0
 * @update
 */
public abstract class AbstractHtmlElementTag extends AbstractTagSupport implements DynamicAttributes {
    private static final long serialVersionUID = 1L;

    public static final String ID = "id";
    public static final String CLASS = "class";
    public static final String STYLE = "style";
    public static final String TITLE = "title";
    public static final String ONCLICK = "onclick";
    public static final String ONDBLCLICK = "ondblclick";
    public static final String ONKEYPRESS = "onkeypress";
    public static final String ONKEYUP = "onkeyup";
    public static final String ONKEYDOWN = "onkeydown";

    private String id;
    private String cssClass;
    private String style;
    private String title;
    private String onclick;
    private String ondblclick;
    private String onkeypress;
    private String onkeyup;
    private String onkeydown;

    private Map<String, Object> dynamicAttributes;

    @Override
    public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
        if (this.dynamicAttributes == null) {
            this.dynamicAttributes = new HashMap<String, Object>();
        }
        if (!isValidDynamicAttribute(localName, value)) {
            throw new IllegalArgumentException("Attribute " + localName + "=\"" + value + "\" is not allowed");
        }
        dynamicAttributes.put(localName, value);
    }

    /**
     * 设定的动态属性是否有效
     * @author lee
     * @date 2016年6月2日 下午2:30:29
     * @param localName 属性名称
     * @param value 属性值
     * @return
     */
    protected boolean isValidDynamicAttribute(String localName, Object value) {
        return true;
    }

    @Override
    protected final int doStartTagInternal() throws JspException {
        JspTagWriter jspTagWriter = createJspTagWriter();
        return writeContentInStartTag(jspTagWriter);
    }

    /**
     * 所有子类必须实现该方法用于输出标签内容,该方法实则被{@link #doStartTagInternal()}方法调用<br>
     * 继而在 {@link #doStartTag()}方法中表达出具体实现
     * @author lee
     * @date 2016年6月2日 下午3:06:29
     * @param jspTagWriter {@link #JspTagWriter}
     * @return 与{@link javax.servlet.jsp.tagext.Tag#doStartTag()}方法中的返回值一致
     * @throws JspException
     */
    protected abstract int writeContentInStartTag(JspTagWriter jspTagWriter) throws JspException;

    @Override
    public void doFinally() {
        super.doFinally();
        this.dynamicAttributes = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOndblclick() {
        return ondblclick;
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public String getOnkeypress() {
        return onkeypress;
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public String getOnkeyup() {
        return onkeyup;
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public String getOnkeydown() {
        return onkeydown;
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public Map<String, Object> getDynamicAttributes() {
        return dynamicAttributes;
    }

    public void setDynamicAttributes(Map<String, Object> dynamicAttributes) {
        this.dynamicAttributes = dynamicAttributes;
    }

}
