package com.example.saidi.todokotlin

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.saidi.todokotlin.database.AppDatabase
import com.example.saidi.todokotlin.database.TaskEntry

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = MainViewModel::class.java.simpleName
    val tasks: LiveData<List<TaskEntry>>?

    init {
        val appDatabase = AppDatabase.getInstance(this.getApplication())
        tasks = appDatabase?.taskDAO()?.loadAllTasks()
    }

}
