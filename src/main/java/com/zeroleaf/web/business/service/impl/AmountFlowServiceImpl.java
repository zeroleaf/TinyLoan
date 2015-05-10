package com.zeroleaf.web.business.service.impl;

import com.zeroleaf.web.business.service.AmountFlowService;
import com.zeroleaf.web.business.service.Page;
import com.zeroleaf.web.business.service.dto.InvestRecord;
import com.zeroleaf.web.domain.dao.AmountFlowDAO;
import com.zeroleaf.web.model.AmountFlow;
import com.zeroleaf.web.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by zeroleaf on 2015/5/5.
 */
@Service @Transactional
public class AmountFlowServiceImpl implements AmountFlowService {

    @Resource
    private AmountFlowDAO amountFlowDAO;

    @Override
    public Page<AmountFlow> getPage(User user, int page) {
        final int pageSize = 5;
        int pos = pageSize * (page - 1);
        long totalNumber = amountFlowDAO.count(user.getId());
        Page<AmountFlow> r = new Page<>(page, pageSize, totalNumber);
        r.addContent(amountFlowDAO.getRange(user.getId(), pos, pageSize));
        return r;
    }

    @Override
    public Page<AmountFlow> getProfit(User investor, int page) {
        final int pageSize = 5;
        int pos = pageSize * (page - 1);
        long totalNumber = amountFlowDAO.count(investor.getId());
        Page<AmountFlow> r = new Page<>(page, pageSize, totalNumber);
        r.addContent(amountFlowDAO.getProfitRange(investor.getId(), pos, pageSize));
        return r;
    }
}
