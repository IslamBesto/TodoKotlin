package com.example.saidi.todokotlin.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "task")
data class TaskEntry(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                     var description: String? = "",
                     var priority: Int? = 0,
                     @ColumnInfo(name = "updated_at") var updatedAt: Date
)
