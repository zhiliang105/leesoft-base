package com.leeframework.modules.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leeframework.common.controller.BaseController;
import com.leeframework.common.model.Page;
import com.leeframework.common.model.view.PageData;
import com.leeframework.common.model.view.BootstrapTableParams;
import com.leeframework.common.model.view.ViewMessage;
import com.leeframework.modules.system.entity.Role;
import com.leeframework.modules.system.service.RoleService;

/**
 * 系统角色业务控制器
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年5月23日 上午12:47:12
 */
@Controller
@RequestMapping(value = "${path.admin}/system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 跳转至列表页面
     * @datetime 2018年5月28日 下午11:49:40
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return "/system/role/roleList";
    }

    /**
     * 获取列表数据
     * @datetime 2018年5月28日 下午11:49:54
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public PageData list(BootstrapTableParams pageParams) {
        Page<Role> page = this.roleService.find(pageParams);
        return new PageData(page);
    }

    /**
     * 执行删除功能
     * @datetime 2018年5月28日 下午11:50:03
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ViewMessage delete(String[] ids) {
        this.roleService.delete(ids);
        return sucDelete();
    }

    /**
     * 跳转到新增表单页面
     * @datetime 2018年5月28日 下午11:50:15
     */
    @RequestMapping(value = "/toAddFrom", method = RequestMethod.GET)
    public String toAddFrom(Model model) {
        return "/system/role/roleForm";
    }

    /**
     * 新增角色信息
     * @datetime 2018年5月28日 下午11:50:31
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ViewMessage save(@Validated Role role, BindingResult result) {
        ViewMessage vm = validate(role, null);
        if (vm == null) {
            this.roleService.save(role);
            return sucSave();
        }
        return vm;
    }

    /**
     * 跳转至修改角色 页面
     * @datetime 2018年5月28日 下午11:50:42
     */
    @RequestMapping(value = "/toUpdateFrom", method = RequestMethod.GET)
    public String toUpdateFrom(String id, Model model) {
        Role role = this.roleService.get(id);
        model.addAttribute("role", role);
        return "/system/role/roleForm";
    }

    /**
     * 修改角色信息
     * @datetime 2018年5月28日 下午11:50:53
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ViewMessage update(@Validated Role role, BindingResult result) {
        Role oldRole = this.roleService.get(role.getId());
        ViewMessage vm = validate(role, oldRole);
        if (vm == null) {
            oldRole.setCode(role.getCode());
            oldRole.setIsSystem(role.getIsSystem());
            oldRole.setName(role.getName());
            oldRole.setRemark(role.getRemark());
            oldRole.setSort(role.getSort());
            oldRole.setType(role.getType());
            this.roleService.update(oldRole);
            return sucUpdate();
        }
        return vm;
    }

    /**
     * 前端同名验证
     * @datetime 2018年6月11日 下午11:49:29
     */
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    @ResponseBody
    public ViewMessage validate(String property, String value) {
        boolean flag = this.roleService.validate(property, value);
        if (flag) {
            return invalid(property, "");
        } else {
            return new ViewMessage();
        }
    }

    /*
     * 同名验证
     */
    private ViewMessage validate(Role newRole, Role oldRole) {
        String code = newRole.getCode();
        if (oldRole == null || !oldRole.getCode().equals(code)) {
            boolean hasCode = this.roleService.validate("code", code);
            if (hasCode) {
                return invalid("code", "角色编号已经存在,不可重复添加");
            }
        }

        String name = newRole.getName();
        if (oldRole == null || !oldRole.getName().equals(name)) {
            boolean hasName = this.roleService.validate("name", name);
            if (hasName) {
                return invalid("name", "角色名称已经存在,不可重复添加");
            }
        }
        return null;
    }

}
