package com.zeroleaf.web.domain.dao.hibernate;

import com.zeroleaf.web.domain.dao.AmountFlowDAO;
import com.zeroleaf.web.model.AmountFlow;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zeroleaf on 2015/5/5.
 */
@Component
public class HibernateAmountFlowDAO implements AmountFlowDAO {

    @Resource
    private SessionFactory sessionFactory;

    @Override @SuppressWarnings("unchecked")
    public List<AmountFlow> getRange(long userId, int pos, int limit) {
        final String hql = "FROM AmountFlow WHERE user.id = :userId ORDER BY time DESC";
        return (List<AmountFlow>) sessionFactory.getCurrentSession().createQuery(hql)
                .setLong("userId", userId)
                .setFirstResult(pos)
                .setMaxResults(limit)
                .list();
    }

    @Override @SuppressWarnings("unchecked")
    public List<AmountFlow> getProfitRange(long userId, int pos, int limit) {
        final String hql = "FROM AmountFlow WHERE user.id = :userId AND type = 6 ORDER BY time DESC";
        return (List<AmountFlow>) sessionFactory.getCurrentSession().createQuery(hql)
                .setLong("userId", userId)
                .setFirstResult(pos)
                .setMaxResults(limit)
                .list();
    }

    @Override
    public long count(long userId) {
        final String hql = "SELECT COUNT (*) FROM AmountFlow WHERE user.id = :userId";
        return (long) sessionFactory.getCurrentSession().createQuery(hql)
                .setLong("userId", userId).uniqueResult();
    }

    @Override
    public long getRaCount() {
        final String hql = "SELECT COUNT (*) FROM AmountFlow af WHERE af.type = 1 OR af.type = 2";
        return (long) sessionFactory.getCurrentSession().createQuery(hql)
                .uniqueResult();
    }

    @Override @SuppressWarnings("unchecked")
    public List<AmountFlow> getRaAmountFlow(int pos, int limit) {
        final String hql = "FROM AmountFlow af WHERE af.type = 1 OR af.type = 2 ORDER BY af.time DESC";
        return (List<AmountFlow>) sessionFactory.getCurrentSession().createQuery(hql)
                .setFirstResult(pos)
                .setMaxResults(limit)
                .list();
    }
}
