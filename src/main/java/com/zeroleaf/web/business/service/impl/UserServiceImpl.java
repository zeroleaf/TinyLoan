package com.zeroleaf.web.business.service.impl;

import com.zeroleaf.web.business.service.LoginResult;
import com.zeroleaf.web.business.service.Page;
import com.zeroleaf.web.business.service.UserService;
import com.zeroleaf.web.business.service.dto.InvestAnalysis;
import com.zeroleaf.web.domain.dao.UserDAO;
import com.zeroleaf.web.model.Asset;
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
    public Long newUser(User user) {
        if (user.getAsset() == null) {
            user.setAsset(Asset.newEmptyAsset());
        }
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

    @Override
    public List<LoanApplicationForm> getRefundForms(User user, int limit) {
        return userDAO.getRefund(user.getId(), limit);
    }

    @Override
    public User findByNick(String nick) {
        return userDAO.findByNick(nick);
    }

    @Override
    public void recharge(User user, Double charge, String type) {
        User dbUser = userDAO.findById(user.getId());
        dbUser.recharge(charge, type);
    }

    @Override
    public void advance(User user, Double amount, String credit) {
        User dbUser = userDAO.findById(user.getId());
        dbUser.advance(amount, credit);
    }

    @Override
    public Page<User> getTypeUser(Integer page, Integer type) {
        final int pageSize = 10;
        int pos = pageSize * (page - 1);
        long count = userDAO.getTypeUserCount(type);

        Page<User> up = new Page<>(page, pageSize, count);
        up.addContent(userDAO.getTypeUser(type, pos, pageSize));
        return up;
    }
}
