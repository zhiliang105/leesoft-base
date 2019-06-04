package com.leeframework.common.model.view;

import java.util.HashMap;
import java.util.Map;

/**
 * 控制层向页面返回的消息模型<br>
 * 消息类型只提供基本类型模式,具体解析由具体的controller和view决定
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年12月3日 上午3:40:10
 */
public class ViewMessage {

    /**
     * 错误代码
     */
    public final static int CODE_ERROR = 0;
    /**
     * 成功代码
     */
    public final static int CODE_SUCCESS = 1;
    /**
     * 数据校验失败代码
     */
    public final static int CODE_VALIDAT = 2;

    private int code;// 消息状态码
    private Object msg = "";// 其他信息
    private Map<String, Object> attributes;// 其他参数

    public ViewMessage() {
        this(-1);
    }

    public ViewMessage(int code) {
        this(code, "", new HashMap<String, Object>());
    }

    public ViewMessage(int code, Object msg) {
        this(code, msg, new HashMap<String, Object>());
    }

    public ViewMessage(int code, Object msg, Map<String, Object> attributes) {
        this.code = code;
        this.msg = msg;
        this.attributes = attributes;
    }

    public ViewMessage setMessage(int code, String message) {
        this.code = code;
        this.msg = message;
        return this;
    }

    public static ViewMessage createError(String msg) {
        return new ViewMessage(CODE_ERROR, msg);
    }

    public static ViewMessage createSuc(String msg) {
        return new ViewMessage(CODE_SUCCESS, msg);
    }

    public ViewMessage addAttribute(String key, Object value) {
        this.attributes.put(key, value);
        return this;
    }

    public int getCode() {
        return code;
    }

    public Object getMsg() {
        return msg;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

}
