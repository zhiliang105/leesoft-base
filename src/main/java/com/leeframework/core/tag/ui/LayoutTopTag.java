package com.leeframework.core.tag.ui;

/**
 * 页面布局标签
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月20日 下午10:17:47
 */
public class LayoutTopTag extends LayoutTag {
    private static final long serialVersionUID = 1L;

    @Override
    protected String getStartHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"bt-layout bt-layout-top\">");
        sb.append("<div class=\"layui-fluid form-fluid\">");
        return sb.toString();
    }

    @Override
    protected String getEndHtml() {
        String end = "</div>";
        end += "</div>";
        return end;
    }

    @Override
    protected Class<?> getContainerClass() {
        return LayoutContainerTag.class;
    }
}
