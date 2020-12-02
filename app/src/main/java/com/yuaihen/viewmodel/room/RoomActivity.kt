package com.yuaihen.viewmodel.room

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.yuaihen.viewmodel.R

/**
 * Created by Yuaihen.
 * on 2020/12/2
 */
class RoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_room)
    }
}