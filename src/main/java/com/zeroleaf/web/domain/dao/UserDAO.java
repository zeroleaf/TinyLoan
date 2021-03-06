package com.zeroleaf.web.domain.dao;

import com.zeroleaf.web.model.LoanApplicationForm;
import com.zeroleaf.web.model.User;

import java.util.List;

/**
 * Created by zeroleaf on 2015/5/1.
 */
public interface UserDAO {

    Long save(User user);

    void update(User user);

    User merge(User user);

    User findById(Long id);

    /**
     * 通过 用户名 查找该用户.
     *
     * 如果指定的用户名不存在, 则返回 null.
     *
     * @param nick 用户名
     * @return 该用户名对应的用户.
     */
    User findByNick(String nick);

    List<LoanApplicationForm> getLoanApplicationForms(String nick, int limit);

    List<LoanApplicationForm> getRefund(Long id, int limit);

    long getTypeUserCount(Integer type);

    List<User> getTypeUser(Integer type, int pos, int limit);

}
