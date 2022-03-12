package com.kradwan.codegeneartormvvmsample.presentation.start_feature.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.kradwan.codegeneartormvvmsample.domain.usecase.account.LoginUseCase
import com.kradwan.codegeneartormvvmsample.presentation._base.BaseViewModel
import com.kradwan.codegeneartormvvmsample.presentation._base.DataState
import com.kradwan.codegeneartormvvmsample.presentation.start_feature.state.TestStateEvent
import com.kradwan.codegeneartormvvmsample.presentation.start_feature.state.TestViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartMainViewModel @Inject constructor(
    loginUseCase: LoginUseCase
) : BaseViewModel<TestStateEvent, TestViewState>() {


    override suspend fun handleStateEvent(stateEvent: TestStateEvent): LiveData<DataState<TestViewState>> {

        when (stateEvent) {
            is TestStateEvent.Log -> {

            }
            is TestStateEvent.GetList -> {
                return liveData {
                    emit(
                        DataState.data(
                            data = TestViewState(
                                list = arrayListOf<String>()
                            )
                        )
                    )
                }
            }


            is TestStateEvent.MakeToast -> {

                return liveData {
                    emit(
                        DataState.data(
                            data = TestViewState(
                                toast = stateEvent.msg
                            )
                        )
                    )
                }
            }

        }

        return object : LiveData<DataState<TestViewState>>() {
            override fun onActive() {
                super.onActive()
                value = DataState.data(null)
            }
        }

    }

}