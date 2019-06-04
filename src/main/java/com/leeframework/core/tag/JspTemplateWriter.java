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
import java.util.Locale;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.springframework.util.StringUtils;

import com.leeframework.core.web.Globals;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 读取模板内容,然后写入到JspWriter输出到页面中
 * @author 李志亮 (Lee) <279683131(@qq.com),18538086795@163.com>
 * @date Date:2016年6月2日 Time: 下午1:24:09
 * @version 1.0
 * @since version 1.0
 * @update
 */
public class JspTemplateWriter extends JspTagWriter {

    /**
     * 模板的Class路径
     */
    private final static String PATHP_REFIX = "/com/leeframework/core/tag/ftl/";

    /**
     * Freemarker的主要配置对象,单例模式
     */
    private static Configuration configuration;

    private String name;// 模板的名称

    static {
        configuration = new Configuration();
        configuration.setClassForTemplateLoading(JspTemplateWriter.class, PATHP_REFIX);
        configuration.setLocale(Locale.CHINA);
        configuration.setDefaultEncoding(Globals.UTF8);
        configuration.setCacheStorage(new freemarker.cache.MruCacheStorage(20, Integer.MAX_VALUE));
    }

    public JspTemplateWriter(PageContext pageContext, String name) {
        super(pageContext);
        this.name = name;
    }

    /**
     * 设置模板的名称
     * @author lee
     * @date 2016年6月2日 下午4:02:19
     * @param name 模板的名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 读取模板内容,将数据直接通过Writer输出到页面
     * @author lee
     * @date 2016年6月2日 下午1:54:23
     * @param root 数据集合
     * @throws JspException 统一采用JspException收集异常信息
     */
    public void process(Map<String, Object> root) throws JspException {
        if (StringUtils.isEmpty(name)) {
            throw new JspException("The name of template is null, can not resolve it");
        }
        try {
            Writer writer = getIOWriter();
            Template tpl = configuration.getTemplate(name);
            tpl.process(root, writer);
        } catch (JspException e) {
            throw e;
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        } catch (TemplateException e) {
            throw new JspException(e.getMessage());
        }
    }
}
