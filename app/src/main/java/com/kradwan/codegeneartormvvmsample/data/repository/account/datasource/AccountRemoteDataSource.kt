package com.kradwan.codegeneartormvvmsample.data.repository.account.datasource

import androidx.lifecycle.LiveData
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginRequest
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginResponse
import com.kradwan.codegeneartormvvmsample.data.model.general.getCountries.GetCountriesResponse
import com.kradwan.codegeneartormvvmsample.domain.util.GenericApiResponse
import okhttp3.ResponseBody
import retrofit2.Call

interface AccountRemoteDataSource {

    suspend fun login(request: LoginRequest): LiveData<GenericApiResponse<LoginResponse>>
    suspend fun getCountries(): LiveData<GenericApiResponse<GetCountriesResponse>>

}