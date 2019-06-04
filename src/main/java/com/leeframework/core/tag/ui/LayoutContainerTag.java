package com.leeframework.core.tag.ui;

/**
 * 页面布局标签-容器
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月20日 下午10:17:47
 */
public class LayoutContainerTag extends LayoutTag {
    private static final long serialVersionUID = 1L;


    @Override
    protected String getStartHtml() {
        return "<div class=\"bt-container\">";
    }

    @Override
    protected String getEndHtml() {
        return "</div>";
    }

    @Override
    protected Class<?> getContainerClass() {
        return null;
    }
}
