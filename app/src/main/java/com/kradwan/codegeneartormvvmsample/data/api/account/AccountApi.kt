package com.kradwan.codegeneartormvvmsample.data.api.account

import androidx.lifecycle.LiveData
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginRequest
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginResponse
import com.kradwan.codegeneartormvvmsample.data.model.general.getCountries.GetCountriesRequest
import com.kradwan.codegeneartormvvmsample.data.model.general.getCountries.GetCountriesResponse
import com.kradwan.codegeneartormvvmsample.domain.util.GenericApiResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AccountApi {


    @POST("Account/Login")
    fun login(@Body request: LoginRequest): LiveData<GenericApiResponse<LoginResponse>>


    @POST("api/General/GetCountries")
    @Headers("lang:en")
    fun GetCountries(@Body request: GetCountriesRequest = GetCountriesRequest()): LiveData<GenericApiResponse<GetCountriesResponse>>

}