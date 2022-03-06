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
import com.kradwan.codegeneartormvvmsample.domain.util.ApiSuccessResponse
import com.kradwan.codegeneartormvvmsample.domain.util.GenericApiResponse
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountViewState
import com.kradwan.codegeneartormvvmsample.presentation.state.DataState
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    val accountRemoteDataSource: AccountRemoteDataSource,
    val sharedPreferences: SharedPreferences,
    val sharedPrefsEditor: SharedPreferences.Editor,
    val context: Context
) :
    AccountRepository {

    private val cache: LruCache<String, Any> = LruCache(100)


    override fun login(request: LoginRequest): LiveData<DataState<AccountViewState>> {

        return object :
            NetworkBoundResource<LoginResponse, Any, AccountViewState>("login", fromCache = false) {


            override suspend fun fromCache() {
                done(AccountViewState(loginResponse = cache.get("login") as? LoginResponse))
            }

            override fun saveOnCache(body: LoginResponse) {
                cache.put(name, body)
            }

            //            override suspend fun fromDB(): LiveData<GenericApiResponse<LoginResponse>>? = null
            override suspend fun fromDB() {}

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
        return object :
            NetworkBoundResource<GetCountriesResponse, Any, AccountViewState>("getCountries") {

            init {
                cache.put(name, GetCountriesResponse("From Cache DataSource "))
            }


            override fun saveOnCache(body: GetCountriesResponse) {
                cache.put(name, body)
            }

            override suspend fun createCall(): LiveData<GenericApiResponse<GetCountriesResponse>> {
                val response = accountRemoteDataSource.getCountries()
                return response
            }

            override suspend fun handleApiSuccessResponse(data: GetCountriesResponse) {
                done(AccountViewState(GetCountriesResponse = data))
            }

            override suspend fun fromCache() {
                val data = cache.get(name) as? GetCountriesResponse
                done(AccountViewState(GetCountriesResponse = data))
            }

            //            override suspend fun fromDB(): LiveData<GenericApiResponse<GetCountriesResponse>>? {
            override suspend fun fromDB() {
                return
            }
//            override suspend fun fromDB(): LiveData<GenericApiResponse<GetCountriesResponse>>? = null

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