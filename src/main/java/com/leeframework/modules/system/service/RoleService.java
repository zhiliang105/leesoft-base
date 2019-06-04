package com.leeframework.modules.system.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leeframework.common.service.BaseService;
import com.leeframework.modules.system.dao.RoleDao;
import com.leeframework.modules.system.entity.Role;

/**
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年5月23日 上午12:46:46
 */
@Service
@Transactional(readOnly = true)
public class RoleService extends BaseService<Role, RoleDao> {
}
