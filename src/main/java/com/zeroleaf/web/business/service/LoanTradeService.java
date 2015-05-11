package com.zeroleaf.web.business.service;

import com.zeroleaf.web.business.service.dto.InvestAnalysis;
import com.zeroleaf.web.business.service.dto.InvestRecord;
import com.zeroleaf.web.business.service.dto.MyDebt;
import com.zeroleaf.web.model.User;

/**
 * Created by zeroleaf on 2015/5/10.
 */
public interface LoanTradeService {

    /**
     * 投资记录.
     *
     * @param investor 投资者
     * @param page
     * @return
     */
    Page<InvestRecord> getInvestRecord(User investor, int page);

    /**
     * 投资结构分析.
     *
     * @param investor 投资者.
     * @return
     */
    InvestAnalysis getInvestAnalysis(User investor);

    /**
     * 累计收益.
     *
     * @param investor 投资者.
     * @return
     */
    double getTotalInvestProfit(User investor);

    double getTotalInvestAmount(User investor);

    Page<MyDebt> getMyDebt(User debtor, Integer page);
}
