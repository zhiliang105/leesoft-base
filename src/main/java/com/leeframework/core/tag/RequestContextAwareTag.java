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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.JspAwareRequestContext;
import org.springframework.web.servlet.support.RequestContext;

/**
 * 所有标签类的顶级父类,其中包含了 {@link RequestContext}
 * @author 李志亮 (Lee) <279683131(@qq.com),18538086795@163.com>
 * @date Date:2016年6月2日 Time: 上午9:51:06
 * @see org.springframework.web.servlet.tags.RequestContextAwareTag
 */
public abstract class RequestContextAwareTag extends TagSupport implements TryCatchFinally {
    private static final long serialVersionUID = 1L;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 用于表示{@link javax.servlet.jsp.PageContext} 中Page级别{@link RequestContext}实例的属性名称.
     */
    public static final String REQUEST_CONTEXT_PAGE_ATTRIBUTE = "request_context_page_attribute";

    /**
     * @see org.springframework.web.servlet.support.RequestContext
     */
    private RequestContext requestContext;

    /**
     * 包装该方法,首先创建当前的RequestContext,然后委托{@link #doStartTagInternal()}方法实现具体的操作
     * @see org.springframework.web.servlet.support.JspAwareRequestContext
     */
    @Override
    public final int doStartTag() throws JspException {
        try {
            this.requestContext = (RequestContext) this.pageContext.getAttribute(REQUEST_CONTEXT_PAGE_ATTRIBUTE);
            if (this.requestContext == null) {
                this.requestContext = new JspAwareRequestContext(this.pageContext);
                this.pageContext.setAttribute(REQUEST_CONTEXT_PAGE_ATTRIBUTE, this.requestContext);
            }
            return doStartTagInternal();
        } catch (JspException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new JspTagException(ex.getMessage());
        }
    }

    /**
     * 返回当前的RequestContext,该方法不可覆盖
     * @author lee
     * @date 2016年6月2日 上午10:53:49
     * @return {@link org.springframework.web.servlet.support.RequestContext}实例
     */
    protected final RequestContext getRequestContext() {
        return this.requestContext;
    }

    /**
     * 被doStartTag方法调用的实际要实现的操作
     * @author lee
     * @date 2016年6月2日 上午10:55:10
     * @return 和 TagSupport.doStartTag返回值一样
     * @throws Exception 方法执行中发生的任何异常,将在doStartTag的方法中被调用的时候捕获
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag
     */
    protected abstract int doStartTagInternal() throws JspException;

    @Override
    public void doCatch(Throwable throwable) throws Throwable {
        throw throwable;
    }

    @Override
    public void doFinally() {
        this.requestContext = null;
    }

}
