package com.kradwan.codegeneartormvvmsample.domain.usecase


@DslMarker
annotation class HtmlTagMarker

@HtmlTagMarker
class RequestMeta {
    var metaShowLoading: Boolean = true
    var metaFromNetwork: Boolean = true
    var metaFromCache: Boolean = false
    var metaFromDB: Boolean = false
    var metaSaveResponse: Boolean = false
    var metaStopIfFoundResult = false

}


@HtmlTagMarker
class RequestSetting<T> {
    var settingName: String = "default"
    private var settingMeta: RequestMeta? = null
    var settingData: T? = null


    @HtmlTagMarker
    fun meta(init: RequestMeta.() -> Unit): RequestMeta {
        val meta = RequestMeta()
        meta.init()
        this.settingMeta = meta
        return meta
    }

    fun meta(): RequestMeta = settingMeta ?: RequestMeta()
}


@HtmlTagMarker
fun <T> requestSetting(init: RequestSetting<T>.() -> Unit): RequestSetting<T> {
    val setting = RequestSetting<T>()
    setting.init()

    return setting
}


@HtmlTagMarker
fun <T> defaultRequestSetting(action: T? = null): RequestSetting<T> {
    return requestSetting {
        meta {
            metaFromNetwork = true
            metaStopIfFoundResult = true
        }
        settingData = action
    }
}

