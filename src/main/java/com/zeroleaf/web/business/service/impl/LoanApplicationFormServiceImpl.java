package com.zeroleaf.web.business.service.impl;

import com.zeroleaf.web.business.service.LoanApplicationFormService;
import com.zeroleaf.web.domain.dao.LoanApplicationFormDAO;
import com.zeroleaf.web.domain.dao.UserDAO;
import com.zeroleaf.web.model.LoanApplicationForm;
import com.zeroleaf.web.model.LoanTrade;
import com.zeroleaf.web.model.User;
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

    @Resource
    private UserDAO userDAO;

    @Override
    public List<LoanApplicationForm> getUnaudited(int page) {
        final int limit = 20;
        int pos = (page - 1) * limit;
        return loanApplicationFormDAO.getUnaudited(pos, limit);
    }

    @Override
    public void setStatus(Long id, Integer status) {
        // TODO FIXME 根据当前业务规则 设置 贷款状态。
        LoanApplicationForm laf = loanApplicationFormDAO.findById(id);
        if (laf != null) {
            laf.setStatus(status);
        }
    }

    @Override
    public List<LoanApplicationForm> getInvestable(int page) {
        final int limit = 20;
        int pos = (page - 1) * limit;
        return loanApplicationFormDAO.getInvestable(pos, limit);
    }

    @Override
    public void newInvest(User investor, Long id, Integer quantity) {
        // TODO 增加资金流信息.
        LoanApplicationForm laf = loanApplicationFormDAO.findById(id);
        if (laf != null) {
            LoanTrade trade = LoanTrade.newTrade(investor, quantity);
            laf.addLoanTrade(trade);

            investor.decreaseBalance(trade.getBalance());   // 投资者减少资金.
            userDAO.update(investor);

            User admin = userDAO.findByNick("admin");       // 平台增加相应资金.
            admin.increaseBalance(trade.getBalance());

            if (laf.isDone()) {
                laf.getUser().increaseBalance(laf.getBalance());    // 借贷者增加借贷金额.
                admin.decreaseBalance(laf.getBalance());            // 平台减少借贷金额.
            }
        }
    }
}
