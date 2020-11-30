package com.yuaihen.viewmodel.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.yuaihen.viewmodel.R
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button3.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_detailFragment_to_homeFragment))
    }

}