package com.yuaihen.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.yuaihen.viewmodel.databinding.ActivityScoreBinding

/**
 * Created by Yuaihen.
 * on 2020/11/24
 */
class ScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_score)
        binding.data = ViewModelProvider(this).get(ScoreViewModel::class.java)
        binding.lifecycleOwner = this


    }
}