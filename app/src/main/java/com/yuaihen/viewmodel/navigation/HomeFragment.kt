package com.yuaihen.viewmodel.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.yuaihen.viewmodel.R
import com.yuaihen.viewmodel.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel = ViewModelProvider(activity!!).get(MyViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.data = viewModel
        binding.lifecycleOwner = activity

        binding.seekBar.progress = viewModel.getNumber().value!!
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.getNumber().value = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        return binding.root
//        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //两种方式跳转
        button3.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_detailFragment)
        

        )
//        button3.setOnClickListener {
//            val bundle = Bundle().apply {
//                putString("name", "Rose")
//            }
//            Navigation.findNavController(it)
//                .navigate(R.id.action_homeFragment_to_detailFragment, bundle)
//        }

    }

}