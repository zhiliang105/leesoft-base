package com.leeframework.core.security.shiro.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import com.leeframework.common.utils.DateUtil;
import com.leeframework.core.web.helper.RequestHelper;

/**
 * 自定义Shiro的SessionDAO
 * @author 李志亮 (Lee) <279683131(@qq.com),18538086795@163.com>
 * @date Date:2016年6月8日 Time: 上午12:39:39
 * @version 1.0
 * @since version 1.0
 * @update
 */
public class CustomerEnterpriseCacheSessionDAO extends EnterpriseCacheSessionDAO implements CustomerSessionDAO {

    private final static String REQ_ATTR_SESSION_KEY = "SESSION_";

    /**
     * Session处理离线状态的时间(单位:分钟)
     */
    private final static int SESSION_LEAVE_TIME = 10;

    @Override
    protected void doUpdate(Session session) {
        if (!isValid(session)) {
            return;
        }
        HttpServletRequest request = RequestHelper.getRequest();
        if (RequestHelper.isStaticFileRequest(request)) {
            return;
        }
        super.doUpdate(session);
    }

    @Override
    protected void doDelete(Session session) {
        if (!isValid(session)) {
            return;
        }
        super.doDelete(session);
    }

    @Override
    protected Serializable doCreate(Session session) {
        if (RequestHelper.isStaticFileRequest(RequestHelper.getRequest())) {
            return "";
        }
        return super.doCreate(session);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return super.doReadSession(sessionId);
    }

    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        HttpServletRequest request = RequestHelper.getRequest();

        Session s = null;
        if (request != null) {
            s = (Session) request.getAttribute(REQ_ATTR_SESSION_KEY + sessionId);
            if (s != null) {
                return s;
            }
        }
        Session session = super.readSession(sessionId);
        if (request != null && session != null) {
            request.setAttribute(REQ_ATTR_SESSION_KEY + sessionId, session);
        }

        return session;
    }

    @Override
    public Collection<Session> getActiveSessions(boolean includeLeave) {
        return getActiveSessions(includeLeave, null, null);
    }

    @Override
    public Collection<Session> getActiveSessions(boolean includeLeave, Object principal, Session filterSession) {
        Collection<Session> activeSessions = super.getActiveSessions();
        if (includeLeave && principal == null) {
            return activeSessions;
        }

        Set<Session> sessions = new HashSet<Session>();
        for (Session session : activeSessions) {
            boolean isActiveSession = false;

            // 不包括离线并符合最后访问时间小于等于3分钟条件。
            if (includeLeave || DateUtil.currentMinite(session.getLastAccessTime()) <= SESSION_LEAVE_TIME) {
                isActiveSession = true;
            }

            // 符合登陆者条件。
            if (principal != null) {
                PrincipalCollection pc = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                if (principal.toString().equals(pc != null ? pc.getPrimaryPrincipal().toString() : "")) {
                    isActiveSession = true;
                }
            }

            // 过滤掉的SESSION
            if (filterSession != null && filterSession.getId().equals(session.getId())) {
                isActiveSession = false;
            }
            if (isActiveSession) {
                sessions.add(session);
            }
        }

        return sessions;
    }

    /**
     * 验证会话是否过期/停止
     * @author lee
     * @date 2016年6月9日 上午1:55:35
     * @param session
     * @return
     */
    private boolean isValid(Session session) {
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            return false;
        }
        return true;
    }
}
