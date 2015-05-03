package com.zeroleaf.web.business.service;

import com.zeroleaf.web.model.LoanApplicationForm;
import com.zeroleaf.web.model.User;

import java.util.List;

/**
 * Created by zeroleaf on 2015/5/3.
 */
public interface LoanApplicationFormService {

    List<LoanApplicationForm> getUnaudited(int page);

    void setStatus(Long id, Integer status);

    List<LoanApplicationForm> getInvestable(int page);

    void newInvest(User investor, Long id, Integer quantity);
}
