package com.kradwan.codegeneartormvvmsample.presentation._di.module

import com.kradwan.codegeneartormvvmsample.data.api.account.AccountApi
import com.kradwan.codegeneartormvvmsample.data.repository.account.datasource.AccountRemoteDataSource
import com.kradwan.codegeneartormvvmsample.data.repository.account.datasourceImpl.AccountRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DataModule {


    @Singleton
    @Provides
    fun provideAccountRemoteDataSource(service: AccountApi): AccountRemoteDataSource {
        return AccountRemoteDataSourceImpl(service)
    }


}