package com.nishank.cache.strategy.impl;

import com.nishank.cache.strategy.contract.CacheStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nishank Gupta on 06-May-18.
 */
public class LFUCacheStrategy implements CacheStrategy {

    Map<Object, Integer> keys = new HashMap<>();

    @Override
    public void track(Object obj) {
        synchronized (keys) {
            Integer value = keys.get(obj);
            value = value == null ? 1 : value + 1;
            keys.put(obj, value);
        }
    }

    @Override
    public void untrack(Object obj) {
        synchronized (keys) {
            keys.remove(obj);
        }
    }

    @Override
    public Object expire() {
        Object result = null;
        Integer value = null;

        synchronized (keys) {
            for (Map.Entry<Object, Integer> entry : keys.entrySet()) {
                if (value == null) {
                    value = entry.getValue();
                    result = entry.getKey();
                    continue;
                }

                if (value > entry.getValue()) {
                    value = entry.getValue();
                    result = entry.getKey();
                }
            }
        }

        return result;
    }

    @Override
    public void print() {
        for (Map.Entry<Object, Integer> entry : keys.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ",Value: " + entry.getValue());
        }
    }
}
