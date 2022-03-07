package com.kradwan.codegeneartormvvmsample.presentation.account

import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginRequest
import com.kradwan.codegeneartormvvmsample.domain.usecase.RequestSetting

sealed class AccountStateEvent {

    class Login(val request: LoginRequest) : AccountStateEvent()
    class Login2(val request: RequestSetting<LoginRequest>) : AccountStateEvent()
    object GetCountries: AccountStateEvent()

}