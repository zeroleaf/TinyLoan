package com.zeroleaf.web.business.service.impl;

import com.zeroleaf.web.business.service.LoginResult;
import com.zeroleaf.web.business.service.UserService;
import com.zeroleaf.web.domain.dao.UserDAO;
import com.zeroleaf.web.model.LoanApplicationForm;
import com.zeroleaf.web.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
            return LoginResult.newSuccess(user);
        }
        return LoginResult.PASSWORD_NOT_MATCH;
    }

    @Override
    public boolean changePassword(String nick, String old, String password) {
        User user = userDAO.findByNick(nick);
        if (!old.equals(user.getPassword())) {
            return false;
        }

        user.setPassword(password);
        return true;
    }

    @Override
    public void updateInfo(User user) {
        User dbUser = userDAO.findByNick(user.getNick());
        dbUser.setIdNumber(user.getIdNumber());
        dbUser.setName(user.getName());
        dbUser.setPhoneNumber(user.getPhoneNumber());
        dbUser.setCardNumber(user.getCardNumber());
        dbUser.setEmail(user.getEmail());
    }

    @Override
    public void addLoanApplicationForm(String nick, LoanApplicationForm laf) {
        User dbUser = userDAO.findByNick(nick);
        dbUser.addLoanApplicationForm(laf);
    }

    @Override
    public List<LoanApplicationForm> getLoanApplicationForms(String nick, int limit) {
        return userDAO.getLoanApplicationForms(nick, limit);
    }
}
