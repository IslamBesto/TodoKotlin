package com.example.saidi.todokotlin

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saidi.todokotlin.database.TaskEntry
import kotlinx.android.synthetic.main.task_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(var mItemClickListener: ItemClickListener? = null, var mTaskEntries: List<TaskEntry>? = null, val mContext: Context? = null) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    companion object {
        const val DATE_FORMAT = "dd/MM/yyy"
    }

    private val dataFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    fun setTasks(taskEntries: List<TaskEntry>?) {
        mTaskEntries = taskEntries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TaskAdapter.TaskViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.task_layout, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mTaskEntries?.size ?: 0
    }

    override fun onBindViewHolder(holder: TaskAdapter.TaskViewHolder, position: Int) {
        val taskEntry = mTaskEntries?.get(position)
        holder.bind(taskEntry)
        val priorityCircle: GradientDrawable = holder.itemView.priorityTextView.background as GradientDrawable
        val priorityColor = getPriorityColor(taskEntry?.priority)
        priorityCircle.setColor(priorityColor)
    }

    private fun getPriorityColor(priority: Int?): Int {
        val priorityColor = 0
        mContext?.let {
            return when (priority) {
                1 -> ContextCompat.getColor(mContext, R.color.materialRed)
                2 -> ContextCompat.getColor(mContext, R.color.materialOrange)
                3 -> ContextCompat.getColor(mContext, R.color.materialYellow)
                else -> priorityColor

            }
        } ?: run {
            return priorityColor
        }
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(p0: View?) {
            val elementId = mTaskEntries?.get(adapterPosition)?.id
            mItemClickListener?.onItemClickListener(elementId)
        }

        fun bind(taskEntry: TaskEntry?) {
            itemView.taskDescription.text = taskEntry?.description
            itemView.taskUpdatedAt.text = dataFormat.format(taskEntry?.updatedAt)
            itemView.priorityTextView.text = taskEntry?.priority.toString()
        }
    }

    interface ItemClickListener {
        fun onItemClickListener(itemId: Long?)
    }
}