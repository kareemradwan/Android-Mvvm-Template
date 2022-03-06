package com.kradwan.codegeneartormvvmsample.presentation._di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.kradwan.codegeneartormvvmsample.data.repository.account.AccountRepositoryImpl
import com.kradwan.codegeneartormvvmsample.data.repository.account.datasource.AccountRemoteDataSource
import com.kradwan.codegeneartormvvmsample.domain.repository.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSharedPrefsEditor(sharedPreferences: SharedPreferences): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }



    @Provides
    fun providesAccountRepo(
        remoteAccountDataSource: AccountRemoteDataSource,
        sharedPreferences: SharedPreferences,
        sharedPrefsEditor: SharedPreferences.Editor,
        context: Context
    ): AccountRepository {
        return AccountRepositoryImpl(
            remoteAccountDataSource,
            sharedPreferences,
            sharedPrefsEditor,
            context
        )
    }


}