package com.zeroleaf.web.business.service;

import com.zeroleaf.web.model.User;

/**
 * Created by zeroleaf on 2015/5/1.
 */
public interface UserService {

    Long save(User user);

    LoginResult login(String nick, String password);
}
