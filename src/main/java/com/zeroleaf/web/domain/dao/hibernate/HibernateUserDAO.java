package com.zeroleaf.web.domain.dao.hibernate;

import com.zeroleaf.web.domain.dao.UserDAO;
import com.zeroleaf.web.model.LoanApplicationForm;
import com.zeroleaf.web.model.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zeroleaf on 2015/5/1.
 */
@Component
public class HibernateUserDAO implements UserDAO {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Long save(User user) {
        return (Long) sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    public User merge(User user) {
        return (User) sessionFactory.getCurrentSession().merge(user);
    }

    @Override
    public User findById(Long id) {
        return (User) sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public User findByNick(String nick) {
        final String hql = "FROM User WHERE nick = :nick";
        return (User) sessionFactory.getCurrentSession().createQuery(hql)
                .setString("nick", nick)
                .uniqueResult();
    }

    @Override @SuppressWarnings("unchecked")
    public List<LoanApplicationForm> getLoanApplicationForms(String nick, int limit) {
        final String hql = "FROM LoanApplicationForm laf WHERE laf.user.nick = :nick ORDER BY laf.date DESC, laf.id DESC";
        return (List<LoanApplicationForm>) sessionFactory.getCurrentSession().createQuery(hql)
                .setString("nick", nick)
                .setMaxResults(limit)
                .list();
    }

    @Override @SuppressWarnings("unchecked")
    public List<LoanApplicationForm> getRefund(Long userId, int limit) {
        final String hql = "FROM LoanApplicationForm WHERE user.id = :userId AND raiseQuantity >= quantity ORDER BY id DESC";
        return (List<LoanApplicationForm>) sessionFactory.getCurrentSession().createQuery(hql)
                .setLong("userId", userId)
                .setMaxResults(limit)
                .list();
    }
}
