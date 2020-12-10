package com.yuaihen.jetpack.score

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.yuaihen.jetpack.R
import com.yuaihen.jetpack.databinding.ActivityScoreBinding

/**
 * Created by Yuaihen.
 * on 2020/11/24
 */
class ScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_score)

        val viewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)
        binding.data = viewModel
        binding.lifecycleOwner = this

    }


}