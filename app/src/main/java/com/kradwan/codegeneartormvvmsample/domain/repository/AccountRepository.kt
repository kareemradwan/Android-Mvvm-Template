package com.kradwan.codegeneartormvvmsample.domain.repository

import androidx.lifecycle.LiveData
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginRequest
import com.kradwan.codegeneartormvvmsample.data.repository.SuperRepository
import com.kradwan.codegeneartormvvmsample.domain.usecase.RequestSetting
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountViewState
import com.kradwan.codegeneartormvvmsample.presentation._base.DataState

abstract class AccountRepository : SuperRepository() {


    abstract suspend fun login(request: LoginRequest): LiveData<DataState<AccountViewState>>
    abstract suspend fun login2(request: RequestSetting<LoginRequest>): LiveData<DataState<AccountViewState>>


    abstract suspend fun getCountries(request: RequestSetting<Nothing> = RequestSetting()): LiveData<DataState<AccountViewState>>


}