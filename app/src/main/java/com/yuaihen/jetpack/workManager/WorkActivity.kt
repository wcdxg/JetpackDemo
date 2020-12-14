package com.yuaihen.jetpack.workManager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.yuaihen.jetpack.databinding.ActivityWorkManagerBinding

/**
 * Created by Yuaihen.
 * on 2020/12/14
 */
class WorkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkManagerBinding
    private val workManager = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button.setOnClickListener {
            //约束条件
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val worker = OneTimeWorkRequestBuilder<MyWorker>()
//                .setConstraints(constraints)
                .setInputData(workDataOf("hello" to "this is worker"))
                .build()
            workManager.enqueue(worker)
        }
    }
}