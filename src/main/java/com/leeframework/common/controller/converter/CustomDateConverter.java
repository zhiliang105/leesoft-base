package com.leeframework.common.controller.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import com.leeframework.common.utils.StringUtil;

/**
 * 自定义日期类型转换器<br>
 * 系统全部使用yyyy-MM-dd类型的日期格式
 * @author 李志亮 (Lee) <279683131(@qq.com),zhiliang.li@wirelessandon.cn>
 * @date Date:2016年5月19日 Time: 下午4:14:39
 * @version 1.0
 * @since version 1.0
 * @update
 */
public class CustomDateConverter implements Converter<String, Date> {
    private static Logger log = LoggerFactory.getLogger(CustomDateConverter.class);

    /**
     * 系统支持的转换格式
     */
    private static final String[] FORMATTER = { "yyyy-MM-dd HH:mm:ss", //
            "yyyy/MM/dd HH:mm:ss", //
            "yyyy-MM-dd HH:mm", //
            "yyyy/MM/dd HH:mm", //
            "yyyy-MM-dd", //
            "yyyy/MM/dd" };

    @Override
    public Date convert(String source) {
        if (StringUtil.isNotEmpty(source)) {
            for (int i = 0; i < FORMATTER.length; i++) {
                String formatter = FORMATTER[i];
                SimpleDateFormat format = new SimpleDateFormat(formatter);
                try {
                    return format.parse(source);
                } catch (ParseException e) {
                }
            }
            try {
                return new Date(new Long(source).longValue());
            } catch (Exception e) {
                log.error("Failed to converter string to date:{}", source);
            }
        }
        return null;
    }

}
