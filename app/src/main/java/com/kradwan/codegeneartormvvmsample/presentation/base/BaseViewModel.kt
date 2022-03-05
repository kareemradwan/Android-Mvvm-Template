package com.kradwan.codegeneartormvvmsample.presentation.base

import android.util.Log
import androidx.lifecycle.*
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountViewState
import com.kradwan.codegeneartormvvmsample.presentation.state.DataState

abstract class BaseViewModel<StateEvent, ViewState> : ViewModel() {

    var error = MutableLiveData<String>()
    var showLoading: MutableLiveData<Boolean> = MutableLiveData()

    val TAG: String = "AppDebug"

    protected val _stateEvent: MutableLiveData<StateEvent> = MutableLiveData()
    protected val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    //to help navigate to screens from view model
//    private var _navigateScreen = MutableLiveData<Event<Any>>()
//    val navigateScreen: LiveData<Event<Any>> = _navigateScreen
//
//    fun navigate(action: NavDirections){
//        _navigateScreen!!.value = Event(action)
//    }

    val viewState: LiveData<ViewState>
        get() = _viewState


    val result = MediatorLiveData<DataState<ViewState>>()

    //    val dataState  : MutableLiveData<DataState<ViewState>> = MutableLiveData()
    val dataState = result as LiveData<DataState<ViewState>>
//    val dataState: LiveData<DataState<ViewState>> = Transformations
//        .switchMap(_stateEvent) { stateEvent ->
//            stateEvent?.let {
//                Log.d("DDDD", "DDDD Response: handleStateEvent ${stateEvent}")
//                handleStateEvent(stateEvent)
//            }
//        }


    private val sources: ArrayList<LiveData<DataState<ViewState>>> = ArrayList()

    fun setStateEvent(event: StateEvent) {

        val source = handleStateEvent(event)
        sources.add(source)
        result.addSource(source) {
            Log.d("DDDD", "addSource: ${it}")
//            dsa.value = null
//            dsa.value = it
            result.value = it
//            result.removeSource(source)
//            dataState.value = it
        }
//        Log.d("DDDD" , "addSource: size : ${result.hasObservers()}")
//        _stateEvent.postValue(event)
//        _stateEvent.postValue(null)
    }

    /*






     */

    override fun onCleared() {
        sources.forEach {
            result.removeSource(it)
        }
        sources.clear()
        super.onCleared()
    }


    fun getCurrentViewStateOrNew(): ViewState {
        val value = viewState.value?.let {
            it
        } ?: initNewViewState()
        return value
    }

    abstract fun handleStateEvent(stateEvent: StateEvent): LiveData<DataState<ViewState>>

    abstract fun initNewViewState(): ViewState


    private fun doSomeThingWhenIdChange(id: Int): LiveData<String> {
        TODO()
    }

//    val data = MediatorLiveData<String>()
//    val userId: MutableLiveData<Int> = MutableLiveData()
//
//    fun updateId(id: Int) {
//        val source = doSomeThingWhenIdChange(id)
//
//        data.addSource(userId) {
//            data.addSource(doSomeThingWhenIdChange(it)) {
//                data.value = it
//            }
//        }
//
//    }
/*

    private fun doSomeThingWhenIdChange(id: Int) : LiveData<String>  {
        TODO()
    }

    val data = MediatorLiveData<String>()
   val userId: MutableLiveData<Int> = MutableLiveData()

    fun updateId( id: Int) {
        val source = doSomeThingWhenIdChange(id)
        data.addSource(source) {
            data.value  = it
        }
    }

    override fun onViewCreated() {
        data.observe(this) {  result ->

        }
    }

    */


    private val userId: MutableLiveData<Int> = MutableLiveData()
    val data: LiveData<String> = Transformations
        .switchMap(userId) { stateEvent ->
            stateEvent?.let {
                getProfile(it)
            }
        }
    private fun getProfile(id: Int): LiveData<String> {
        TODO()
    }

}