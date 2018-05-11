package com.nishank.cache;

import com.nishank.cache.base.Cache;
import com.nishank.cache.strategy.impl.LFUCacheStrategy;
import com.nishank.cache.strategy.impl.LRUCacheStrategy;

/**
 * Created by Nishank Gupta on 06-May-18.
 */
public class CacheTest {

    public static void main(String[] args) {
        Cache<Integer, Integer> LRUCache = new Cache(5, new LRUCacheStrategy());
        System.out.println("LRUCache");
        addToCache(LRUCache, 1, 5);
        LRUCache.get(1);
        LRUCache.get(2);
        addToCache(LRUCache, 6, 2);


        Cache<Integer, Integer> LFUCache = new Cache(5, new LFUCacheStrategy());
        System.out.println("LFUCache");
        addToCache(LFUCache, 1, 5);
        LFUCache.get(3);
        LFUCache.get(4);
        addToCache(LFUCache, 6, 2);
    }

    private static void addToCache(Cache<Integer, Integer> cache, int start, int count) {
        Integer value = start;
        while (count > 0) {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            cache.add(value, value);
            ++value;
            --count;
        }
    }
}
