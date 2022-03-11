package com.kradwan.codegeneartormvvmsample.domain

import android.util.Log
import android.util.LruCache
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kradwan.codegeneartormvvmsample.domain.usecase.RequestMeta
import com.kradwan.codegeneartormvvmsample.domain.usecase.RequestSetting
import com.kradwan.codegeneartormvvmsample.domain.util.*
import com.kradwan.codegeneartormvvmsample.presentation._di.account.AccountScope
import com.kradwan.codegeneartormvvmsample.presentation.state.DataState
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.*


private val CACHE: LruCache<String, Any> = LruCache(10)


open class NetworkBoundResource<ResponseObject, ViewStateType> constructor(
    val name: String,
    val meta: RequestMeta? = RequestMeta(),
) {

    companion object {
        suspend fun <T, B> createRequest(init: suspend (NetworkBoundResource<T, B>.() -> Unit)): NetworkBoundResource<T, B> {
            val setting = NetworkBoundResource<T, B>("", RequestMeta())
            setting.init()
            setting.startWork()
            return setting

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

        Log.d("DDDD", "startWork: ")

        setValue(DataState.loading(isLoading = meta?.metaShowLoading ?: false, cachedData = null))

//        if (meta?.metaFromCache == true) {
//            val cachedData = (CACHE.get(name) as? ResponseObject)
//            cachedData?.let {
//                handleApiSuccessResponse(it)
//                if (meta.metaStopIfFoundResult) {
//                    Log.d("DDDD", "Found Data on Cache")
//                    return
////                        return@launch
//                }
//            }
//        }
//
//        if (meta?.metaFromDB == true) {
//            fromDB()
//        }

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

    private  fun handleNetworkCall(response: GenericApiResponse<ResponseObject>) {
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


    open  fun onErrorReturn(
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