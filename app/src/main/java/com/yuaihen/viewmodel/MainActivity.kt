package com.yuaihen.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myViewModel = ViewModelProvider(this)
            .get(MyViewModel::class.java)


        myViewModel.likeNum.observe(this, {
            textView.text = it.toString()
        })

        imageButtonLike.setOnClickListener {
            myViewModel.likeNum.value = myViewModel.likeNum.value?.plus(1)
        }

        imageButtonDisLike.setOnClickListener {
            myViewModel.likeNum.value = myViewModel.likeNum.value?.minus(1)
        }

    }
}