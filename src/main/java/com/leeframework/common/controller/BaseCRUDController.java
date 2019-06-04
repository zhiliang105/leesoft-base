package com.leeframework.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leeframework.common.hibernate4.dao.BaseDao;
import com.leeframework.common.hibernate4.entity.IdEntity;
import com.leeframework.common.model.view.ViewMessage;
import com.leeframework.common.service.BaseService;
import com.leeframework.common.utils.StringUtil;
import com.leeframework.common.utils.clazz.GenericsUtil;

/**
 * 基础增删改查业务控制器 <br>
 * 问题：无法通过shiro做权限控制
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月11日 上午1:07:07
 */
public abstract class BaseCRUDController<E extends IdEntity, Service extends BaseService<E, BaseDao<E>>> extends BaseController {

    protected Service baseService;
    private Class<?> entityClass;
    private String entityName;
    private String requestMappingPre;

    public BaseCRUDController() {
        this.entityClass = GenericsUtil.getSuperClassGenricType(this.getClass());
        this.entityName = StringUtil.toLowerCaseFirstOne(entityClass.getSimpleName());
        this.requestMappingPre = getRequestMappgingPre();
    }

    /**
     * 跳转到列表页面
     * @datetime 2018年6月11日 下午6:33:32
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    protected String toList(Model model) {
        preToList(model);
        return requestMappingPre + entityName + "List";
    }

    /**
     * 批量删除功能
     * @datetime 2018年6月11日 下午6:17:22
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    protected ViewMessage delete(String[] ids) {
        ViewMessage vm = preDelete(ids);
        if (vm == null) {
            this.baseService.delete(ids);
            return sucDelete();
        }
        return vm;
    }

    /**
     * 执行删除调用,由子类实现,如果返回null,执行删除,否则由子类实现业务逻辑控制
     * @datetime 2018年6月11日 下午6:20:03
     */
    protected ViewMessage preDelete(String[] ids) {
        return null;
    }

    /**
     * 在跳转到列表页面之前调用,一般用户往页面输出数据
     * @datetime 2018年6月11日 下午6:34:55
     */
    protected void preToList(Model model) {

    }

    @Autowired
    private void setBaseService(Service baseService) {
        this.baseService = baseService;
    }

}
