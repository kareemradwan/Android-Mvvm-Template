package com.kradwan.codegeneartormvvmsample.data.repository.account.datasourceImpl

import androidx.lifecycle.LiveData
import com.kradwan.codegeneartormvvmsample.data.api.account.AccountApi
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginRequest
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginResponse
import com.kradwan.codegeneartormvvmsample.data.model.general.getCountries.GetCountriesResponse
import com.kradwan.codegeneartormvvmsample.data.repository.account.datasource.AccountRemoteDataSource
import com.kradwan.codegeneartormvvmsample.domain.util.GenericApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AccountRemoteDataSourceImpl  @Inject constructor( val service: AccountApi) : AccountRemoteDataSource {

    override suspend fun login(request: LoginRequest): LiveData<GenericApiResponse<LoginResponse>> {
//        delay(10_000)
        return service.login(request)
    }

    override suspend fun getCountries(): LiveData<GenericApiResponse<GetCountriesResponse>> {
//        delay(5000)
        return service.GetCountries()
    }

}