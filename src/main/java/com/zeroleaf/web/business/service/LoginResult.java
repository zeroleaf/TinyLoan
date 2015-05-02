package com.zeroleaf.web.business.service;

/**
 * Created by zeroleaf on 2015/5/1.
 */
public class LoginResult {

    public static final String MSG_USER_NOT_EXIST       = "用户名不存在";
    public static final String MSG_PASSWORD_NOT_MATCH   = "用户名或密码错误";
    public static final String MSG_SUCCESS              = "登陆成功";

    public static final LoginResult USER_NOT_EXIST
            = new LoginResult(false, MSG_USER_NOT_EXIST,     -1);

    public static final LoginResult PASSWORD_NOT_MATCH
            = new LoginResult(false, MSG_PASSWORD_NOT_MATCH, -1);

    private final boolean isValid;

    private final String msg;

    private final Integer type;

    protected LoginResult(boolean isValid, String msg, Integer type) {
        this.isValid = isValid;
        this.msg     = msg;
        this.type    = type;
    }

    public static LoginResult newSuccess(Integer type) {
        return new LoginResult(true, MSG_SUCCESS, type);
    }

    public boolean isValid() {
        return isValid;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getType() {
        return type;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "isValid=" + isValid +
                ", msg='" + msg + '\'' +
                ", type=" + type +
                '}';
    }
}
