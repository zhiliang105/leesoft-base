package com.leeframework.core.exception;

/**
 * DAO层异常
 * @author 李志亮 (Lee) <279683131(@qq.com),zhiliang.li@wirelessandon.cn>
 * @date Date:2016年5月7日 Time: 下午3:00:54
 * @version 1.0
 * @since version 1.0
 * @update
 */
public class DaoRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1672314203491245886L;

    private String message;

    public DaoRuntimeException() {
        super();
    }

    public DaoRuntimeException(String message) {
        super(message);
        this.message = message;
    }

    public DaoRuntimeException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public DaoRuntimeException(Throwable cause) {
        super(cause);
    }

    protected DaoRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
