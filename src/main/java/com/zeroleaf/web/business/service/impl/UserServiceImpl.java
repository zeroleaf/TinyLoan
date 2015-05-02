package com.zeroleaf.web.business.service.impl;

import com.zeroleaf.web.business.service.LoginResult;
import com.zeroleaf.web.business.service.UserService;
import com.zeroleaf.web.domain.dao.UserDAO;
import com.zeroleaf.web.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by zeroleaf on 2015/5/1.
 */
@Service @Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserDAO userDAO;

    @Override
    public Long save(User user) {
        return userDAO.save(user);
    }

    @Override
    public LoginResult login(String nick, String password) {
        User user = userDAO.findByNick(nick);
        if (user == null) {
            return LoginResult.USER_NOT_EXIST;
        }
        if (password.equals(user.getPassword())) {
            return LoginResult.newSuccess(user.getType());
        }
        return LoginResult.PASSWORD_NOT_MATCH;
    }
}
