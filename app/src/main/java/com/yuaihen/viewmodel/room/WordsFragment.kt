package com.yuaihen.viewmodel.room

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuaihen.viewmodel.R
import kotlinx.android.synthetic.main.fragment_words.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by Yuaihen.
 * on 2020/12/7
 */
class WordsFragment : Fragment(), CoroutineScope {

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private lateinit var filteredWords: LiveData<List<Word>>
    private lateinit var wordViewModel: WordViewModel
    private lateinit var adapter1: RoomAdapter
    private lateinit var adapter2: RoomAdapter

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
        wordViewModel = ViewModelProvider(activity!!).get(WordViewModel::class.java)
        recyclerview.layoutManager = LinearLayoutManager(context)
        adapter1 = RoomAdapter(true, wordViewModel)
        adapter2 = RoomAdapter(false, wordViewModel)
        recyclerview.adapter = adapter1
        lifecycle.addObserver(wordViewModel.wordRepository)

        filteredWords = wordViewModel.getAllWordsLive()
        filteredWords.observe(activity!!) { words ->
            val temp = adapter1.itemCount
            adapter1.setWordsData(words)
            adapter2.setWordsData(words)
            if (temp != words.size) {
                adapter1.notifyDataSetChanged()
                adapter2.notifyDataSetChanged()
            }
        }

        floatingActionButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_wordsFragment_to_addFragment))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_room, menu)
        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.maxWidth = 750
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val pattern = newText.trim()
                launch {
                    filteredWords.removeObservers(activity!!)
                    filteredWords = wordViewModel.findWordsWithPattern(pattern)
                    filteredWords.observe(activity!!) { words ->
                        val temp = adapter1.itemCount
                        adapter1.setWordsData(words)
                        adapter2.setWordsData(words)
                        if (temp != words.size) {
                            adapter1.notifyDataSetChanged()
                            adapter2.notifyDataSetChanged()
                        }
                    }
                }
                return true
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}