package com.kradwan.codegeneartormvvmsample.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kradwan.codegeneartormvvmsample.domain.util.*
import com.kradwan.codegeneartormvvmsample.presentation._base.DataState
import kotlinx.coroutines.*


open class NetworkBoundResource<ResponseObject, ViewStateType> {

    companion object {
        suspend fun <T, B> createRequest(init: suspend (NetworkBoundResource<T, B>.() -> Unit)): LiveData<DataState<B>> {
            val setting = NetworkBoundResource<T, B>()
            setting.init()
            setting.startWork()
            return setting.asLiveData()
        }

    }

    private val result = MediatorLiveData<DataState<ViewStateType>>()

    fun setValue(dataState: DataState<ViewStateType>) {

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main) {
                result.value = dataState
            }
        }
    }

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>


    open fun validate(): Boolean {
        return true
    }

    public suspend fun startWork() {
        if (!validate()) {
            return
        }


        setValue(DataState.loading(isLoading = true, cachedData = null))

        val apiResponse = createCallFun
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.Main) {
                    handleNetworkCall(response)
                }
            }

        }


    }

    private fun handleNetworkCall(response: GenericApiResponse<ResponseObject>) {
        when (response) {
            is ApiSuccessResponse -> {
                val body = response.body
                handleApiSuccessResponse(body)
            }
            is ApiErrorResponse -> {
                onErrorReturn(response.errorMessage, true, false)
            }
            is ApiEmptyResponse -> {
                onErrorReturn("HTTP 204. Returned NOTHING.", false, true)
            }
        }
    }


    open fun onErrorReturn(
        errorMessage: String?,
        shouldUseDialog: Boolean,
        shouldUseToast: Boolean
    ) {
        onCompleteJob(DataState.error(errorMessage ?: "error msg"))
    }


    open fun onCompleteJob(dataState: DataState<ViewStateType>, cancel: Boolean = true) {
        setValue(dataState)
    }


    lateinit var handleApiSuccessResponse: (ResponseObject) -> Unit
    lateinit var createCallFun: LiveData<GenericApiResponse<ResponseObject>>

    open suspend fun fromDB() {}


}