package com.zeroleaf.web.business.service;

/**
 * Created by zeroleaf on 2015/5/1.
 */
public enum LoginResult {

    USER_NOT_EXIST("用户名不存在"),
    PASSWORD_NOT_MATCH("用户名或密码错误"),
    SUCCESS("登陆成功");

    private String msg;

    LoginResult(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
