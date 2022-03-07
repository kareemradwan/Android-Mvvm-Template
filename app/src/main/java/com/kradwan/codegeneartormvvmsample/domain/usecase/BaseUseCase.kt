package com.kradwan.codegeneartormvvmsample.domain.usecase

import androidx.lifecycle.LiveData
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountStateEvent
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountViewState
import com.kradwan.codegeneartormvvmsample.presentation.state.DataState


class RequestMeta {
    var showLoading: Boolean = true
    var fromNetwork: Boolean = false
    var fromCache: Boolean = false
    var fromDB: Boolean = false
    var stopIfFoundResult = false

}


class RequestSetting<T> {

    lateinit var meta: RequestMeta
    var event: T? = null


}

fun meta(init: RequestMeta.() -> Unit): RequestMeta {
    val meta = RequestMeta()
    meta.init()
    return meta
}

fun <T> requestSetting(init: RequestSetting<T>.() -> Unit): RequestSetting<T> {
    val setting = RequestSetting<T>()
    setting.init()
    return setting
}

fun <T> defaultRequestSetting(action: T?): RequestSetting<T> {
    return requestSetting {
        meta {
            fromNetwork = true
            stopIfFoundResult = true
        }
        event = action
    }
}

interface BaseUseCase<A : Any> {

    fun  execute(request: RequestSetting<A>) : LiveData<DataState<AccountViewState>>
}
