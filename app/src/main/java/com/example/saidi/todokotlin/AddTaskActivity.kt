package com.example.saidi.todokotlin

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.saidi.todokotlin.database.AppDatabase
import com.example.saidi.todokotlin.database.TaskEntry
import kotlinx.android.synthetic.main.activity_add_task.*
import kotlinx.android.synthetic.main.task_layout.*
import java.util.*

const val EXTRA_TASK_ID = "extraTaskId"
const val INSTANCE_TASK_ID = "instanceTaskId"
const val PRIORITY_HIGH = 1
const val PRIORITY_MEDIUM = 2
const val PRIORITY_LOW = 3
private const val DEFAULT_TASK_ID = -1
private var mTaskId = DEFAULT_TASK_ID

class AddTaskActivity : AppCompatActivity(), LifecycleObserver {

    private val TAG = AddTaskActivity::class.java.simpleName
    private var mDb: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        mDb = AppDatabase.getInstance(applicationContext)
        saveButton.setOnClickListener {
            onSaveButtonClicked()
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID)
        }
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            saveButton.text = getString(R.string.update_button)
            if (mTaskId == DEFAULT_TASK_ID) {
                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID)
                val factory = mDb?.let { AddTaskViewModelFactory(it, mTaskId) }
                val viewModel = ViewModelProviders.of(this, factory).get(AddTaskViewModel::class.java)
                viewModel.task?.observe(this, Observer {
                    populateUI(it)
                })
            }

        }
    }

    fun getPriorityFromViews(): Int {
        var priority = 1
        val checkedId = radioGroup.checkedRadioButtonId
        when (checkedId) {
            R.id.radButton1 -> priority = PRIORITY_HIGH
            R.id.radButton2 -> priority = PRIORITY_MEDIUM
            R.id.radButton3 -> priority = PRIORITY_LOW
        }
        return priority
    }

    private fun onSaveButtonClicked() {
        val description = editTextTaskDescription.text
        val priority = getPriorityFromViews()
        val data = Date()

        val task = TaskEntry(description = description.toString(), priority = priority, updatedAt = data)
        AppExecutors.getInstance()?.diskIO?.execute {
            if (mTaskId == DEFAULT_TASK_ID) {
                mDb?.taskDAO()?.insertTask(task)
            } else {
                task.id = mTaskId.toLong()
                mDb?.taskDAO()?.updateTask(task)
            }
            finish()
        }
    }

    fun setPriorityInViews(priority: Int?) {
        when (priority) {
            PRIORITY_HIGH -> radioGroup.check(R.id.radButton1)
            PRIORITY_MEDIUM -> radioGroup.check(R.id.radButton2)
            PRIORITY_LOW -> radioGroup.check(R.id.radButton3)
        }
    }

    private fun populateUI(task: TaskEntry?) {
        task ?: return
        editTextTaskDescription.setText(task.description)
        priorityTextView
        setPriorityInViews(task.priority)
    }
}