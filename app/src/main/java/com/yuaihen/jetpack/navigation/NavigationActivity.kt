package com.yuaihen.jetpack.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.yuaihen.jetpack.R

/**
 * Navigation管理Fragment的切换
 * 多个Fragment持有同一ViewModel
 */
class NavigationActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

//        navController = Navigation.findNavController(this, R.id.fragment)
        navController = supportFragmentManager.findFragmentById(R.id.fragment)?.findNavController()!!
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
//        return super.onSupportNavigateUp()
    }
}