package com.yuaihen.viewmodel.room

import android.app.AlertDialog
import android.content.Context
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
    private lateinit var cardViewAdapter: RoomAdapter
    private lateinit var normalAdapter: RoomAdapter

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
        cardViewAdapter = RoomAdapter(true, wordViewModel)
        normalAdapter = RoomAdapter(false, wordViewModel)

        val shp = activity?.getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE)
        val isCardViewType = shp?.getBoolean(IS_USING_CARD_VIEW, false)
        if (isCardViewType!!) {
            recyclerview.adapter = cardViewAdapter
        } else {
            recyclerview.adapter = normalAdapter
        }
        lifecycle.addObserver(wordViewModel.wordRepository)

        filteredWords = wordViewModel.getAllWordsLive()
        filteredWords.observe(activity!!) { words ->
            val temp = cardViewAdapter.itemCount
            cardViewAdapter.setWordsData(words)
            normalAdapter.setWordsData(words)
            if (temp != words.size) {
                cardViewAdapter.notifyDataSetChanged()
                normalAdapter.notifyDataSetChanged()
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
                        val temp = cardViewAdapter.itemCount
                        cardViewAdapter.setWordsData(words)
                        normalAdapter.setWordsData(words)
                        if (temp != words.size) {
                            cardViewAdapter.notifyDataSetChanged()
                            normalAdapter.notifyDataSetChanged()
                        }
                    }
                }
                return true
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clearData -> {
                AlertDialog.Builder(activity)
                    .setTitle("清空数据")
                    .setPositiveButton(
                        "确定"
                    ) { _, _ -> wordViewModel.clearAll() }
                    .setNegativeButton(
                        "取消"
                    ) { _, _ -> }
                    .create()
                    .show()
            }
            R.id.switchView -> {
                val shp = activity?.getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE)
                val isCardViewType = shp?.getBoolean(IS_USING_CARD_VIEW, false)
                val editor = shp?.edit()
                if (isCardViewType!!) {
                    recyclerview.adapter = normalAdapter
                    editor?.putBoolean(IS_USING_CARD_VIEW, false)
                } else {
                    recyclerview.adapter = cardViewAdapter
                    editor?.putBoolean(IS_USING_CARD_VIEW, true)
                }
                editor?.apply()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    companion object {
        const val VIEW_TYPE_SHP = "view_type_shp"
        const val IS_USING_CARD_VIEW = "is_using_card_view"
    }
}