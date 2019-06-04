package com.leeframework.modules.system.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leeframework.common.service.BaseService;
import com.leeframework.common.utils.properties.SysConfigProperty;
import com.leeframework.modules.system.dao.UserDao;
import com.leeframework.modules.system.entity.User;

/**
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月21日 下午4:16:21
 */
@Service
@Transactional(readOnly = true)
public class UserService extends BaseService<User, UserDao> {

    public User findByUsername(String userName) {

        // 开发调试模式
        boolean isDepDebug = SysConfigProperty.getDepDebug();
        if (isDepDebug) {
            return User.createSuperadmin();
        }

        User user = findUniqueByProperty("userName", userName);
        return user;

    }
}
