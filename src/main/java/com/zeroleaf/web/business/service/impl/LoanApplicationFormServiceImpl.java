package com.zeroleaf.web.business.service.impl;

import com.zeroleaf.web.business.service.LoanApplicationFormService;
import com.zeroleaf.web.business.service.Page;
import com.zeroleaf.web.business.service.dto.AppRecord;
import com.zeroleaf.web.business.service.dto.InvestRecord;
import com.zeroleaf.web.business.service.dto.MyDebt;
import com.zeroleaf.web.domain.dao.LoanApplicationFormDAO;
import com.zeroleaf.web.domain.dao.LoanTradeDAO;
import com.zeroleaf.web.domain.dao.UserDAO;
import com.zeroleaf.web.model.AmountFlow;
import com.zeroleaf.web.model.LoanApplicationForm;
import com.zeroleaf.web.model.LoanTrade;
import com.zeroleaf.web.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Resource
    private LoanTradeDAO loanTradeDAO;

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
        LoanApplicationForm laf = loanApplicationFormDAO.findById(id);
        if (laf != null) {
            LoanTrade trade = LoanTrade.newTrade(investor, quantity);
            laf.addLoanTrade(trade);

            investor = userDAO.merge(investor);
            investor.invest(trade.getBalance(), laf.getTitle());    // 投资者减少资金.

            User admin = userDAO.findByNick("admin");               // 平台增加相应资金.
            admin.increaseBalance(trade.getBalance());

            if (laf.isDone()) {
                laf.getUser().debt(laf.getBalance(), laf.getTitle());   // 借贷者增加借贷金额.
                admin.decreaseBalance(laf.getBalance());                // 平台减少借贷金额.
            }
        }
    }

    @Override
    public void newRefund(User debtor, Long lafId) {
        LoanApplicationForm laf = loanApplicationFormDAO.findById(lafId);
        if (laf != null) {
            laf.setIsRefunded(true);        // 设置 已回款标志.

            // 借款者减少金额
            debtor = userDAO.merge(debtor);
            debtor.refund(laf.getRefundBalance(), laf.getTitle());

            // 所有投资者增加相应金额.
            List<LoanTrade> trades = laf.getTrades();
            for (LoanTrade trade : trades) {
                User investor = trade.getInvestor();
                investor.profit(trade.getProfit(), laf.getTitle());
            }
        }
    }

    @Override
    public double getToRefundBalance(User debtor) {
        double toRefundBalance = 0;
        List<LoanApplicationForm> lafs = loanApplicationFormDAO.getToRefundForms(debtor);
        for (LoanApplicationForm laf : lafs) {
            toRefundBalance += laf.getRefundBalance();
        }
        return toRefundBalance;
    }

    @Override
    public double getTotalDebtAmount(User debtor) {
        double totalDebtAmount = 0;
        List<LoanApplicationForm> lafs = loanApplicationFormDAO.getAllDebtForms(debtor);
        for (LoanApplicationForm laf : lafs) {
            totalDebtAmount += laf.getBalance();
        }
        return totalDebtAmount;
    }

    @Override
    public Page<AppRecord> lafs(Integer page) {
        final int pageSize = 10;
        int pos = pageSize * (page - 1);
        long count = loanApplicationFormDAO.count();

        Page<AppRecord> p = new Page<>(page, pageSize, count);
        List<LoanApplicationForm> lafs = loanApplicationFormDAO.getLafs(pos, pageSize);
        for (LoanApplicationForm laf :lafs) {
            AppRecord record = new AppRecord(laf.getCode(), laf.getUser().getNick(),
                    laf.getQuantity(), laf.getDate(), laf.getFormatStatus());
            p.addContent(record);
        }
        return p;
    }

    @Override
    public List<AppRecord> getNotRefund(int limit) {
        List<AppRecord> list = new ArrayList<>();
        List<LoanApplicationForm> lafs = loanApplicationFormDAO.getNotRefund(limit);
        for (LoanApplicationForm laf : lafs) {
            AppRecord record = new AppRecord(laf.getCode(), laf.getUser().getNick(),
                    laf.getQuantity(), laf.getDate(), laf.getFormatStatus());
            record.setAmount(laf.getRefundBalance());
            list.add(record);
        }
        return list;
    }
}
