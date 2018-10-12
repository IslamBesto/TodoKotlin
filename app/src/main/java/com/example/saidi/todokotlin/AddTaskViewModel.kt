package com.example.saidi.todokotlin

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.saidi.todokotlin.database.AppDatabase
import com.example.saidi.todokotlin.database.TaskEntry

class AddTaskViewModel(appDatabase: AppDatabase?, taskId: Int?) : ViewModel() {

    private val TAG = MainViewModel::class.java.simpleName
    val task: LiveData<TaskEntry>? = appDatabase?.taskDAO()?.loadTaskById(taskId)

}