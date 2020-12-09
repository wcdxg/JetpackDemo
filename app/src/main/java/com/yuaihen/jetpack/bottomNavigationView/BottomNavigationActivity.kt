package com.yuaihen.jetpack.bottomNavigationView

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.yuaihen.jetpack.R
import kotlinx.android.synthetic.main.activity_bottom_navigation.*

/**
 * Created by Yuaihen.
 * on 2020/12/9
 */
class BottomNavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        val navController = supportFragmentManager.findFragmentById(R.id.bottomFragmentContainer)
            ?.findNavController()
        val configuration = AppBarConfiguration.Builder(bottomNavigationView.menu).build()
        NavigationUI.setupActionBarWithNavController(this, navController!!, configuration)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }
}