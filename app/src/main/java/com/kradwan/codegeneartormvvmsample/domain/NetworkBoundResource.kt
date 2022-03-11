package com.kradwan.codegeneartormvvmsample.domain

import android.util.Log
import android.util.LruCache
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kradwan.codegeneartormvvmsample.domain.usecase.RequestMeta
import com.kradwan.codegeneartormvvmsample.domain.util.*
import com.kradwan.codegeneartormvvmsample.presentation._di.account.AccountScope
import com.kradwan.codegeneartormvvmsample.presentation.state.DataState
import kotlinx.coroutines.*
import javax.inject.Inject


private val CACHE: LruCache<String, Any> = LruCache(10)

open class NetworkBoundResource<ResponseObject, ViewStateType>(
    val name: String,
    val meta: RequestMeta? = RequestMeta(),
) {




    private var job: Job? = null

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main + Job())

    private val result = MediatorLiveData<DataState<ViewStateType>>()

    fun setValue(dataState: DataState<ViewStateType>) {
        result.value = dataState
    }

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>

    init {

        doNetworkRequest()
    }

    open fun validate(): Boolean {
        return true
    }

    private fun doNetworkRequest() {
        if (!validate()) {
            return
        }

        setValue(DataState.loading(isLoading = meta?.metaShowLoading ?: false, cachedData = null))

        job = coroutineScope.launch {


            if (meta?.metaFromCache == true) {
                val cachedData = (CACHE.get(name) as? ResponseObject)
                cachedData?.let {
                    handleApiSuccessResponse(it)
                    if (meta.metaStopIfFoundResult) {
                        Log.d("DDDD", "Found Data on Cache")
                        return@launch
                    }
                }
            }

            if (meta?.metaFromDB == true) {
                fromDB()
            }

            withContext(Dispatchers.Main) {
                // make network call
                val apiResponse = createCall()
                result.addSource(apiResponse) { response ->

                    result.removeSource(apiResponse)
                    coroutineScope.launch {
                        handleNetworkCall(response)
                    }
                }
            }
        }
        job?.let {
            pushJob(it)
        }


    }

    private suspend fun handleNetworkCall(response: GenericApiResponse<ResponseObject>) {
        when (response) {
            is ApiSuccessResponse -> {
                val body = response.body
                if (meta?.metaSaveResponse == true) {
                    Log.d("DDDD", "Save Data on Cache")
                    CACHE.put(name, body)
                }
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
        coroutineScope.launch {
            withContext(Dispatchers.Main) {
                setValue(dataState)
                if (cancel) {
                    job?.cancel()
                    endJob()
                }
            }
        }
    }


    //    abstract suspend fun handleApiSuccessResponse(data: ResponseObject)
    open suspend fun handleApiSuccessResponse(data: ResponseObject) {}

    open fun endJob() {}

    open fun pushJob(job: Job) {}

    //    abstract suspend fun createCall(): LiveData<GenericApiResponse<ResponseObject>>
    open suspend fun createCall(): LiveData<GenericApiResponse<ResponseObject>> {
        TODO()
    }

    open suspend fun fromDB() {}


}