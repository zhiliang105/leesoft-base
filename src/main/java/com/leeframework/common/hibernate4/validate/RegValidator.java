package com.leeframework.common.hibernate4.validate;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.leeframework.common.utils.StringUtil;

/**
 * 根据正则表达式校验
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月11日 上午12:32:37
 */
public abstract class RegValidator<A extends Annotation> implements ConstraintValidator<A, String> {

    @Override
    public final void initialize(A constraintAnnotation) {

    }

    @Override
    public final boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtil.isEmpty(value)) {
            return true;
        }
        return mactchs(value);
    }

    public abstract boolean mactchs(String value);

}
