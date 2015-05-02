package com.zeroleaf.web.business.service;

import com.zeroleaf.web.model.User;

/**
 * Created by zeroleaf on 2015/5/1.
 */
public class LoginResult {

    public static final String MSG_USER_NOT_EXIST       = "用户名不存在";
    public static final String MSG_PASSWORD_NOT_MATCH   = "用户名或密码错误";
    public static final String MSG_SUCCESS              = "登陆成功";

    public static final LoginResult USER_NOT_EXIST
            = new LoginResult(false, MSG_USER_NOT_EXIST,     null);

    public static final LoginResult PASSWORD_NOT_MATCH
            = new LoginResult(false, MSG_PASSWORD_NOT_MATCH, null);

    private final boolean isValid;

    private final String msg;

    private final User user;

    protected LoginResult(boolean isValid, String msg, User user) {
        this.isValid = isValid;
        this.msg     = msg;
        this.user    = user;
    }

    public static LoginResult newSuccess(User user) {
        return new LoginResult(true, MSG_SUCCESS, user);
    }

    public boolean isValid() {
        return isValid;
    }

    public String getMsg() {
        return msg;
    }

    public User getUser() {
        return user;
    }

    public Integer getType() {
        return user.getType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginResult)) return false;

        LoginResult result = (LoginResult) o;

        return user.equals(result.user);
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "isValid=" + isValid +
                ", msg='" + msg + '\'' +
                ", user=" + user +
                '}';
    }
}
