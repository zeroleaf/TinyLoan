package com.zeroleaf.web.domain.dao;

import com.zeroleaf.web.model.LoanTrade;
import com.zeroleaf.web.model.User;

import java.util.List;

/**
 * Created by zeroleaf on 2015/5/10.
 */
public interface LoanTradeDAO {

    long count(User investor);

    List<LoanTrade> getLoanTrade(User investor, int pos, int limit);

    long debtCount(User debtor);

    List<LoanTrade> getDebtTrade(User debtor, int pos, int limit);

}
