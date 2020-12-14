package com.yuaihen.jetpack

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yuaihen.jetpack.bottomNavigationView.BottomNavigationActivity
import com.yuaihen.jetpack.lifecycle.LifeCycleActivity
import com.yuaihen.jetpack.navigation.NavigationActivity
import com.yuaihen.jetpack.room.RoomActivity
import com.yuaihen.jetpack.savestate.SaveStateActivity
import com.yuaihen.jetpack.score.ScoreActivity
import com.yuaihen.jetpack.workManager.WorkActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val VIEW_MODEL = 1
        const val SAVE_STATE_VIEW_MODEL = 2
        const val NAVIGATION = 3
        const val LIFECYCLE = 4
        const val ROOM = 5
        const val BOTTOM_NAVAGITION_VIEW = 6
        const val WOEKER = 7
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelBtn.setOnClickListener(MyOnClickListener(VIEW_MODEL))
        saveStateViewModelBtn.setOnClickListener(MyOnClickListener(SAVE_STATE_VIEW_MODEL))
        navigationBtn.setOnClickListener(MyOnClickListener(NAVIGATION))
        lifeCycleBtn.setOnClickListener(MyOnClickListener(LIFECYCLE))
        roomBtn.setOnClickListener(MyOnClickListener(ROOM))
        bottomNavigationBtn.setOnClickListener(MyOnClickListener(BOTTOM_NAVAGITION_VIEW))
        workerBtn.setOnClickListener(MyOnClickListener(WOEKER))
    }


    inner class MyOnClickListener(private val index: Int) : View.OnClickListener {
        private val intent = Intent()
        override fun onClick(v: View?) {
            when (index) {
                VIEW_MODEL -> intent.setClass(applicationContext, ScoreActivity::class.java)
                SAVE_STATE_VIEW_MODEL -> intent.setClass(
                    applicationContext,
                    SaveStateActivity::class.java
                )
                NAVIGATION -> intent.setClass(applicationContext, NavigationActivity::class.java)
                LIFECYCLE -> intent.setClass(applicationContext, LifeCycleActivity::class.java)
                ROOM -> intent.setClass(applicationContext, RoomActivity::class.java)
                BOTTOM_NAVAGITION_VIEW -> intent.setClass(
                    applicationContext,
                    BottomNavigationActivity::class.java
                )
                WOEKER -> intent.setClass(
                    applicationContext,
                    WorkActivity::class.java
                )
            }

            startActivity(intent)
        }

    }
}