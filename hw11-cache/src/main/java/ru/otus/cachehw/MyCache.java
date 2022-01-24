package ru.otus.cachehw;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private final List<HwListener<K,V>> listeners = new ArrayList<>();
    private final WeakHashMap<K,V> cache = new WeakHashMap<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        listeners.forEach(listener -> listener.notify(key, value, "put to cache"));
    }

    @Override
    public void remove(K key) {
        var value = cache.get(key);
        cache.remove(key);
        listeners.forEach(listener -> listener.notify(key, value, "remove from cache"));
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public String toString() {
        return "cache=" + cache.size();
    }
}
