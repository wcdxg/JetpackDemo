package com.yuaihen.jetpack.score

import android.os.Bundle
import android.util.Log
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
    val TAG = "ScoreActivity"

    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_score)
        //使用下面两种方式创建的ViewModel在屏幕旋转时数据会丢失 不会重新设置
        //ViewModelProvider.NewInstanceFactory().create(ScoreViewModel::class.java)
        //ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(ScoreViewModel::class.java)
        //原因在于上面两种方式创建的ViewModel在屏幕发生旋转后会重新创建,重新创建的ViewModel并不是之前的那一个了,通过打印内存地址可以看到差别,因此数据没有保留
        //需要使用下面方式创建
        //ViewModelProvider(this).get(ScoreViewModel::class.java)
        //这种方式创建的ViewModel在屏幕发生旋转后重新创建的ViewModel仍然是之前的ViewModel,内存地址也是一样的.

        val viewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)
        binding.data = viewModel
        binding.lifecycleOwner = this

        Log.d(TAG, "onCreate: 页面创建")
        Log.d(TAG, "onCreate: ${viewModel}")

    }


}