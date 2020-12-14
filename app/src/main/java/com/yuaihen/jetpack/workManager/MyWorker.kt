package com.yuaihen.jetpack.workManager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Created by Yuaihen.
 * on 2020/12/14
 */
class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val data = inputData.getString("hello")
        Log.d("TAG", "doWork: start $data")
        Thread.sleep(3000)
        Log.d("TAG", "doWork: finish")
        return Result.success()
    }
}