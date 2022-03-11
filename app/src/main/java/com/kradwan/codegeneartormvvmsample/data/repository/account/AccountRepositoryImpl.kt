package com.kradwan.codegeneartormvvmsample.data.repository.account

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginRequest
import com.kradwan.codegeneartormvvmsample.data.model.general.getCountries.GetCountriesResponse
import com.kradwan.codegeneartormvvmsample.data.repository.account.datasource.AccountRemoteDataSource
import com.kradwan.codegeneartormvvmsample.domain.NetworkBoundResource
import com.kradwan.codegeneartormvvmsample.domain.repository.AccountRepository
import com.kradwan.codegeneartormvvmsample.domain.usecase.RequestSetting
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountViewState
import com.kradwan.codegeneartormvvmsample.presentation.state.DataState
import kotlinx.coroutines.delay
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    val accountRemoteDataSource: AccountRemoteDataSource,
    val sharedPreferences: SharedPreferences,
    val sharedPrefsEditor: SharedPreferences.Editor,
    val context: Context
) : AccountRepository() {


    override suspend fun login(request: LoginRequest): LiveData<DataState<AccountViewState>> {

        return liveData {

        }
//      return  NetworkBoundResource<LoginResponse, AccountViewState>("", RequestMeta(), network = {
//            accountRemoteDataSource.login(request)
//        }, handleApiSuccess = {
//            Log.d("DDDD", "handleApiSuccess: $it")
//        }).asLiveData()
//
//        return object : NetworkBoundResource<LoginResponse, AccountViewState>("dsa", RequestMeta()) {
//
//            init {
//                suspend {
//                    init()
//                }.createCoroutine()
//            }
//
//            override suspend fun createCall(): LiveData<GenericApiResponse<LoginResponse>> {
//                return accountRemoteDataSource.login(request)
//            }
//
//            override suspend fun handleApiSuccessResponse(data: LoginResponse) {
//                done(AccountViewState(loginResponse = data))
//            }
//
//        }.asLiveData()
//    }
    }

    override suspend fun getCountries(request: RequestSetting<Nothing>): LiveData<DataState<AccountViewState>> {

//        delay(3000)
        return NetworkBoundResource.createRequest<GetCountriesResponse, AccountViewState> {
            this.createCallFun = accountRemoteDataSource.getCountries()
            this.handleApiSuccessResponse =
                { it -> done(AccountViewState(GetCountriesResponse = it)) }
        }.asLiveData()
    }

    override suspend fun login2(request: RequestSetting<LoginRequest>): LiveData<DataState<AccountViewState>> {

//        return liveData<DataState<AccountViewState>> {
//            val response = accountRemoteDataSource.getCountries()
//            emit(DataState.data(AccountViewState(GetCountriesResponse = response.value?.body())))
//        }
        return liveData {

        }

//        return NetworkBoundResource<GetCountriesResponse, AccountViewState>(
//            "",
//            RequestMeta(),
//            network = {
//                accountRemoteDataSource.getCountries()
//            },
//            handleApiSuccess = {
//                Log.d("DDDD", "handleApiSuccess: $it")
//            }).asLiveData()
//        return object : NetworkBoundResource<GetCountriesResponse, AccountViewState>(
//            "getCountries2",
//            request.meta()
//        ) {
//
//            override suspend fun createCall(): LiveData<GenericApiResponse<GetCountriesResponse>> {
//                delay(3000)
//                return accountRemoteDataSource.getCountries()
//            }
//
//            override suspend fun fromDB() {
//                done(
//                    AccountViewState(GetCountriesResponse = GetCountriesResponse("from DB")),
//                    false
//                )
//            }
//
//            override suspend fun handleApiSuccessResponse(data: GetCountriesResponse) {
//                done(AccountViewState(GetCountriesResponse = data))
//            }
//
//
//        }.asLiveData()
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