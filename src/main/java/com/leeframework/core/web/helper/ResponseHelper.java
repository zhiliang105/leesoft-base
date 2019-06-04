package com.leeframework.core.web.helper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import com.leeframework.common.utils.ExcelUtil;

/**
 * HttpServletResponse辅助工具类
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月25日 下午9:37:02
 */
public final class ResponseHelper {
    public static final Logger log = LoggerFactory.getLogger(ResponseHelper.class);

    /**
     * 发送内容。使用UTF-8编码。
     * @param response HttpServletResponse
     * @param contentType
     * @param text
     */
    public static void render(HttpServletResponse response, String contentType, String text) {
        response.setContentType(contentType);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            response.getWriter().write(text);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 发送json。使用UTF-8编码。
     * @param response HttpServletResponse
     * @param text 发送的字符串
     */
    public static void renderJson(HttpServletResponse response, String text) {
        render(response, "application/json;charset=UTF-8", text);
    }

    /**
     * 发送文本。使用UTF-8编码。
     * @param response HttpServletResponse
     * @param text 发送的字符串
     */
    public static void renderText(HttpServletResponse response, String text) {
        render(response, "text/plain;charset=UTF-8", text);
    }

    /**
     * 发送xml。使用UTF-8编码。
     * @param response HttpServletResponse
     * @param text 发送的字符串
     */
    public static void renderXml(HttpServletResponse response, String text) {
        render(response, "text/xml;charset=UTF-8", text);
    }

    /**
     * 输出excel流
     * @throws IOException
     * @datetime 2018年6月4日 下午10:10:05
     */
    public static void writeExcelOutputStream(HttpServletResponse response, File file) throws IOException {
        String fileName = new String(file.getName().getBytes(), "ISO8859-1");
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        writeExcelOutputStream(response, inputStream, fileName);
    }

    /**
     * 输出excel流
     * @throws IOException
     * @datetime 2018年6月4日 下午10:10:05
     */
    public static void writeExcelOutputStream(HttpServletResponse response, InputStream is, String fileName) throws IOException {
        fileName = new String(fileName.getBytes(), "ISO8859-1");
        response.setHeader("Content-disposition", fileName);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.setHeader("Pragma", "No-cache");
        FileCopyUtils.copy(is, response.getOutputStream());
    }

    /**
     * 输出excel流
     * @throws IOException
     * @datetime 2018年6月4日 下午10:10:05
     */
    public static void writeExcelOutputStream(HttpServletResponse response, Workbook wb, String fileName) throws IOException {
        InputStream inputStream = ExcelUtil.getInputStream(wb);
        writeExcelOutputStream(response, inputStream, fileName);
    }

}
