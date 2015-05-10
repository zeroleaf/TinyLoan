package com.zeroleaf.web.business.service.dto;

import java.util.*;

/**
 * Created by zeroleaf on 2015/5/11.
 */
public class InvestAnalysis implements Iterable<Integer> {

    private Map<Integer, Integer> keyValues = new HashMap<>();
    private int totalCount = 0;

    @Override
    public Iterator<Integer> iterator() {
        return keyValues.keySet().iterator();
    }

    public boolean isEmpty() {
        return keyValues.isEmpty();
    }

    public void tickKey(Integer key) {
        ++totalCount;
        Integer value = keyValues.get(key);
        if (value == null) {
            keyValues.put(key, 1);
        } else {
            ++value;
        }
    }

    public String percentage(Integer key) {
        if (keyValues.get(key) == null) {
            throw new IllegalArgumentException("不存在 key " + key + " 对应的值");
        }

        return String.format("%.2f%%", (double) keyValues.get(key) / totalCount * 100);
    }
}
