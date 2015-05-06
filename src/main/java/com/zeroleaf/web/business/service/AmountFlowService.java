package com.zeroleaf.web.business.service;

import com.zeroleaf.web.model.AmountFlow;
import com.zeroleaf.web.model.User;

/**
 * Created by zeroleaf on 2015/5/5.
 */
public interface AmountFlowService {

    Page<AmountFlow> getPage(User user, int page);
}
