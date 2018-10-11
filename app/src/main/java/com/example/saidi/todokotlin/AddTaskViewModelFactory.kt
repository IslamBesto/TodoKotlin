package com.example.saidi.todokotlin

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.saidi.todokotlin.database.AppDatabase


class AddTaskViewModelFactory(database: AppDatabase, taskId: Int) : ViewModelProvider.Factory {

    private val mAppDatabase: AppDatabase? = database
    private val mTaskId: Int? = taskId
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddTaskViewModel::class.java)) {
            return AddTaskViewModel(mAppDatabase, mTaskId) as T
        }
        throw IllegalArgumentException("unexpected model class $modelClass")

    }
}