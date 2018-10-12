package com.example.saidi.todokotlin

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.saidi.todokotlin.database.AppDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TaskAdapter.ItemClickListener {

    var mDB: AppDatabase? = null
    var taskAdapter: TaskAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(this)
        val decoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        recyclerViewTasks.adapter = taskAdapter
        recyclerViewTasks.addItemDecoration(decoration)
        mDB = AppDatabase.getInstance(applicationContext)
        val simpleItemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, p1: Int) {
                val taskEntry = taskAdapter?.mTaskEntries?.get(viewHolder.adapterPosition)
                AppExecutors.getInstance()?.diskIO?.execute {
                    taskEntry?.let { mDB?.taskDAO()?.deleteTask(taskEntry) }
                }
            }

        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchHelper)
        itemTouchHelper.attachToRecyclerView(recyclerViewTasks)

        fab.setOnClickListener {
            val addTaskIntent = Intent(this@MainActivity, AddTaskActivity::class.java)
            startActivity(addTaskIntent)
        }

        setupViewModel()

    }

    fun setupViewModel() {
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.tasks?.observe(this, Observer {
            taskAdapter?.setTasks(it)
        })
    }

    override fun onItemClickListener(itemId: Long?) {
        val intent = Intent(this, AddTaskActivity::class.java)
        intent.putExtra(EXTRA_TASK_ID, itemId)
        startActivity(intent)
    }
}
