package com.nishank.cache.strategy.impl;

import com.nishank.cache.strategy.contract.CacheStrategy;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nishank Gupta on 06-May-18.
 */
public class LRUCacheStrategy implements CacheStrategy {

    Map<Object, Date> keys = new HashMap<>();

    @Override
    public void track(Object obj) {
        synchronized (keys) {
            keys.put(obj, new Date());
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
        Date date = null;

        synchronized (keys) {
            for (Map.Entry<Object, Date> entry : keys.entrySet()) {
                if (date == null) {
                    date = entry.getValue();
                    result = entry.getKey();
                    continue;
                }

                if (date.after(entry.getValue())) {
                    date = entry.getValue();
                    result = entry.getKey();
                }
            }
        }

        return result;
    }

    @Override
    public void print() {
        for (Map.Entry<Object, Date> entry : keys.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ",Value: " + entry.getValue());
        }
    }
}
