package com.kradwan.codegeneartormvvmsample.presentation.start_feature.state

sealed class TestStateEvent {

    class Log(val msg: String) : TestStateEvent()

    class GetList(val size: Int) : TestStateEvent()

    class MakeToast(val msg: String) : TestStateEvent()

}