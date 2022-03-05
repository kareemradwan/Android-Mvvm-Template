package com.kradwan.codegeneartormvvmsample.domain.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

//class GenericApiResponse<T> : GenericApiResponseOrg<T>()

/**
 * Copied from Architecture components google sample:
 * https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/api/ApiResponse.kt
 */
@Suppress("unused") // T is used in extending classes
sealed class GenericApiResponse<T> {

    companion object {
        private val TAG: String = "DDDD"

        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            Log.e(TAG, "GenericApiResponse: error: ${error.message}" , error)
            return ApiErrorResponse(error.message ?: "unknown error")
        }


        fun <T> create(response: Response<T>): GenericApiResponse<T> {

            val result = getResult(call = response)

            return when (result.status) {
                ResponseStatus.SUCCESS -> {
                    ApiSuccessResponse(body = result.data!!)
                }
                ResponseStatus.ERROR -> {
                    ApiErrorResponse(result.message!!)
                }
                ResponseStatus.EMPTY -> {
                    ApiEmptyResponse()
                }
            }
        }

        fun <T> getResult(call: Response<T>): ResponseResource<T> {
            try {
                Log.i(TAG, "getResult  ${call.code()}")
                if (call.isSuccessful) {
                    val body = call.body()
                    if (body != null) return ResponseResource.success(body)
                } else if (call.code() == NO_CONTENT) {
                    return ResponseResource.empty(null)
                } else if (call.code() == BAD_REQUEST) {
                    return ResponseResource.error(
                        message = call.errorBody()?.string() ?: "Bad Request"
                    )
                } else if (call.code() == UNAUTHORIZED) {
                    return ResponseResource.error(message = "401 Unauthorized. Token may be invalid.")
                } else if (call.code() == NOT_FOUND) {
                    return ResponseResource.error(message = "404 not found page")
                } else if (call.code() in BAD_REQUEST..BAD_REQUEST_LAST) {
                    return ResponseResource.error(message = call.errorBody()?.string())
                } else if (call.code() == SERVER_ERROR)
                    return ResponseResource.error(message = "500 INTERNAL SERVER ERROR")
                return ResponseResource.error(message = " ${call.code()} ${call.message()}")
            } catch (exception: UnknownHostException) {
                exception.printStackTrace()
                return ResponseResource.error(message = "no internet")
            } catch (exception: ConnectException) {
                exception.printStackTrace()
                return ResponseResource.error(message = "timeout")
            } catch (exception: SocketTimeoutException) {
                exception.printStackTrace()
                return ResponseResource.error(message = "timeout")
            } catch (exception: Exception) {
                exception.printStackTrace()
                return ResponseResource.error(message = exception.message ?: "Error occured")
            }
        }


    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : GenericApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : GenericApiResponse<T>() {}

data class ApiErrorResponse<T>(val errorMessage: String, val error: T? = null) : GenericApiResponse<T>()
