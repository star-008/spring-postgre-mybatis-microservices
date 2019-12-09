package com.tianwen.springcloud.scoreapi.service.util.cache;

import java.util.HashMap;

/**
 * Created by kimchh on 11/7/2018.
 */
public abstract class CacheService<T> extends HashMap<String, T> {

    protected static final int INITIAL_CAPACITY = 1000;

    public CacheService() {
        super(INITIAL_CAPACITY);
    }

    public String generatePrefixedKey(String prefix, String key) {
        return prefix + "-" + key;
    }

    public T put(String prefix, String key, T value) {
        return put(generatePrefixedKey(prefix, key), value);
    }

    public T get(String prefix, String key) {
        return get(generatePrefixedKey(prefix, key));
    }

    public T remove(String prefix, String key) {
        return remove(generatePrefixedKey(prefix, key));
    }
}
