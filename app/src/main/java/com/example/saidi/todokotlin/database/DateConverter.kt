package com.example.saidi.todokotlin.database

import android.arch.persistence.room.TypeConverter
import java.util.*

object DateConverter {

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        timestamp?.let { return Date(timestamp) } ?: kotlin.run { return null }
    }

    fun toTimestamp(date: Date?): Long? {
        date?.let { return date.time } ?: kotlin.run { return null }
    }
}