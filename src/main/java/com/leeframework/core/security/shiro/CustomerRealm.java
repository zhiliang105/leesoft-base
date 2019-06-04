package com.leeframework.core.security.shiro;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.kaptcha.Constants;
import com.leeframework.core.exception.shiro.CaptchaException;
import com.leeframework.core.web.Globals;
import com.leeframework.modules.system.entity.User;
import com.leeframework.modules.system.service.UserService;

/**
 * 自定义Realm,对数据源进行配置
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月30日 上午10:23:43
 */
public class CustomerRealm extends AuthorizingRealm {
    private final static Logger log = LoggerFactory.getLogger(CustomerRealm.class);

    @Resource
    private UserService sysUserService;

    @Override
    public String getName() {
        return "CustomerRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if (token instanceof UsernamePasswordToken) {
            return true;
        } else {
            log.warn("Does not support the token: [{}],should be [{}]", token, UsernamePasswordToken.class);
            return false;
        }
    }

    /**
     * 认证方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Session session = ShiroUtil.getSubject().getSession();

        CustomerUsernamePasswordToken cToken = (CustomerUsernamePasswordToken) token;

        // 验证码判断
        String validCode = cToken.getCaptcha();
        String validCodeInSession = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (validCodeInSession == null || validCode == null || !validCodeInSession.trim().toLowerCase().equals(validCode.trim().toLowerCase())) {
            throw new CaptchaException();
        }

        String userName = (String) token.getPrincipal();
        User user = sysUserService.findByUsername(userName);
        if (user != null) {

            // 判断用户是否被禁用
            if (user.getEnable() == null || !user.getEnable()) {
                throw new DisabledAccountException();
            }

            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(),
                    ByteSource.Util.bytes(userName), getName());

            // 将登录用户存储到session中
            // 注意：在rememberMe的时候,重新进入系统首页,session中的数据会丢失,所以需要重新设置
            // 在ShiroUtil.getCurrentUser()方法中统一处理
            session.setAttribute(Globals.USER_SESSION, user);
            return simpleAuthenticationInfo;
        }

        return null;
    }

    /**
     * 授权方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        return simpleAuthorizationInfo;
    }

    /**
     * 清除所有登录帐户有关的缓存数据
     * @author lee
     * @date 2016年5月25日 下午4:29:13
     */
    public void clearCached() {

        // 此方法获取的principals中只有当前登录的用户,无法获取全部的用户认证信息,原因未知
        // PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        // super.clearCache(principals);

        Cache<Object, AuthorizationInfo> authorizationCache = getAuthorizationCache();
        if (authorizationCache != null) {
            authorizationCache.clear();
        }
    }

}
