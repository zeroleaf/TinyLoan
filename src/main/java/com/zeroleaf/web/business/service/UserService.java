package com.zeroleaf.web.business.service;

import com.zeroleaf.web.model.LoanApplicationForm;
import com.zeroleaf.web.model.User;

import java.util.List;

/**
 * Created by zeroleaf on 2015/5/1.
 */
public interface UserService {

    Long newUser(User user);

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

    void addLoanApplicationForm(String nick, LoanApplicationForm laf);

    List<LoanApplicationForm> getLoanApplicationForms(String nick, int limit);

    User findByNick(String nick);
}
