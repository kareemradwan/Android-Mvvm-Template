package com.kradwan.codegeneartormvvmsample.presentation._base



data class DataState<T>(
    var error: String? = null,
    var loading: Boolean? = false,
    var data: T? = null
) {

    companion object {

        fun <T> error(
            response: String
        ): DataState<T> {
            return DataState(
                error = response,
                loading = false,
                data = null
            )
        }

        fun <T> loading(
            isLoading: Boolean,
            cachedData: T? = null
        ): DataState<T> {
            return DataState(
                error = null,
                loading = isLoading,
                data = cachedData
            )
        }

        fun <T> data(
            data: T? = null,
        ): DataState<T> {
            return DataState(
                error = null,
                loading = false,
                data = data
            )
        }
    }
}