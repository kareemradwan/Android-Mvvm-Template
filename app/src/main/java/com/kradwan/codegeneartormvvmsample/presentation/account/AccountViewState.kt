package com.kradwan.codegeneartormvvmsample.presentation.account

import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginResponse
import com.kradwan.codegeneartormvvmsample.data.model.general.getCountries.GetCountriesResponse

data class AccountViewState(
    var loginResponse: LoginResponse? = null,
    var GetCountriesResponse : GetCountriesResponse?  = null,
)