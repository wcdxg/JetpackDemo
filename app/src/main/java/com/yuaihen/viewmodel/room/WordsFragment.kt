package com.yuaihen.viewmodel.room

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuaihen.viewmodel.R
import kotlinx.android.synthetic.main.fragment_words.*

/**
 * Created by Yuaihen.
 * on 2020/12/7
 */
class WordsFragment : Fragment() {

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_words, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProvider(activity!!).get(WordViewModel::class.java)
        recyclerview.layoutManager = LinearLayoutManager(context)
        val adapter1 = RoomAdapter(true, viewModel)
        val adapter2 = RoomAdapter(false, viewModel)
        recyclerview.adapter = adapter1
        lifecycle.addObserver(viewModel.wordRepository)
        viewModel.wordRepository.allWordsLive.observe(activity!!, {
            val temp = adapter1.itemCount
            adapter1.setWordsData(it)
            adapter2.setWordsData(it)
            if (temp != it.size) {
                adapter1.notifyDataSetChanged()
                adapter2.notifyDataSetChanged()
            }
        })

        floatingActionButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_wordsFragment_to_addFragment))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_room, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}