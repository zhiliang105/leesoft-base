package com.leeframework.common.controller.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * 空字符串转null处理
 * @author 李志亮 (Lee) <279683131(@qq.com),zhiliang.li@wirelessandon.cn>
 * @date Date:2016年5月25日 Time: 下午3:38:47
 * @version 1.0
 * @since version 1.0
 * @update
 */
public class CustomStringConverter implements Converter<String, String> {
    private static Logger log = LoggerFactory.getLogger(CustomStringConverter.class);

    @Override
    public String convert(String source) {
        try {
            if (source != null) {
                String str = source.trim();
                if (str.equals("")) {
                    return null;
                }
                return str;
            }
        } catch (Exception e) {
        }
        log.error("Failed to converter string to string:{}", source);
        return null;
    }

}
