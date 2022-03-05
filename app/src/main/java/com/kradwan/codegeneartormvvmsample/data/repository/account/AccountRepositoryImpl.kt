package com.kradwan.codegeneartormvvmsample.data.repository.account

import androidx.lifecycle.LiveData
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginRequest
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginResponse
import com.kradwan.codegeneartormvvmsample.data.model.general.getCountries.GetCountriesResponse
import com.kradwan.codegeneartormvvmsample.data.repository.account.datasource.AccountRemoteDataSource
import com.kradwan.codegeneartormvvmsample.domain.NetworkBoundResource
import com.kradwan.codegeneartormvvmsample.domain.repository.AccountRepository
import com.kradwan.codegeneartormvvmsample.domain.util.ApiSuccessResponse
import com.kradwan.codegeneartormvvmsample.domain.util.GenericApiResponse
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountViewState
import com.kradwan.codegeneartormvvmsample.presentation.state.DataState
import kotlinx.coroutines.CoroutineScope

class AccountRepositoryImpl(
    scope: CoroutineScope,
    private val accountRemoteDataSource: AccountRemoteDataSource,
) : AccountRepository(scope = scope) {


    override fun login(request: LoginRequest): LiveData<DataState<AccountViewState>> {

        val result = object : NetworkBoundResource<LoginResponse, Any, AccountViewState>(scope) {

            override fun validate(): Boolean {
                if (request.email.isBlank()) {
                    onErrorReturn("email can't be empty", true, false)
                    return false
                }
                return true
            }

            override suspend fun createCall(): LiveData<GenericApiResponse<LoginResponse>> =
                accountRemoteDataSource.login(request)

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<LoginResponse>) {
                done(AccountViewState(loginResponse = response.body))
            }

        }.asLiveData()
        return result
    }

    override fun getCountries(): LiveData<DataState<AccountViewState>> {
        val result =
            object : NetworkBoundResource<GetCountriesResponse, Any, AccountViewState>(scope) {


                override suspend fun createCall(): LiveData<GenericApiResponse<GetCountriesResponse>> =
                    accountRemoteDataSource.getCountries()

                override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<GetCountriesResponse>) {
                    done(AccountViewState(GetCountriesResponse = response.body))
                }

            }.asLiveData()
        return result
    }


    fun <T> NetworkBoundResource<T, Any, AccountViewState>.done(state: AccountViewState) {
        onCompleteJob(
            DataState.data(
                data = state
            )
        )
    }

}