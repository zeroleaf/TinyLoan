package com.zeroleaf.web.business.service.impl;

import com.zeroleaf.web.business.service.LoanTradeService;
import com.zeroleaf.web.business.service.Page;
import com.zeroleaf.web.business.service.dto.InvestRecord;
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
}
