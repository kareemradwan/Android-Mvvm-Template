package com.kradwan.codegeneartormvvmsample.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.kradwan.codegeneartormvvmsample.data.api.account.AccountApi
import com.kradwan.codegeneartormvvmsample.data.repository.account.AccountRepositoryImpl
import com.kradwan.codegeneartormvvmsample.data.repository.account.datasourceImpl.AccountRemoteDataSourceImpl
import com.kradwan.codegeneartormvvmsample.domain.repository.AccountRepository
import com.kradwan.codegeneartormvvmsample.domain.usecase.account.LoginUseCase
import com.kradwan.codegeneartormvvmsample.domain.usecase.general.GetCountriesUseCase
import com.kradwan.codegeneartormvvmsample.domain.util.LiveDataCallAdapterFactory
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountStateEvent
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountViewState
import com.kradwan.codegeneartormvvmsample.presentation.base.BaseViewModel
import com.kradwan.codegeneartormvvmsample.presentation.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val loginUseCase: LoginUseCase,
    val getCountries: GetCountriesUseCase
) : BaseViewModel<AccountStateEvent, AccountViewState>() {


//    fun provideClient(): OkHttpClient {
//
//        val interceptor = run {
//            val httpLoggingInterceptor = HttpLoggingInterceptor()
//            httpLoggingInterceptor.apply {
//                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
//            }
//        }
//        return OkHttpClient.Builder()
//            .addNetworkInterceptor(interceptor)
//            .addInterceptor(interceptor)
//            .readTimeout(60, TimeUnit.SECONDS)
//            .writeTimeout(60, TimeUnit.SECONDS)
//            .build()
//
//    }

//    val loginUseCase = LoginUseCase(repo)
//    val getCountries = GetCountriesUseCase(repo)

    override fun handleStateEvent(stateEvent: AccountStateEvent): LiveData<DataState<AccountViewState>> {
        return when (stateEvent) {
            is AccountStateEvent.Login -> loginUseCase.login(stateEvent.request)
            is AccountStateEvent.GetCountries -> getCountries.execute()
        }

    }

    override fun initNewViewState(): AccountViewState {
        return AccountViewState()
    }
}