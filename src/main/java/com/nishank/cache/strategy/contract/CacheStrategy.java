package com.nishank.cache.strategy.contract;

/**
 * Created by Nishank Gupta on 06-May-18.
 */
public interface CacheStrategy {

    void track(Object obj);

    void untrack(Object obj);

    Object expire();

    void print();
}
