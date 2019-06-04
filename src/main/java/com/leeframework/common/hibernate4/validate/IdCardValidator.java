package com.leeframework.common.hibernate4.validate;

import com.leeframework.common.utils.RegExUtil;

/**
 * 身份证号码验证
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月11日 上午12:23:05
 */
public class IdCardValidator extends RegValidator<IdCard> {

    @Override
    public boolean mactchs(String value) {
        return RegExUtil.isIdCard(value);
    }

}
