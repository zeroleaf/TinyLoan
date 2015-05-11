package com.zeroleaf.web.domain.dao;

import com.zeroleaf.web.model.LoanApplicationForm;
import com.zeroleaf.web.model.User;

import java.util.List;

/**
 * Created by zeroleaf on 2015/5/3.
 */
public interface LoanApplicationFormDAO {

    List<LoanApplicationForm> getUnaudited(int pos, int limit);

    LoanApplicationForm findById(Long id);

    List<LoanApplicationForm> getInvestable(int pos, int limit);

    List<LoanApplicationForm> getToRefundForms(User debtor);

    List<LoanApplicationForm> getAllDebtForms(User debtor);

    long debtCount(User debtor);

    List<LoanApplicationForm> getRangeDebtForms(User debtor, int pos, int limit);

    long count();

    List<LoanApplicationForm> getLafs(int pos, int limit);
}
