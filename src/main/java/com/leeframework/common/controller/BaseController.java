package com.leeframework.common.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.leeframework.common.model.view.ViewMessage;
import com.leeframework.common.utils.DateUtil;
import com.leeframework.common.utils.StringUtil;
import com.leeframework.common.utils.SystemUtil;
import com.leeframework.common.utils.properties.SysConfigProperty;
import com.leeframework.core.web.helper.RequestHelper;
import com.leeframework.core.web.helper.ResponseHelper;

/**
 * Controller基础支持类
 * @author 李志亮 (Lee) <279683131(@qq.com)>
 * @date Date:2016年4月24日 Time: 下午8:52:43
 */
public abstract class BaseController {
    public final static String FORWARD_URL_PREFIX = UrlBasedViewResolver.FORWARD_URL_PREFIX;

    public final static String REDIRECT_URL_PREFIX = UrlBasedViewResolver.REDIRECT_URL_PREFIX;
    protected Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 绑定前端form提交的List长度(默认256)
     * @datetime 2018年6月27日 下午1:38:23
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024 * 10);

    }

    /**
     * 获取国际化资源信息<br>
     * 主要用于或者操作结果信息
     * @author lee
     * @date 2016年4月27日 下午2:54:57
     */
    protected String getMessage(String key) {
        HttpServletRequest request = RequestHelper.getRequest();
        String msg = RequestHelper.getMVCRequestContext(request).getMessage(key);
        return msg == null ? "" : msg;
    }

    /**
     * 获取在controller类上注解的访问路径
     * @datetime 2018年6月11日 下午5:32:20
     */
    protected String getRequestMappgingPre() {
        String currentClassRequestMapping = "";
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
        if (requestMapping != null && requestMapping.value().length > 0) {
            currentClassRequestMapping = requestMapping.value()[0];
            if (StringUtil.isNotEmpty(currentClassRequestMapping)) {

                // 替换掉${path.admin}或者${path.wap}等可支持的uri前缀
                Map<String, String> pathMap = SysConfigProperty.getPathMap();
                for (String name : pathMap.keySet()) {
                    String requestMappdingDefinePre = "${" + name + "}";
                    String value = pathMap.get(name);
                    if (currentClassRequestMapping.startsWith(requestMappdingDefinePre)) {
                        currentClassRequestMapping = currentClassRequestMapping.replace(requestMappdingDefinePre, "");
                        break;
                    } else if (currentClassRequestMapping.startsWith(value)) {
                        currentClassRequestMapping = currentClassRequestMapping.replace(value, "");
                        break;
                    }
                }
            }

        }
        if (StringUtil.isEmpty(currentClassRequestMapping)) {
            currentClassRequestMapping = "";
        }
        return currentClassRequestMapping;
    }

    /***
     * 数据校验失败信息提示
     * @datetime 2018年5月28日 下午10:26:44
     */
    protected ViewMessage invalid(String field, String msg) {
        ViewMessage vm = new ViewMessage(ViewMessage.CODE_VALIDAT);
        vm.addAttribute(field, msg);
        return vm;
    }

    /**
     * 重定向到指定的视图页面
     * @author lee
     * @date 2016年6月10日 下午3:53:40
     * @param viewName 视图的全名
     * @return
     */
    protected String redirect(String viewName) {
        return REDIRECT_URL_PREFIX + viewName;
    }

    /**
     * 删除成功提示信息
     * @datetime 2018年5月28日 上午1:36:37
     */
    protected ViewMessage sucDelete() {
        return new ViewMessage(ViewMessage.CODE_SUCCESS, getMessage("comm.suc.delete"));
    }

    /**
     * 添加成功提示信息
     * @datetime 2018年5月28日 上午1:36:37
     */
    protected ViewMessage sucSave() {
        return new ViewMessage(ViewMessage.CODE_SUCCESS, getMessage("comm.suc.save"));
    }

    /**
     * 文件上传解析
     * @datetime 2018年10月27日 下午6:28:49
     * @param request
     * @param file 上传的文件对象
     * @param folderName 保存到系统中的文件夹名称
     */
    protected UploadFile uploadFile(HttpServletRequest request, MultipartFile file, String folderName) throws Exception {
        UploadFile uFile = new UploadFile();
        if (file != null && !file.isEmpty()) {
            String fileOriName = file.getOriginalFilename();
            String ext = fileOriName.substring(fileOriName.lastIndexOf("."));
            String fileName = SystemUtil.getUUID() + ext;

            String date = DateUtil.dateFormat(new SimpleDateFormat("yyyyMMdd"));
            String basePath = request.getSession().getServletContext().getRealPath("/static/upload/" + folderName + "/" + date);
            File folder = new File(basePath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            file.transferTo(new File(folder, fileName));
            String path = "/static/upload/" + folderName + "/" + date + "/" + fileName;

            uFile.setHasFile(true);
            uFile.setFileName(fileName);
            uFile.setOriginalFilename(fileOriName);
            uFile.setPath(path);
        }
        return uFile;
    }

    /**
     * 下载导入模板
     * @datetime 2018年6月4日 下午9:28:34
     */
    @RequestMapping(value = "/downTemplate", method = RequestMethod.GET)
    public void downTemp(HttpSession session, HttpServletResponse response, String filename) throws IOException {
        File file = new File(session.getServletContext().getRealPath("/static/template/"), filename);
        ResponseHelper.writeExcelOutputStream(response, file);
    }

    /**
     * 修改成功提示信息
     * @datetime 2018年5月28日 上午1:36:37
     */
    protected ViewMessage sucUpdate() {
        return new ViewMessage(ViewMessage.CODE_SUCCESS, getMessage("comm.suc.update"));
    }
}
