package com.zeroleaf.web.business.service.impl;

import com.zeroleaf.web.business.service.LoanTradeService;
import com.zeroleaf.web.business.service.Page;
import com.zeroleaf.web.business.service.dto.InvestAnalysis;
import com.zeroleaf.web.business.service.dto.InvestRecord;
import com.zeroleaf.web.business.service.dto.MyDebt;
import com.zeroleaf.web.domain.dao.LoanTradeDAO;
import com.zeroleaf.web.model.LoanApplicationForm;
import com.zeroleaf.web.model.LoanTrade;
import com.zeroleaf.web.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zeroleaf on 2015/5/10.
 */
@Service @Transactional
public class LoanTradeServiceImpl implements LoanTradeService {

    @Resource
    private LoanTradeDAO loanTradeDAO;

    @Override
    public Page<InvestRecord> getInvestRecord(User investor, int page) {

        final int pageSize = 5;
        int pos = pageSize * (page - 1);
        long totalNumber = loanTradeDAO.count(investor);
        Page<InvestRecord> r = new Page<>(page, pageSize, totalNumber);

        List<LoanTrade> trades = loanTradeDAO.getLoanTrade(investor, pos, pageSize);
        for (LoanTrade trade : trades) {
            LoanApplicationForm laf = trade.getForm();
            InvestRecord iRecord = InvestRecord.from(trade, laf);
            r.addContent(iRecord);
        }

        return r;
    }

    @Override
    public InvestAnalysis getInvestAnalysis(User investor) {

        InvestAnalysis analysis = new InvestAnalysis();

        List<LoanTrade> trades = loanTradeDAO.getLoanTrade(investor, 0, Integer.MAX_VALUE);
        for (LoanTrade trade : trades) {
            LoanApplicationForm laf = trade.getForm();
            analysis.tickKey(laf.getDeadline());
        }

        return analysis;
    }

    @Override
    public double getTotalInvestProfit(User investor) {
        double totalProfit = 0;
        List<LoanTrade> trades = loanTradeDAO.getLoanTrade(investor, 0, Integer.MAX_VALUE);
        for (LoanTrade trade : trades) {
            LoanApplicationForm laf = trade.getForm();
            if (laf.getIsRefunded()) {
                totalProfit += laf.getSingleProfit() * trade.getQuantity();
            }
        }
        return totalProfit;
    }

    @Override
    public double getTotalInvestAmount(User investor) {
        double totalAmount = 0;
        List<LoanTrade> trades = loanTradeDAO.getLoanTrade(investor, 0, Integer.MAX_VALUE);
        for (LoanTrade trade : trades) {
            totalAmount += trade.getBalance();
        }
        return totalAmount;
    }

    @Override
    public Page<MyDebt> getMyDebt(User debtor, Integer page) {
        final int pageSize = 10;
        int pos = pageSize * (page - 1);
        long debtCount = loanTradeDAO.debtCount(debtor);
        Page<MyDebt> myDebt = new Page<>(page, pageSize, debtCount);

        List<LoanTrade> trades = loanTradeDAO.getDebtTrade(debtor, pos, pageSize);
        for (LoanTrade trade : trades) {
            LoanApplicationForm laf = trade.getForm();
            MyDebt entity = new MyDebt(laf.getTitle(), laf.getRefundDate(), laf.getRefundBalance(),
                    trade.getInvestor().getNick(), trade.getProfit(), trade.getQuantity());
            myDebt.addContent(entity);
        }
        return myDebt;
    }

    @Override
    public Page<MyDebt> getAllMyDebt(Integer page) {
        final int pageSize = 10;
        int pos = pageSize * (page - 1);
        long debtCount = loanTradeDAO.count();
        Page<MyDebt> myDebt = new Page<>(page, pageSize, debtCount);

        List<LoanTrade> trades = loanTradeDAO.getLoanTrade(pos, pageSize);
        for (LoanTrade trade : trades) {
            LoanApplicationForm laf = trade.getForm();
            MyDebt entity = new MyDebt(laf.getTitle(), laf.getRefundDate(), laf.getRefundBalance(),
                    trade.getInvestor().getNick(), trade.getProfit(), trade.getQuantity());
            entity.setDebtor(laf.getUser().getNick());
            myDebt.addContent(entity);
        }
        return myDebt;
    }
}
