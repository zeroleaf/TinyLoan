package com.zeroleaf.web.domain.dao;

import com.zeroleaf.web.model.LoanApplicationForm;

import java.util.List;

/**
 * Created by zeroleaf on 2015/5/3.
 */
public interface LoanApplicationFormDAO {

    List<LoanApplicationForm> getUnaudited(int pos, int limit);

    LoanApplicationForm findById(Long id);
}
