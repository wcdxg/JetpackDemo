package com.yuaihen.jetpack.workManager

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.yuaihen.jetpack.databinding.ActivityWorkManagerBinding

/**
 * Created by Yuaihen.
 * on 2020/12/14
 */
const val SHARED_PREFERENCES_NAME = "shp_name"
const val WORK_A_NAME = "work_a_name"
const val WORK_B_NAME = "work_b_name"

class WorkActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var binding: ActivityWorkManagerBinding
    private val workManager = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sp = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
        sp.registerOnSharedPreferenceChangeListener(this)
        updateView()
        binding.button.setOnClickListener {
            val workRequestA = createWork(WORK_A_NAME)
            val workRequestB = createWork(WORK_B_NAME)
            workManager.beginWith(workRequestA)
                .then(workRequestB)
                .enqueue()
        }
    }

    private fun createWork(name: String): OneTimeWorkRequest {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        return OneTimeWorkRequestBuilder<MyWorker>()
            .setConstraints(constraints)
            .setInputData(workDataOf("type" to name))
            .build()
    }

    private fun updateView() {
        val sp = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
        binding.textViewA.text = sp.getInt(WORK_A_NAME, 0).toString()
        binding.textViewB.text = sp.getInt(WORK_B_NAME, 0).toString()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        updateView()
    }
}