package com.zeroleaf.web.domain.dao.hibernate;

import com.zeroleaf.web.domain.dao.LoanApplicationFormDAO;
import com.zeroleaf.web.model.LoanApplicationForm;
import com.zeroleaf.web.model.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zeroleaf on 2015/5/3.
 */
@Component
public class HibernateLoanApplicationFormDAO implements LoanApplicationFormDAO {

    @Resource
    private SessionFactory sessionFactory;

    @Override @SuppressWarnings("unchecked")
    public List<LoanApplicationForm> getUnaudited(int pos, int limit) {
        final String hql = "FROM LoanApplicationForm laf WHERE laf.status = 1";
        return (List<LoanApplicationForm>) sessionFactory.getCurrentSession().createQuery(hql)
                .setFirstResult(pos)
                .setMaxResults(limit)
                .list();
    }

    @Override
    public LoanApplicationForm findById(Long id) {
        return (LoanApplicationForm) sessionFactory.getCurrentSession()
                .get(LoanApplicationForm.class, id);
    }

    @Override @SuppressWarnings("unchecked")
    public List<LoanApplicationForm> getInvestable(int pos, int limit) {
        final String hql = "FROM LoanApplicationForm WHERE status = 2 AND raiseQuantity < quantity ORDER BY date DESC, id DESC";
        return (List<LoanApplicationForm>) sessionFactory.getCurrentSession().createQuery(hql)
                .setFirstResult(pos)
                .setMaxResults(limit)
                .list();
    }

    @Override @SuppressWarnings("unchecked")
    public List<LoanApplicationForm> getToRefundForms(User debtor) {
        final String hql = "FROM LoanApplicationForm WHERE status = 2 AND raiseQuantity >= quantity AND isRefunded = false AND user.id = :debtorId";
        return (List<LoanApplicationForm>) sessionFactory.getCurrentSession().createQuery(hql)
                .setLong("debtorId", debtor.getId())
                .list();
    }

    @Override @SuppressWarnings("unchecked")
    public List<LoanApplicationForm> getAllDebtForms(User debtor) {
        final String hql = "FROM LoanApplicationForm WHERE user.id = :debtorId AND status = 2 AND raiseQuantity >= quantity";
        return (List<LoanApplicationForm>) sessionFactory.getCurrentSession().createQuery(hql)
                .setLong("debtorId", debtor.getId())
                .list();
    }

    @Override
    public long debtCount(User debtor) {
        final String hql = "SELECT COUNT (*) FROM LoanApplicationForm WHERE user.id = :debtorId";
        return (long) sessionFactory.getCurrentSession().createQuery(hql)
                .setLong("debtorId", debtor.getId())
                .uniqueResult();
    }

    @Override @SuppressWarnings("unchecked")
    public List<LoanApplicationForm> getRangeDebtForms(User debtor, int pos, int limit) {
        final String hql = "FROM LoanApplicationForm WHERE user.id = :debtorId ORDER BY date DESC";
        return (List<LoanApplicationForm>) sessionFactory.getCurrentSession().createQuery(hql)
                .setLong("debtorId", debtor.getId())
                .setFirstResult(pos)
                .setMaxResults(limit)
                .list();
    }

    @Override
    public long count() {
        final String hql = "SELECT COUNT (*) FROM LoanApplicationForm";
        return (long) sessionFactory.getCurrentSession().createQuery(hql)
                .uniqueResult();
    }

    @Override @SuppressWarnings("unchecked")
    public List<LoanApplicationForm> getLafs(int pos, int limit) {
        final String hql = "FROM LoanApplicationForm";
        return (List<LoanApplicationForm>) sessionFactory.getCurrentSession().createQuery(hql)
                .setFirstResult(pos)
                .setMaxResults(limit)
                .list();
    }

    @Override @SuppressWarnings("unchecked")
    public List<LoanApplicationForm> getNotRefund(int limit) {
        final String hql = "FROM LoanApplicationForm laf WHERE status = 2 AND raiseQuantity >= quantity AND isRefunded = false ORDER BY date DESC";
        return (List<LoanApplicationForm>) sessionFactory.getCurrentSession().createQuery(hql)
                .setMaxResults(limit)
                .list();
    }
}
