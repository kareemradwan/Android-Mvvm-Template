package com.kradwan.codegeneartormvvmsample.domain

import android.util.Log
import android.util.LruCache
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kradwan.codegeneartormvvmsample.domain.util.*
import com.kradwan.codegeneartormvvmsample.presentation.state.DataState
import kotlinx.coroutines.*
import javax.inject.Inject


private val CACHE: LruCache<String, Any> = LruCache(10)

abstract class NetworkBoundResource<ResponseObject, CacheObject, ViewStateType>(
    val name: String,
    var fromCache: Boolean = false,
    val fromDb: Boolean = false,
    shouldDisplayLoading: Boolean = true,
) {


    private var job: Job? = null

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main + Job())

    private val result = MediatorLiveData<DataState<ViewStateType>>()

    fun setValue(dataState: DataState<ViewStateType>) {
        result.value = dataState
    }

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>

    init {
        setValue(DataState.loading(isLoading = shouldDisplayLoading, cachedData = null))

        doNetworkRequest()
    }

    open fun validate(): Boolean {
        return true
    }

    private fun doNetworkRequest() {
        if (!validate()) {
            return
        }

        job = coroutineScope.launch {


            if (fromCache) {
                (CACHE.get(name) as? ResponseObject)?.let { handleApiSuccessResponse(it) }
//
////                fromCache()
            }
//            if (fromDb) {
//                fromDB()
//            }

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
//                saveOnCache(body)
                CACHE.put(name, body)
//                cache.save(name, body as Any)
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

    public fun onErrorReturn(
        errorMessage: String?,
        shouldUseDialog: Boolean,
        shouldUseToast: Boolean
    ) {
        onCompleteJob(DataState.error(errorMessage ?: "error msg"))
    }

//    open fun saveOnCache(body: ResponseObject) {}

    fun onCompleteJob(dataState: DataState<ViewStateType>) {
        coroutineScope.launch {
            withContext(Dispatchers.Main) {
                job?.cancel()
                setValue(dataState)
                endJob()
            }
        }
    }


    //    abstract suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)
    abstract suspend fun handleApiSuccessResponse(data: ResponseObject)

    open fun endJob() {
        Log.d("DDDDD", "End Job: ${name}")
    }

    open fun pushJob(job: Job) {
        Log.d("DDDDD", "Push Job: ${name}")
    }

    abstract suspend fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    //    abstract suspend fun fromDB(): LiveData<GenericApiResponse<ResponseObject>>?
    open suspend fun fromDB() {}


//    open suspend fun fromCache() {}


}