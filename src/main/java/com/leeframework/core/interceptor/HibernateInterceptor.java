package com.leeframework.core.interceptor;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.leeframework.common.hibernate4.entity.BaseEntity;
import com.leeframework.common.hibernate4.entity.IdEntity;
import com.leeframework.core.security.shiro.ShiroUtil;
import com.leeframework.modules.system.entity.User;

/**
 * hibernate拦截器,分别处理通用的实体记录创建时间(createTime)和更新时间(updateTime)
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月25日 下午8:46:22
 */
public class HibernateInterceptor extends EmptyInterceptor {
    private static final long serialVersionUID = 799213033818397971L;

    /**
     * 实体更新的时候更新记录的updateTime,updateId,updateName(如果有)
     */
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if (entity instanceof IdEntity) {
            boolean b = false;
            for (int i = 0, length = propertyNames.length; i < length; i++) {
                if (propertyNames[i].equalsIgnoreCase(BaseEntity.UPDATE_TIME)) {
                    Date updateTime = new Date();
                    currentState[i] = updateTime;
                    b = true;
                } else {
                    User user = ShiroUtil.getCurrentUser();
                    if (user != null) {
                        if (propertyNames[i].equalsIgnoreCase(BaseEntity.UPDATE_ID)) {
                            currentState[i] = user.getId();
                            b = true;
                        } else if (propertyNames[i].equalsIgnoreCase(BaseEntity.UPDATE_NAME)) {
                            currentState[i] = user.getRealName();
                            b = true;
                        }
                    }
                }

            }
            return b;
        }
        return false;
    }

    /**
     * 实体保存的时候更新记录的createTime,createId,createName(如果有)
     */
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof IdEntity) {
            boolean b = false;
            for (int i = 0, length = propertyNames.length; i < length; i++) {
                String propertyName = propertyNames[i];
                if (propertyName.equalsIgnoreCase(BaseEntity.CREATE_TIME)) {
                    Date createTime = new Date();
                    state[i] = createTime;
                    b = true;
                } else {
                    User user = ShiroUtil.getCurrentUser();
                    if (user != null) {
                        if (propertyNames[i].equalsIgnoreCase(BaseEntity.CREATE_ID)) {
                            state[i] = user.getId();
                            b = true;

                        } else if (propertyNames[i].equalsIgnoreCase(BaseEntity.CREATE_NAME)) {
                            state[i] = user.getRealName();
                            b = true;
                        }
                    }
                }

            }
            return b;
        }
        return false;
    }
}
