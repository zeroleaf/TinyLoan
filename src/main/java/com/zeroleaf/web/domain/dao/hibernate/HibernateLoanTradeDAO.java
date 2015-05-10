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
}
