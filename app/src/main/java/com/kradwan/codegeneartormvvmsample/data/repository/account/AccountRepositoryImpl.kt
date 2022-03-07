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
import com.kradwan.codegeneartormvvmsample.domain.util.GenericApiResponse
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountViewState
import com.kradwan.codegeneartormvvmsample.presentation.state.DataState
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    val accountRemoteDataSource: AccountRemoteDataSource,
    val sharedPreferences: SharedPreferences,
    val sharedPrefsEditor: SharedPreferences.Editor,
    val context: Context
) : AccountRepository() {


    override fun login(request: LoginRequest): LiveData<DataState<AccountViewState>> {

        return object : NetworkBoundResource<LoginResponse, Any, AccountViewState>("login") {

            override suspend fun createCall(): LiveData<GenericApiResponse<LoginResponse>> {
                return accountRemoteDataSource.login(request)
            }

            override suspend fun fromDB() {
                done(AccountViewState(loginResponse = LoginResponse("dsa")))
            }

            override suspend fun handleApiSuccessResponse(data: LoginResponse) {
                done(AccountViewState(loginResponse = data))
            }

        }.asLiveData()
    }


    override fun getCountries(request: RequestSetting<Nothing>): LiveData<DataState<AccountViewState>> {
        return object : NetworkBoundResource<GetCountriesResponse, Any, AccountViewState>(
            "getCountries",
            request.meta()
        ) {

            override suspend fun createCall(): LiveData<GenericApiResponse<GetCountriesResponse>> {
                return accountRemoteDataSource.getCountries()
            }

            override suspend fun handleApiSuccessResponse(data: GetCountriesResponse) {
                done(AccountViewState(GetCountriesResponse = data))
            }

        }.asLiveData()
    }

    override fun login2(request: RequestSetting<LoginRequest>): LiveData<DataState<AccountViewState>> {
        return object : NetworkBoundResource<GetCountriesResponse, Any, AccountViewState>(
            "getCountries2",
            request.meta()
        ) {

            override suspend fun createCall(): LiveData<GenericApiResponse<GetCountriesResponse>> {
                return accountRemoteDataSource.getCountries()
            }

            override suspend fun fromDB() {
                done(AccountViewState(GetCountriesResponse = GetCountriesResponse("from DB")))
            }
            override suspend fun handleApiSuccessResponse(data: GetCountriesResponse) {
                done(AccountViewState(GetCountriesResponse = data))
            }

        }.asLiveData()
    }

    fun <T> NetworkBoundResource<T, Any, AccountViewState>.done(state: AccountViewState) {
        onCompleteJob(
            DataState.data(
                data = state
            )
        )
    }

}