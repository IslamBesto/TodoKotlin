package com.example.saidi.todokotlin

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors(val diskIO: Executor?, val valnetworkIO: Executor?, val mainThread: Executor?) {

    companion object {
        private val LOCK = Any()
        private var sInstance: AppExecutors? = null
        fun getInstance(): AppExecutors? {
            sInstance ?: kotlin.run {
                synchronized(LOCK) {
                    sInstance = AppExecutors(Executors.newSingleThreadExecutor(),
                            Executors.newFixedThreadPool(3),
                            MainThreadExecutor())
                }
            }
            return sInstance
        }

    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}