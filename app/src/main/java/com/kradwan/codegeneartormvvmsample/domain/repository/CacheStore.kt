package com.kradwan.codegeneartormvvmsample.domain.repository

import android.util.LruCache

class CacheStore {

    private val cache: LruCache<String, Any> = LruCache(10)

    fun save(key: String, value: Any) {
        cache.put(key, value)
    }

    @SuppressWarnings("unchecked")
    fun <T> get(key: String): T {
        return cache.get(key) as T
    }

    @SuppressWarnings("unchecked")
    fun <T> getOrNull(key: String): T? {
        (cache.get(key) as? T)?.let { return it } ?: return null

    }
}