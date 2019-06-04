package com.leeframework.core.security.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leeframework.core.web.Globals;
import com.leeframework.modules.system.entity.User;

/**
 * shiro相关操作工具类
 * @author 李志亮 (Lee) <279683131(@qq.com),zhiliang.li@wirelessandon.cn>
 * @date Date:2016年5月28日 Time: 上午11:16:58
 * @version 1.0
 * @since version 1.0
 * @update
 */
public abstract class ShiroUtil {
    private final static Logger log = LoggerFactory.getLogger(ShiroUtil.class);

    /**
     * 获取Shiro的Subject对象
     * @author lee
     * @date 2016年5月30日 下午11:26:16
     * @return Subject
     */
    public static Subject getSubject() {
        try {
            SecurityUtils.getSecurityManager();
        } catch (UnavailableSecurityManagerException e) {
            String msg = "No SecurityManager accessible to the calling code, either bound to the " + ThreadContext.class.getName()
                    + " or as a vm static singleton.  This is an invalid application " + "configuration.";
            log.warn(msg);
            return null;
        }
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录认证的用户的统一入口
     * <p>
     * 系统支持shiro的rememberMe,但是在rememberMe之后如果重新进入系统主页,session中的user会丢失,<br>
     * 所以方法中获取user首先从session中获取,如果获取为空,则通过subject.getPrincipal()获取user然后重新设置到session中
     * </p>
     * @author lee
     * @date 2016年5月30日 下午11:27:05
     * @return 返回当前认证的用户信息
     */
    public static User getCurrentUser() {
        Subject subject = getSubject();
        if (subject != null) {
            Session session = subject.getSession();
            User user = (User) session.getAttribute(Globals.USER_SESSION);
            if (user == null) {
                user = (User) subject.getPrincipal();
                session.setAttribute(Globals.USER_SESSION, user);
            }
            return user;
        }
        return null;
    }
}
