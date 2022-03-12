package com.kradwan.codegeneartormvvmsample.data.repository.account

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginRequest
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginResponse
import com.kradwan.codegeneartormvvmsample.data.model.general.getCountries.GetCountriesResponse
import com.kradwan.codegeneartormvvmsample.data.repository.account.datasource.AccountRemoteDataSource
import com.kradwan.codegeneartormvvmsample.domain.NetworkBoundResource
import com.kradwan.codegeneartormvvmsample.domain.repository.AccountRepository
import com.kradwan.codegeneartormvvmsample.domain.usecase.RequestSetting
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountViewState
import com.kradwan.codegeneartormvvmsample.presentation._base.DataState
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    val accountRemoteDataSource: AccountRemoteDataSource,
    val sharedPreferences: SharedPreferences,
    val sharedPrefsEditor: SharedPreferences.Editor,
    val context: Context
) : AccountRepository() {


    override suspend fun login(request: LoginRequest): LiveData<DataState<AccountViewState>> {

        return NetworkBoundResource.createRequest<LoginResponse, AccountViewState> {
            this.createCallFun = accountRemoteDataSource.login(request)
            this.handleApiSuccessResponse = { it -> done(AccountViewState(loginResponse = it)) }
        }

    }

    override suspend fun getCountries(request: RequestSetting<Nothing>): LiveData<DataState<AccountViewState>> {

//        delay(3000)
        return NetworkBoundResource.createRequest<GetCountriesResponse, AccountViewState> {
            this.createCallFun = accountRemoteDataSource.getCountries()
            this.handleApiSuccessResponse = { it ->
                done(AccountViewState(GetCountriesResponse = it))
            }
        }
    }

    override suspend fun login2(request: RequestSetting<LoginRequest>): LiveData<DataState<AccountViewState>> {
        return NetworkBoundResource.createRequest<GetCountriesResponse, AccountViewState> {
            this.createCallFun = accountRemoteDataSource.getCountries()
            this.handleApiSuccessResponse = { it ->
                done(AccountViewState(GetCountriesResponse = it))
            }
        }
    }

    fun <T> NetworkBoundResource<T, AccountViewState>.done(
        state: AccountViewState,
        cancel: Boolean = true
    ) {
        onCompleteJob(
            DataState.data(
                data = state,
            ),
            cancel = cancel
        )
    }

}