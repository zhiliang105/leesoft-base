package com.leeframework.core.tag.ui;

/**
 * 页面布局标签
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月20日 下午10:17:47
 */
public class LayoutCenterTag extends LayoutTag {
    private static final long serialVersionUID = 1L;

    @Override
    protected String getStartHtml() {
        return "<div class=\"bt-layout bt-layout-center\">";
    }

    @Override
    protected String getEndHtml() {
        return "</div>";
    }

    @Override
    protected Class<?> getContainerClass() {
        return LayoutContainerTag.class;
    }
}
