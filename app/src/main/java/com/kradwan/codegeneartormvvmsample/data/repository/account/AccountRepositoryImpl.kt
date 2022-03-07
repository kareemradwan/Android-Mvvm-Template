package com.kradwan.codegeneartormvvmsample.data.repository.account

import android.content.Context
import android.content.SharedPreferences
import androidx.collection.LruCache
import androidx.lifecycle.LiveData
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginRequest
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginResponse
import com.kradwan.codegeneartormvvmsample.data.model.general.getCountries.GetCountriesResponse
import com.kradwan.codegeneartormvvmsample.data.repository.account.datasource.AccountRemoteDataSource
import com.kradwan.codegeneartormvvmsample.domain.NetworkBoundResource
import com.kradwan.codegeneartormvvmsample.domain.repository.AccountRepository
import com.kradwan.codegeneartormvvmsample.domain.usecase.RequestSetting
import com.kradwan.codegeneartormvvmsample.domain.util.ApiSuccessResponse
import com.kradwan.codegeneartormvvmsample.domain.util.GenericApiResponse
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountStateEvent
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
                val response = accountRemoteDataSource.login(request)
                return response
            }

            //            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<LoginResponse>) {
            override suspend fun handleApiSuccessResponse(data: LoginResponse) {
                done(AccountViewState(loginResponse = data))
            }

        }.asLiveData()
    }



    override fun getCountries(): LiveData<DataState<AccountViewState>> {
        return object : NetworkBoundResource<GetCountriesResponse, Any, AccountViewState>(
            "getCountries",
            fromCache = true
        ) {

            override suspend fun createCall(): LiveData<GenericApiResponse<GetCountriesResponse>> {
                return accountRemoteDataSource.getCountries()
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