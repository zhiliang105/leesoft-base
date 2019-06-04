package com.leeframework.common.hibernate4.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 自定义验证-身份证号
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月10日 下午11:59:04
 */
@Constraint(validatedBy = IdCardValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IdCard {
    String message() default "{com.leeframework.validator.IdCard.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
