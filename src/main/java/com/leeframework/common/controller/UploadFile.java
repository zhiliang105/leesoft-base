package com.leeframework.common.controller;

/**
 * 上传的文件信息
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年10月27日 下午6:26:25
 */
public class UploadFile {

    private boolean hasFile = false;

    private String originalFilename; // 原始文件名称
    private String fileName;// 保存到系统中的文件名称
    private String path;// 保存到系统中的图片的相对路径

    public String getOriginalFilename() {
        return originalFilename;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isHasFile() {
        return hasFile;
    }

    public void setHasFile(boolean hasFile) {
        this.hasFile = hasFile;
    }

}
