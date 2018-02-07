package com.sdot.yidai.model;

/**
 * Created by Administrator on 2017/11/7.
 */

public class ResultModel {

    /**
     * timestamp : 1510034428139
     * status : 500
     * error : Internal Server Error
     * exception : com.liyang.util.FailReturnObject
     * message : 1542 : 请退出登录
     * path : /register
     */

    private long timestamp;
    private int status;
    private String error;
    private String exception;
    private String message;
    private String path;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
