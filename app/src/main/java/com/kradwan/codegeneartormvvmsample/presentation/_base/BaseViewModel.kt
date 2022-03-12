package com.kradwan.codegeneartormvvmsample.presentation._base

import androidx.lifecycle.*
import androidx.navigation.NavDirections
import kotlinx.coroutines.*

abstract class BaseViewModel<StateEvent, ViewState> : ViewModel() {


    private var _navigateScreen = MutableLiveData<NavDirections>()
    val navigateScreen: LiveData<NavDirections> = _navigateScreen

    fun navigate(action: NavDirections) {
        _navigateScreen.value = action
    }



    private val jobs = ArrayList<Job>()
    private val result = MediatorLiveData<DataState<ViewState>>()
    val dataState = result as LiveData<DataState<ViewState>>


    private val sources: ArrayList<LiveData<DataState<ViewState>>> = ArrayList()

    fun setStateEvent(event: StateEvent) {

        val job = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val source = handleStateEvent(event)
                withContext(Dispatchers.Main) {
                    sources.add(source)
                    result.addSource(source) {
                        result.value = it
                    }
                }
            }
        }

        this.jobs.add(job)
    }

    private fun clear() {
        jobs.forEach {
            it.cancel()
        }
        jobs.clear()
    }


    override fun onCleared() {
        sources.forEach {
            result.removeSource(it)
        }
        sources.clear()
        clear()
        super.onCleared()
    }


    protected abstract suspend fun handleStateEvent(stateEvent: StateEvent): LiveData<DataState<ViewState>>

}