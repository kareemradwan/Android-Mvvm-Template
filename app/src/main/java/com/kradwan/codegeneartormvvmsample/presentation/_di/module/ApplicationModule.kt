package com.kradwan.codegeneartormvvmsample.presentation._di.module

import dagger.Module

//
@Module(includes = [ApplicationModule.LoggerModule::class])
//@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Module
//    @InstallIn(SingletonComponent::class)
    interface LoggerModule {
        //@Binds
        //@ApplicationScope
        //fun bindLogger(loagger : TimberLogger) : Logger
    }
}
