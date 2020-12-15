package com.yuaihen.jetpack.workManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

/**
 * Created by Yuaihen.
 * on 2020/12/14
 */
class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val name = inputData.getString("type")
        Thread.sleep(3000)
        val sp =
            applicationContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        var number = sp.getInt(name, 0)
        sp.edit().putInt(name, ++number).apply()
        return Result.success(workDataOf("name" to "$name output"))
    }
}