package com.zeroleaf.web.domain.dao;

import com.zeroleaf.web.model.AmountFlow;

import java.util.List;

/**
 * Created by zeroleaf on 2015/5/5.
 */
public interface AmountFlowDAO {

    List<AmountFlow> getRange(long userId, int pos, int limit);

    long count(long userId);

}
