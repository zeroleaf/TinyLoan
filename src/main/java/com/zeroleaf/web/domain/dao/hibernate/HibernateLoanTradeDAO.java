package com.zeroleaf.web.domain.dao.hibernate;

import com.zeroleaf.web.domain.dao.LoanTradeDAO;
import com.zeroleaf.web.model.LoanTrade;
import com.zeroleaf.web.model.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zeroleaf on 2015/5/10.
 */
@Component
public class HibernateLoanTradeDAO implements LoanTradeDAO {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public long count() {
        final String hql = "SELECT COUNT (*) FROM LoanTrade";
        return (long) sessionFactory.getCurrentSession()
                .createQuery(hql).uniqueResult();
    }

    @Override @SuppressWarnings("unchecked")
    public List<LoanTrade> getLoanTrade(int pos, int limit) {
        final String hql = "FROM LoanTrade";
        return (List<LoanTrade>) sessionFactory.getCurrentSession().createQuery(hql)
                .setFirstResult(pos)
                .setMaxResults(limit)
                .list();
    }

    @Override
    public long count(User investor) {
        final String hql = "SELECT COUNT (*) FROM LoanTrade WHERE investor.id = :investorId";
        return (long) sessionFactory.getCurrentSession().createQuery(hql)
                .setLong("investorId", investor.getId())
                .uniqueResult();
    }

    @Override @SuppressWarnings("unchecked")
    public List<LoanTrade> getLoanTrade(User investor, int pos, int limit) {
        final String hql = "FROM LoanTrade WHERE investor.id = :investorId";
        return (List<LoanTrade>) sessionFactory.getCurrentSession().createQuery(hql)
                .setLong("investorId", investor.getId())
                .setFirstResult(pos)
                .setMaxResults(limit)
                .list();
    }

    @Override
    public long debtCount(User debtor) {
        final String hql = "SELECT COUNT (*) FROM LoanTrade trade INNER JOIN trade.form laf WHERE laf.user.id = :debtorId";
        return (long) sessionFactory.getCurrentSession().createQuery(hql)
                .setLong("debtorId", debtor.getId())
                .uniqueResult();
    }

    @Override @SuppressWarnings("unchecked")
    public List<LoanTrade> getDebtTrade(User debtor, int pos, int limit) {
        final String hql = "SELECT trade FROM LoanTrade trade INNER JOIN trade.form laf WHERE laf.user.id = :debtorId ORDER BY laf.date DESC, trade.id DESC ";
        return (List<LoanTrade>) sessionFactory.getCurrentSession().createQuery(hql)
                .setLong("debtorId", debtor.getId())
                .setFirstResult(pos)
                .setMaxResults(limit)
                .list();
    }
}
