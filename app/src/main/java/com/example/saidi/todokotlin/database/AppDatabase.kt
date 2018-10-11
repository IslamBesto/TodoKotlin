package com.example.saidi.todokotlin.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log

@Database(entities = [AppDatabase::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private val LOCK = Any()
        const val DATABASE_NAME = "todolist"
        val LOG_TAG = AppDatabase::class.java.simpleName
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            INSTANCE?.let {
                synchronized(LOCK) {
                    Log.d(LOG_TAG, "Creating new database instance")
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, AppDatabase.DATABASE_NAME).build()
                }
            }
            Log.d(LOG_TAG, "Getting the database instance")
            return INSTANCE
        }
    }

    abstract fun taskDAO(): TaskDao
}