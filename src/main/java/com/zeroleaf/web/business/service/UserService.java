package com.zeroleaf.web.business.service;

import com.zeroleaf.web.model.User;

/**
 * Created by zeroleaf on 2015/5/1.
 */
public interface UserService {

    Long save(User user);

    LoginResult login(String nick, String password);

    /**
     * 修改用户的密码.
     *
     * 修改成功返回 true, 失败返回 false.
     *
     * @param nick 用户名.
     * @param old 旧密码.
     * @param password 新密码.
     * @return 成功 true, 失败 false.
     */
    boolean changePassword(String nick, String old, String password);

    void updateInfo(User user);
}
