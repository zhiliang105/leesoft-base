package com.leeframework.core.tag.simple;

import java.util.Locale;

/**
 * 用户输出contextPath
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年12月2日 下午4:04:30
 */
public class LocalTag extends AbstractSimpleTag {
    private static final long serialVersionUID = 1L;

    @Override
    public String getOutput() {
        Locale local = getLocale();
        if (local != null) {
            return local.toString();
        } else {
            return "zh_CN";
        }
    }

}
