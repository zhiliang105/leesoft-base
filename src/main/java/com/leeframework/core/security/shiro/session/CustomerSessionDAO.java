package com.leeframework.core.security.shiro.session;

import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;

/**
 * 扩展 {@link org.apache.shiro.session.mgt.eis.SessionDAO}接口
 * @author 李志亮 (Lee) <279683131(@qq.com),18538086795@163.com>
 * @date Date:2016年6月8日 Time: 上午10:51:00
 * @version 1.0
 * @since version 1.0
 * @update
 */
public interface CustomerSessionDAO extends SessionDAO {

    /**
     * 获取活动会话
     * @author lee
     * @date 2016年6月8日 上午10:51:50
     * @param includeLeave 是否包括离线（最后访问时间大于3分钟为离线会话）
     * @return
     */
    public Collection<Session> getActiveSessions(boolean includeLeave);

    /**
     * 获取活动会话
     * @author lee
     * @date 2016年6月8日 上午10:52:38
     * @param includeLeave 是否包括离线（最后访问时间大于3分钟为离线会话）
     * @param principal 根据登录者对象获取活动会话
     * @param filterSession 不为空，则过滤掉（不包含）这个会话。
     * @return
     */
    public Collection<Session> getActiveSessions(boolean includeLeave, Object principal, Session filterSession);

}
