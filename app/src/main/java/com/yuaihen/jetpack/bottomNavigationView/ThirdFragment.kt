package com.yuaihen.jetpack.bottomNavigationView

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.yuaihen.jetpack.R
import kotlinx.android.synthetic.main.fragment_first.*
import kotlin.random.Random

class ThirdFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_third, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(ThirdViewModel::class.java)
        imageView.x = imageView.x + viewModel.dx
        val objectAnimator = ObjectAnimator.ofFloat(imageView, "x", 0f, 0f)
        objectAnimator.duration = 500
        imageView.setOnClickListener {
            if (!objectAnimator.isRunning) {
                val dx = if (Random.nextBoolean()) 100f else -100f
                objectAnimator.setFloatValues(
                    imageView.x,
                    imageView.x + dx
                )
                viewModel.dx += dx
                objectAnimator.start()
            }
        }
    }
}