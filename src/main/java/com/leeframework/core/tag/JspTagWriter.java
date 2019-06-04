/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.leeframework.core.tag;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * JSP标签用户输出html的工具类
 * @author 李志亮 (Lee) <279683131(@qq.com),18538086795@163.com>
 * @date Date:2016年6月2日 Time: 上午11:40:09
 * @version 1.0
 * @since version 1.0
 * @update
 * @see {@link Writer}
 */
public class JspTagWriter {

    private final SafeWriter writer;

    public JspTagWriter(PageContext pageContext) {
        Assert.notNull(pageContext, "PageContext must not be null");
        this.writer = new SafeWriter(pageContext);
    }

    /**
     * 直接输出内容
     * @author lee
     * @date 2016年6月2日 上午11:51:38
     * @param str 需要输出的内容
     * @throws JspException
     */
    public void write(String str) throws JspException {
        if (StringUtils.isEmpty(str)) {
            str = "";
        }
        this.writer.append(str);
    }

    /**
     * 返回原始的Writer
     * @author lee
     * @date 2016年6月2日 下午1:50:53
     * @return {@link java.io.Writer} 对象
     */
    protected Writer getIOWriter() throws JspException {
        return this.writer.getWriterToUse();
    }

    /**
     * Simple {@link Writer} wrapper that wraps all {@link IOException IOExceptions} in {@link JspException JspExceptions}.
     */
    private static class SafeWriter {

        private PageContext pageContext;

        public SafeWriter(PageContext pageContext) {
            this.pageContext = pageContext;
        }

        public SafeWriter append(String value) throws JspException {
            try {
                getWriterToUse().write(String.valueOf(value));
                return this;
            } catch (IOException ex) {
                throw new JspException("Unable to write to JspWriter", ex);
            }
        }

        public Writer getWriterToUse() throws JspException {
            if (pageContext == null) {
                throw new JspException("Can not create the write , pageContext is null");
            }
            return this.pageContext.getOut();
        }
    }

}
