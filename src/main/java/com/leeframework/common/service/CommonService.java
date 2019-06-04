package com.leeframework.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service基础业务逻辑
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年12月18日 下午7:52:07
 */
@Transactional(readOnly = true)
public abstract class CommonService {
    protected Logger log = LoggerFactory.getLogger(getClass());
}
