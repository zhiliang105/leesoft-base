package com.leeframework.common.model.plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * layui上传组件返回的数据模型
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年5月31日 下午9:08:47
 */
public class LayuiUpload {

    private int code;
    private String msg = "";
    private Map<String, String> data = new HashMap<String, String>();

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

}
