package com.kradwan.codegeneartormvvmsample.domain.repository

import androidx.lifecycle.LiveData
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginRequest
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountViewState
import com.kradwan.codegeneartormvvmsample.presentation.state.DataState

//abstract class AccountRepository() : SuperRepository() {
interface AccountRepository {


     fun login(request: LoginRequest): LiveData<DataState<AccountViewState>>
     fun getCountries(): LiveData<DataState<AccountViewState>>


}