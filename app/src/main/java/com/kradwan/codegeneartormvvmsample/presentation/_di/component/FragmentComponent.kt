package com.kradwan.codegeneartormvvmsample.presentation._di.component

import com.kradwan.codegeneartormvvmsample.presentation._di.module.DomainModule
import com.kradwan.codegeneartormvvmsample.presentation._di.scope.FragmentScope
import dagger.Subcomponent


@Subcomponent(modules = [DomainModule::class])
@FragmentScope
interface FragmentComponent {
    //fun inject(loginFragment: LoginFragment)
    //fun inject(movieDetailFragment: MovieDetailFragment)
}
