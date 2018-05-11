package com.nishank.cache.base;

import com.nishank.cache.strategy.contract.CacheStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nishank Gupta on 06-May-18.
 */
public class Cache<K, V> {

    int threshold = 10;
    int counter = 0;
    CacheStrategy cacheStrategy;
    Map<K, V> data;

    public Cache(CacheStrategy cacheStrategy) {
        this.cacheStrategy = cacheStrategy;
        data = new HashMap<K, V>();
    }

    public Cache(int threshold, CacheStrategy cacheStrategy) {
        this(cacheStrategy);
        this.threshold = threshold;
    }

    public void add(K key, V value) {
        synchronized (data) {
            if (data.get(key) != null) {
                data.put(key, value);
                cacheStrategy.track(key);
                return;
            }

            while (counter >= threshold) {
                K cacheKey = (K) cacheStrategy.expire();
                remove(cacheKey);
                cacheStrategy.print();
                System.out.println("Removed Key: " + cacheKey);
            }

            data.put(key, value);
            ++counter;
            cacheStrategy.track(key);

        }
    }

    public V get(K key) {
        V result = (V) data.get(key);
        cacheStrategy.track(key);

        return result;
    }

    public void remove(K key) {
        synchronized (data) {
            data.remove(key);
            counter--;
            cacheStrategy.untrack(key);
        }
    }
}
