package com.leeframework.core.tag.ui;

import com.leeframework.common.utils.StringUtil;

/**
 * 页面布局标签
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月20日 下午10:17:47
 */
public class LayoutLeftTag extends LayoutTag {
    private static final long serialVersionUID = 1L;

    @Override
    protected String getStartHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"bt-layout bt-layout-left\">");
        sb.append("<div class=\"panel panel-default\">");

        String title = getTitle();
        if (StringUtil.isNotEmpty(title)) {
            sb.append("<div class=\"panel-heading\"> <h3 class=\"panel-title\"> " + title + " </h3> </div>");
        }

        sb.append("<div class=\"panel-body tree-fluid mCustomScrollbar\" data-mcs-theme=\"minimal-dark\">");

        return sb.toString();
    }

    @Override
    protected String getEndHtml() {
        String end = "</div>";
        end += "</div>";
        end += "</div>";
        return end;
    }

    @Override
    protected Class<?> getContainerClass() {
        return LayoutContainerTag.class;
    }
}
