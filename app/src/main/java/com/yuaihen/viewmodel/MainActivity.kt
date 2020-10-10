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
        myViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
                .get(MyViewModel::class.java)


        textView.text = myViewModel.number.toString()

        button.setOnClickListener {
            myViewModel.number++
            textView.text = myViewModel.number.toString()
        }

        button2.setOnClickListener {
            myViewModel.number += 2
            textView.text = myViewModel.number.toString()
        }

    }
}