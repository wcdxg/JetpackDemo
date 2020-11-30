package com.yuaihen.viewmodel.savestate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.yuaihen.viewmodel.R
import com.yuaihen.viewmodel.databinding.ActivitySaveStateBinding

/**
 * Created by Yuaihen.
 * on 2020/11/25
 * 使用SavedStateHandle保存状态  在Activity被杀死之后恢复状态 替代onSaveInstanceState
 */
class SaveStateActivity : AppCompatActivity() {
    companion object {
        const val KEY_NUMBER = "key_number"
    }

    private lateinit var binding: ActivitySaveStateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_save_state)
        binding.data = ViewModelProvider(this, SavedStateViewModelFactory(application, this)).get(
            SaveStateViewModel::class.java
        )
        binding.lifecycleOwner = this

    }

}