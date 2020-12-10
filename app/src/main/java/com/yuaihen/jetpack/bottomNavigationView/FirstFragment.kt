package com.yuaihen.jetpack.bottomNavigationView

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.yuaihen.jetpack.R
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //生命周期与
        val viewModel: FirstViewModel by activityViewModels()
//        val viewModel: FirstViewModel by viewModels()
//        val viewModel = ViewModelProvider(this).get(FirstViewModel::class.java)
        imageView.rotation = viewModel.rotate
        val objectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 0f)
        objectAnimator.duration = 500
        imageView.setOnClickListener {
            if (!objectAnimator.isRunning) {
                objectAnimator.setFloatValues(imageView.rotation, imageView.rotation + 100)
                viewModel.rotate += 100
                objectAnimator.start()
            }
        }
    }

}