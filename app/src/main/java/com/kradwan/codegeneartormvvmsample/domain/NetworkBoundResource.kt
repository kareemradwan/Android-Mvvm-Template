package com.kradwan.codegeneartormvvmsample.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kradwan.codegeneartormvvmsample.domain.util.*
import com.kradwan.codegeneartormvvmsample.presentation.state.DataState
import kotlinx.coroutines.*

abstract class NetworkBoundResource<ResponseObject, CacheObject, ViewStateType>(
    val scope: CoroutineScope,
    shouldDisplayLoading: Boolean = true
) {


    var job: Job? = null

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO + Job())
    protected val result = MediatorLiveData<DataState<ViewStateType>>()

    fun setValue(dataState: DataState<ViewStateType>) {
        result.value = dataState
    }

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>

    init {

//        if (validate()) {
        setValue(DataState.loading(isLoading = shouldDisplayLoading, cachedData = null))
        doNetworkRequest()
//        }
    }

    open fun validate(): Boolean {
        return true
    }

    private fun doNetworkRequest() {
        job = coroutineScope.launch {

            withContext(Dispatchers.Main) {
                // make network call
                val apiResponse = createCall()
                result.addSource(apiResponse) { response ->
                    result.removeSource(apiResponse)

                    scope.launch {
                        handleNetworkCall(response)
                    }
                }
            }
        }


    }

    private suspend fun handleNetworkCall(response: GenericApiResponse<ResponseObject>) {
        when (response) {
            is ApiSuccessResponse -> {
                handleApiSuccessResponse(response)
            }
            is ApiErrorResponse -> {
                onErrorReturn(response.errorMessage, true, false)
            }
            is ApiEmptyResponse -> {
                onErrorReturn("HTTP 204. Returned NOTHING.", false, true)
            }
        }
    }

    public fun onErrorReturn(
        errorMessage: String?,
        shouldUseDialog: Boolean,
        shouldUseToast: Boolean
    ) {
        onCompleteJob(DataState.error(errorMessage ?: "error msg"))
    }

    fun onCompleteJob(dataState: DataState<ViewStateType>) {
        GlobalScope.launch(Dispatchers.Main) {
            job?.cancel()
            setValue(dataState)
            finishJob()
        }
    }


    open fun finishJob() {}
    abstract suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract suspend fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

}