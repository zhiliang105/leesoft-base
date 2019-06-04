package com.leeframework.common.hibernate4.validate;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.leeframework.common.model.view.ViewMessage;

/**
 * 通用数据校验组件
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年5月28日 下午6:04:10
 */
@Aspect
@Component
public class BindingResultAop {

    /**
     * 获取的错误消息中的占位符{}无法获取正确值,该问题未解决
     * @datetime 2018年6月10日 下午11:10:23
     */
    @Around("execution(* com.leeframework.modules..*Controller.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        BindingResult bindingResult = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BindingResult) {
                bindingResult = (BindingResult) arg;
            }
        }
        if (bindingResult != null) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            if (errors.size() > 0) {
                ViewMessage vm = new ViewMessage(ViewMessage.CODE_VALIDAT, "数据校验失败");
                for (int i = 0; i < errors.size(); i++) {
                    FieldError error = errors.get(i);
                    vm.addAttribute(error.getField(), error.getDefaultMessage());
                }
                return vm;
            }
        }
        return joinPoint.proceed();
    }
}
