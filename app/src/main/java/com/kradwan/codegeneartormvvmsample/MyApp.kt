package com.kradwan.codegeneartormvvmsample

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    companion object {


        
        private var instance: MyApp? = null

        fun appContext(): Context = instance!!.applicationContext


    }
}