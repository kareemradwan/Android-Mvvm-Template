package com.kradwan.codegeneartormvvmsample.domain.repository

import androidx.lifecycle.LiveData
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginRequest
import com.kradwan.codegeneartormvvmsample.data.repository.SuperRepository
import com.kradwan.codegeneartormvvmsample.domain.usecase.RequestSetting
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountStateEvent
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountViewState
import com.kradwan.codegeneartormvvmsample.presentation.state.DataState

//abstract class AccountRepository() : SuperRepository() {
abstract class AccountRepository : SuperRepository() {


    abstract fun login(request: LoginRequest): LiveData<DataState<AccountViewState>>


    abstract fun getCountries(): LiveData<DataState<AccountViewState>>


}