package com.leeframework.core.security.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 用户名/密码/验证码令牌
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年12月3日 下午11:16:18
 */
public class CustomerUsernamePasswordToken extends UsernamePasswordToken {
    private static final long serialVersionUID = 1L;

    private String captcha;

    public CustomerUsernamePasswordToken() {
        super();
    }

    public CustomerUsernamePasswordToken(String userName, String password, String captcha) {
        super(userName, password);
        this.captcha = captcha;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
