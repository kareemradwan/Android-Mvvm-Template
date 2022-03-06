package com.kradwan.codegeneartormvvmsample.presentation._di.account

import com.kradwan.codegeneartormvvmsample.data.api.account.AccountApi
import com.kradwan.codegeneartormvvmsample.data.repository.account.datasource.AccountRemoteDataSource
import com.kradwan.codegeneartormvvmsample.data.repository.account.datasourceImpl.AccountRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AccountModule {

    @Singleton
    @Provides
    fun provideAccountApiService(retrofitBuilder: Retrofit.Builder): AccountApi {
        return retrofitBuilder
            .build()
            .create(AccountApi::class.java)
    }
//
//    @Singleton
//    @Provides
//    fun provideAccountRemoteDataSource(service: AccountApi): AccountRemoteDataSource {
//        return AccountRemoteDataSourceImpl(
//            service
//        )
//    }
}