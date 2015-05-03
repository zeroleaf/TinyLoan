package com.zeroleaf.web.business.service.impl;

import com.zeroleaf.web.business.service.LoanApplicationFormService;
import com.zeroleaf.web.domain.dao.LoanApplicationFormDAO;
import com.zeroleaf.web.model.LoanApplicationForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zeroleaf on 2015/5/3.
 */
@Service @Transactional
public class LoanApplicationFormServiceImpl implements LoanApplicationFormService {

    @Resource
    private LoanApplicationFormDAO loanApplicationFormDAO;

    @Override
    public List<LoanApplicationForm> getUnaudited(int page) {
        final int limit = 20;
        int pos = (page - 1) * limit;
        return loanApplicationFormDAO.getUnaudited(pos, limit);
    }

    @Override
    public void setStatus(Long id, Integer status) {
        LoanApplicationForm laf = loanApplicationFormDAO.findById(id);
        if (laf != null) {
            laf.setStatus(status);
        }
    }
}
