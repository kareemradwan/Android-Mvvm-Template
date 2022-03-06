package com.kradwan.codegeneartormvvmsample.presentation._di.component

import com.kradwan.codegeneartormvvmsample.MyApp
import com.kradwan.codegeneartormvvmsample.presentation._di.module.ApplicationModule
import com.kradwan.codegeneartormvvmsample.presentation._di.scope.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector

@Component(modules = [ApplicationModule::class])
@ApplicationScope
interface ApplicationComponent : AndroidInjector<MyApp> {
    override fun inject(application: MyApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MyApp): Builder

        fun build(): ApplicationComponent
    }
}
