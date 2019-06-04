package com.leeframework.common.hibernate4.validate;

import com.leeframework.common.utils.RegExUtil;

/**
 * 手机号码验证
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月11日 上午12:09:02
 */
public class PhoneValidator extends RegValidator<Phone> {

    @Override
    public boolean mactchs(String value) {
        return RegExUtil.isPhone(value);
    }

}
