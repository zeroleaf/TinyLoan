package com.zeroleaf.web.business.service;

import com.zeroleaf.web.business.service.dto.InvestRecord;
import com.zeroleaf.web.model.AmountFlow;
import com.zeroleaf.web.model.User;

/**
 * Created by zeroleaf on 2015/5/5.
 */
public interface AmountFlowService {

    Page<AmountFlow> getPage(User user, int page);

    /**
     * @param investor
     * @param page
     * @return
     */
    Page<AmountFlow> getProfit(User investor, int page);
}
