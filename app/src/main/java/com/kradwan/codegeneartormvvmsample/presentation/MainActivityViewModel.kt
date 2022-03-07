package com.kradwan.codegeneartormvvmsample.presentation

import androidx.lifecycle.LiveData
import com.kradwan.codegeneartormvvmsample.domain.usecase.RequestSetting
import com.kradwan.codegeneartormvvmsample.domain.usecase.account.LoginUseCase
import com.kradwan.codegeneartormvvmsample.domain.usecase.general.GetCountriesUseCase
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountStateEvent
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountViewState
import com.kradwan.codegeneartormvvmsample.presentation.base.BaseViewModel
import com.kradwan.codegeneartormvvmsample.presentation.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    val getCountries: GetCountriesUseCase,
) : BaseViewModel<AccountStateEvent, AccountViewState>() {


    override fun handleStateEvent(stateEvent: AccountStateEvent): LiveData<DataState<AccountViewState>> {
        return when (stateEvent) {
            is AccountStateEvent.Login -> loginUseCase.login(stateEvent.request)
            is AccountStateEvent.GetCountries -> getCountries.execute()
        }

    }


    override fun initNewViewState(): AccountViewState {
        return AccountViewState()
    }
}