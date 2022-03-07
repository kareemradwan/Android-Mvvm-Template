package com.kradwan.codegeneartormvvmsample.presentation._di.module

import androidx.collection.LruCache
import com.kradwan.codegeneartormvvmsample.domain.repository.CacheStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
//
//@Module
//@InstallIn(SingletonComponent::class)
//class NetworkBoundModule {
//
//    @Provides
//    fun provideLureCache(): CacheStore {
//        return CacheStore()
//    }
//}