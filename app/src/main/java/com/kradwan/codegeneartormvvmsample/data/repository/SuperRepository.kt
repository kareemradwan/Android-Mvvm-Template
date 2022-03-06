package com.kradwan.codegeneartormvvmsample.data.repository

import android.util.Log
import kotlinx.coroutines.Job

abstract class SuperRepository {

    private val jobs = HashMap<String, Job?>()
    private val groupJobs = HashMap<String, HashSet<String>>()
    private var currentGroup = "default"

    fun setCurrent(viewModel: Any) {
        currentGroup = viewModel.javaClass.simpleName ?: "default"
    }

    fun removeCurrent() {
        clearJobGroup()
    }


    private fun addToGroup(jobName: String) {
        val hasGroup = groupJobs.contains(currentGroup)
        if (!hasGroup) {
            groupJobs[currentGroup] = HashSet()
        }
        groupJobs[currentGroup]?.add(jobName)
        Log.d("DDDD", "Add new Job to $currentGroup  => $jobName")
    }

    private fun clearJobGroup() {
        val hasGroup = groupJobs.contains(currentGroup)
        if (hasGroup) {
            groupJobs[currentGroup]?.forEach { jobId ->
                cancelJob(jobId)
            }
            groupJobs[currentGroup]?.clear()
            currentGroup = "default"
        }
    }

    fun startJob(name: String, job: Job?) {
        addToGroup(name)
        if (jobs.contains(name)) {
            // TODO :
            Log.d("DDDD", "Cancel Current Job: $name")
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