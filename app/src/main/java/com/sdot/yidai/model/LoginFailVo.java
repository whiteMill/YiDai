package com.sdot.yidai.model;

/**
 * Created by Administrator on 2017/11/22.
 */

public class LoginFailVo {


    /**
     * ActionStatus : FAIL
     * ErrorInfo : 登录失败，用户名或密码错误！
     * ErrorCode : 1353
     * MsgTime : null
     */

    private String ActionStatus;
    private String ErrorInfo;
    private String ErrorCode;
    private Object MsgTime;

    public String getActionStatus() {
        return ActionStatus;
    }

    public void setActionStatus(String ActionStatus) {
        this.ActionStatus = ActionStatus;
    }

    public String getErrorInfo() {
        return ErrorInfo;
    }

    public void setErrorInfo(String ErrorInfo) {
        this.ErrorInfo = ErrorInfo;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public Object getMsgTime() {
        return MsgTime;
    }

    public void setMsgTime(Object MsgTime) {
        this.MsgTime = MsgTime;
    }
}
