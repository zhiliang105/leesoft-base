package com.leeframework.modules.system.controller;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.leeframework.common.controller.BaseController;
import com.leeframework.common.model.view.ViewMessage;
import com.leeframework.common.utils.DateUtil;
import com.leeframework.common.utils.properties.SysConfigProperty;
import com.leeframework.core.exception.shiro.CaptchaException;
import com.leeframework.core.security.encrypt.RSAEncoder;
import com.leeframework.core.security.shiro.CustomerUsernamePasswordToken;

/**
 * 用户登录相关的业务控制器
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月30日 上午12:05:10
 */
@Controller
@RequestMapping(value = "${path.admin}")
public class LoginController extends BaseController {
    private final static String RSA_P_KEY = "_rsa_private_key_"; // 用于保存密码解密的私钥的session key

    /**
     * 转发到登录页面
     * @datetime 2017年11月30日 上午12:06:01
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
        createRSAKey(request);

        // 开发调试模式
        boolean isDepDebug = SysConfigProperty.getDepDebug();
        if (isDepDebug) {
            model.addAttribute("userName", SysConfigProperty.getDepUserName());
            model.addAttribute("password", SysConfigProperty.getDepPassword());
            model.addAttribute("validCode", "debug");
        }

        String year = DateUtil.getCurrentYear();
        model.addAttribute("year", year);

        return "/system/main/login";
    }

    /**
     * 系统登录处理
     * @datetime 2017年12月3日 上午1:48:58
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ViewMessage login(String userName, String password, String validCode, HttpServletRequest request, HttpServletResponse response) {
        ViewMessage message = new ViewMessage();
        HttpSession session = request.getSession();

        RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute(RSA_P_KEY);
        String rawPassword = RSAEncoder.decrypt(privateKey, password);
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(rawPassword)) {
            message.setMessage(0, "账户或者密码不能为空");
        } else {
            Subject subject = SecurityUtils.getSubject();

            // 开发调试模式
            boolean isDepDebug = SysConfigProperty.getDepDebug();
            if (isDepDebug) {
                validCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
            }

            CustomerUsernamePasswordToken token = new CustomerUsernamePasswordToken(userName, rawPassword, validCode);
            try {
                subject.login(token);
                if (subject.isAuthenticated()) {
                    message.setMessage(1, "登录成功");
                    session.removeAttribute(RSA_P_KEY);
                } else {
                    message.setMessage(0, "登录失败");
                }
            } catch (AuthenticationException e) {
                token.clear();
                if (e instanceof CaptchaException) {
                    message.setMessage(0, "验证码输入有误");
                } else if (e instanceof UnknownAccountException) {
                    message.setMessage(0, "账户名不存在");
                } else if (e instanceof IncorrectCredentialsException) {
                    message.setMessage(0, "账户密码错误");
                } else if (e instanceof DisabledAccountException) {
                    message.setMessage(0, "账户被禁用");
                } else {
                    message.setMessage(0, getMessage("登录失败,未知错误"));
                }
            }
        }
        return message;
    }

    @RequestMapping(value = "/index")
    public String index(Model model) {

        String year = DateUtil.getCurrentYear();
        model.addAttribute("year", year);

        return "/system/main/index";
    }

    @RequestMapping(value = "/home")
    public String home() {
        return "/system/main/home";
    }

    /**
     * 生成RSA密码加密信息
     * @datetime 2017年12月3日 上午1:47:30
     * @param request
     */
    private void createRSAKey(HttpServletRequest request) {
        // 生成密码加密信息
        Map<String, Object> map = RSAEncoder.getKeys();
        RSAPublicKey publicKey = (RSAPublicKey) map.get(RSAEncoder.K_PUBLIC);
        RSAPrivateKey privateKey = (RSAPrivateKey) map.get(RSAEncoder.K_PRIVATE);

        // 公钥信息输出到页面进行加密
        String publicKeyExponent = publicKey.getPublicExponent().toString(16);
        String publicKeyModulus = publicKey.getModulus().toString(16);
        request.setAttribute("publicKeyExponent", publicKeyExponent);
        request.setAttribute("publicKeyModulus", publicKeyModulus);

        // 私钥保留至session,进行验证
        request.getSession().setAttribute(RSA_P_KEY, privateKey);
    }
}
