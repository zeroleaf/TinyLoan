package com.zeroleaf.web.domain.dao.hibernate;

import com.zeroleaf.web.domain.dao.LoanApplicationFormDAO;
import com.zeroleaf.web.model.LoanApplicationForm;
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
}
