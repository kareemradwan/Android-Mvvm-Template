package com.kradwan.codegeneartormvvmsample.domain.util

data class ResponseResource<out T>(val status: ResponseStatus, val data: T?, val message: String?) {

    companion object{
        fun<T> success(data:T,tag: String = ""): ResponseResource<T> =
            ResponseResource(
                status = ResponseStatus.SUCCESS,
                data = data,
                message = tag
            )
        fun<T> error(message: String?): ResponseResource<T> =
            ResponseResource(
                status = ResponseStatus.ERROR,
                data = null,
                message = message
            )
        fun<T> empty(data:T?): ResponseResource<T> =
            ResponseResource(
                status = ResponseStatus.EMPTY,
                data = data,
                message = null
            )
    }

}


sealed class ResponseType{

    class Toast: ResponseType()

    class Dialog: ResponseType()

    class None: ResponseType()
}

