package com.leeframework.core.security.shiro;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import com.leeframework.common.utils.SystemUtil;

/**
 * 系统自定义Shiro的SessionId生成器<br>
 * {@link SessionIdGenerator}
 * @author 李志亮 (Lee) <279683131(@qq.com),zhiliang.li@wirelessandon.cn>
 * @date Date:2016年5月26日 Time: 下午10:46:26
 * @version 1.0
 * @since version 1.0
 * @update
 */
public class ShiroSessionIdGenerator implements SessionIdGenerator {

    /**
     * 返回系统自定义的sessionid
     */
    public Serializable generateId(Session session) {
        return SystemUtil.getUUID();
    }

}
