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

class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)
        imageView.scaleX = viewModel.scaleValue
        imageView.scaleY = viewModel.scaleValue
        val scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 0f, 0f)
        val scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 0f, 0f)
        scaleX.duration = 500
        scaleY.duration = 500
        imageView.setOnClickListener {
            if (!scaleX.isRunning) {
                scaleX.setFloatValues(imageView.scaleX, imageView.scaleX + 0.1f)
                scaleY.setFloatValues(imageView.scaleY, imageView.scaleY + 0.1f)
                viewModel.scaleValue += 0.1f
                scaleX.start()
                scaleY.start()
            }
        }
    }
}