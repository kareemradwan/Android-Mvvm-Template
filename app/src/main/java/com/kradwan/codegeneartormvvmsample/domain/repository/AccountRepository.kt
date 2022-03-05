package com.kradwan.codegeneartormvvmsample.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginRequest
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountViewState
import com.kradwan.codegeneartormvvmsample.presentation.state.DataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

abstract class AccountRepository(val scope: CoroutineScope) {


    private val jobs = HashMap<String, Job?>()

    abstract fun login(request: LoginRequest): LiveData<DataState<AccountViewState>>
    abstract fun getCountries(): LiveData<DataState<AccountViewState>>


    fun startJob(name: String, job: Job?) {
        if (jobs.contains(name)) {
            jobs[name]?.cancel()
        }
        jobs[name] = job
    }

    fun finishJob(name: String) {
        if (jobs.contains(name)) {
            jobs[name]?.cancel()
        }
        jobs.remove(name)
    }

    fun cancelJob(name: String) {
        if (jobs.contains(name)) {
            jobs[name]?.cancel()
        }
        jobs.remove(name)
    }

    fun cancelAllJobs() {
        jobs.keys.forEach { cancelJob(it) }
    }

}