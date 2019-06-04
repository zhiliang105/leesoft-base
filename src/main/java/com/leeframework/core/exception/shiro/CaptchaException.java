package com.leeframework.core.exception.shiro;

import org.apache.shiro.authc.AccountException;

/**
 * 验证码验证异常
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年12月3日 下午11:29:02
 */
public class CaptchaException extends AccountException {
    private static final long serialVersionUID = 1L;

    public CaptchaException() {
        super();
    }

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(Throwable cause) {
        super(cause);
    }

    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }
}
