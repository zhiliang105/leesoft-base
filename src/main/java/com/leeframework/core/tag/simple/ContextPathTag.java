package com.leeframework.core.tag.simple;

/**
 * 用户输出contextPath
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年12月2日 下午4:04:30
 */
public class ContextPathTag extends AbstractSimpleTag {
    private static final long serialVersionUID = 1L;

    @Override
    public String getOutput() {
        return getContextPath();
    }

}
