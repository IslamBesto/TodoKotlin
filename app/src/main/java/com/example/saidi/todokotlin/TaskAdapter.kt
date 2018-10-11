package com.example.saidi.todokotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.saidi.todokotlin.database.TaskEntry

class TaskAdapter(var mItemClickListener: ItemClickListener? = null, var mTaskEntries: List<TaskEntry>? = null, val mContext: Context? = null) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    companion object {
        const val DATE_FORMAT = "dd/MM/yyy"
    }

    fun getTasks(): List<TaskEntry>? {
        return mTaskEntries
    }

    fun setTasks(taskEntries: List<TaskEntry>) {
        mTaskEntries = taskEntries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TaskAdapter.TaskViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: TaskAdapter.TaskViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class TaskViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(p0: View?) {
            val elementId = TaskAdapter().mTaskEntries?.get(adapterPosition)?.id
            TaskAdapter().mItemClickListener?.onItemClickListener(elementId)
        }

    }

    interface ItemClickListener {
        fun onItemClickListener(itemId: Long?)
    }

}