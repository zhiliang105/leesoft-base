package com.leeframework.modules.system.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;

/**
 * 验证码生成控制器
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年12月3日 上午1:36:22
 */
@Controller
public class KaptchaController {
    private final Logger log = LoggerFactory.getLogger(KaptchaController.class);

    @Autowired
    private DefaultKaptcha captchaService;

    @RequestMapping("/validCode")
    public void validCode(HttpServletRequest request, HttpServletResponse response) {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        ServletOutputStream out = null;
        try {
            String capText = captchaService.createText();
            BufferedImage bi = captchaService.createImage(capText);
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();
            request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        } catch (IOException e) {
            log.error("Verification code generation failed", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
