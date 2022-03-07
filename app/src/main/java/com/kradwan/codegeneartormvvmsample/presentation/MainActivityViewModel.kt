package com.kradwan.codegeneartormvvmsample.presentation

import androidx.lifecycle.LiveData
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginRequest
import com.kradwan.codegeneartormvvmsample.domain.usecase.account.LoginUseCase
import com.kradwan.codegeneartormvvmsample.domain.usecase.account.LoginUseCase2
import com.kradwan.codegeneartormvvmsample.domain.usecase.general.GetCountriesUseCase
import com.kradwan.codegeneartormvvmsample.domain.usecase.requestSetting
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountStateEvent
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountViewState
import com.kradwan.codegeneartormvvmsample.presentation.base.BaseViewModel
import com.kradwan.codegeneartormvvmsample.presentation.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val login2UseCase: LoginUseCase2,
    val getCountries: GetCountriesUseCase,
) : BaseViewModel<AccountStateEvent, AccountViewState>() {


    override fun handleStateEvent(stateEvent: AccountStateEvent): LiveData<DataState<AccountViewState>> {
        return when (stateEvent) {
            is AccountStateEvent.Login -> loginUseCase.login(stateEvent.request)
            is AccountStateEvent.GetCountries -> getCountries.execute()
            is AccountStateEvent.Login2 -> login2UseCase.login(request = stateEvent.request)
        }
    }

    fun login(username: String, password: String) {
        setStateEvent(
            AccountStateEvent.Login2(
                requestSetting {
                    meta {
                        metaShowLoading = true
                        metaFromNetwork = true
                        metaSaveResponse = true
                    }
                    settingData = LoginRequest("dsa", "")
                }
            )
        )
    }


    override fun initNewViewState(): AccountViewState {
        return AccountViewState()
    }
}