package com.yuaihen.jetpack.room

import android.app.AlertDialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import com.yuaihen.jetpack.R
import kotlinx.android.synthetic.main.fragment_words.*
import kotlinx.android.synthetic.main.item_room_card.view.*
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
    private lateinit var allWords: List<Word>
    private var undoAction = false
    private lateinit var itemDividerItemDecoration: DividerItemDecoration

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
        recyclerview.itemAnimator = object : DefaultItemAnimator() {
            override fun onAnimationFinished(viewHolder: RecyclerView.ViewHolder) {
                super.onAnimationFinished(viewHolder)
                val linearLayoutManager = recyclerview.layoutManager as LinearLayoutManager?
                linearLayoutManager?.let { manager ->
                    val firstPosition = manager.findFirstVisibleItemPosition()
                    val lastPosition = manager.findLastVisibleItemPosition()
                    for (index in firstPosition..lastPosition) {
                        val myViewHolder =
                            recyclerview.findViewHolderForAdapterPosition(index) as RoomAdapter.MyViewHolder?
                        myViewHolder?.let { holder ->
                            holder.itemView.indexText.text = index.plus(1).toString()
                        }
                    }
                }
            }
        }
        //侧滑删除  拖拽item
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                //数据库操作是异步的 因此如果拖动过快 可能数据还未写入 导致位置或者数据错乱
                //因此需要等待拖动完成再写入数据库或者提供一个按钮在拖动完成之后再进行数据库操作
//                val wordFrom = allWords[viewHolder.adapterPosition]
//                val wordTo = allWords[target.adapterPosition]
//                val tempId = wordFrom.id
//                wordFrom.id = wordTo.id
//                wordTo.id = tempId
//                //刷新数据库
//                wordViewModel.updateWords(wordFrom, wordTo)
//                //刷新adapter 数据没有改变 需要手动刷新
//                cardViewAdapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
//                normalAdapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val word = allWords[viewHolder.adapterPosition]
                wordViewModel.deleteWords(word)
                Snackbar.make(
                    requireActivity().findViewById(R.id.wordFragmentView),
                    "删除了一条数据",
                    Snackbar.LENGTH_SHORT
                )
                    .setAction("撤销") {
                        undoAction = true
                        wordViewModel.insertWord(word)
                    }
                    .show()

            }

            //在滑动的时候，画出浅灰色背景和垃圾桶图标，增强删除的视觉效果
            var icon = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.ic_delete_white_24dp
            )
            var background: Drawable = ColorDrawable(Color.RED)
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                val itemView = viewHolder.itemView
                val iconMargin = (itemView.height - icon!!.intrinsicHeight) / 2
                val iconLeft: Int
                val iconRight: Int
                val iconTop: Int
                val iconBottom: Int
                val backTop: Int
                val backBottom: Int
                val backLeft: Int
                val backRight: Int
                backTop = itemView.top
                backBottom = itemView.bottom
                iconTop = itemView.top + (itemView.height - icon!!.intrinsicHeight) / 2
                iconBottom = iconTop + icon!!.intrinsicHeight
                when {
                    dX > 0 -> {
                        backLeft = itemView.left
                        backRight = itemView.left + dX.toInt()
                        background.setBounds(backLeft, backTop, backRight, backBottom)
                        iconLeft = itemView.left + iconMargin
                        iconRight = iconLeft + icon!!.intrinsicWidth
                        icon!!.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    }
                    dX < 0 -> {
                        backRight = itemView.right
                        backLeft = itemView.right + dX.toInt()
                        background.setBounds(backLeft, backTop, backRight, backBottom)
                        iconRight = itemView.right - iconMargin
                        iconLeft = iconRight - icon!!.intrinsicWidth
                        icon!!.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    }
                    else -> {
                        background.setBounds(0, 0, 0, 0)
                        icon!!.setBounds(0, 0, 0, 0)
                    }
                }
                background.draw(c)
                icon!!.draw(c)
            }
        }).attachToRecyclerView(recyclerview)

        cardViewAdapter = RoomAdapter(true, wordViewModel)
        normalAdapter = RoomAdapter(false, wordViewModel)

        val shp = requireActivity().getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE)
        val isCardViewType = shp?.getBoolean(IS_USING_CARD_VIEW, false)
        itemDividerItemDecoration =
            DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
        if (isCardViewType!!) {
            recyclerview.adapter = cardViewAdapter
        } else {
            recyclerview.addItemDecoration(itemDividerItemDecoration)
            recyclerview.adapter = normalAdapter
        }
        lifecycle.addObserver(wordViewModel.wordRepository)

        filteredWords = wordViewModel.getAllWordsLive()
        filteredWords.observe(viewLifecycleOwner) { words ->
            val temp = cardViewAdapter.itemCount
            allWords = words
            if (temp != words.size) {

                cardViewAdapter.submitList(words)
                normalAdapter.submitList(words)
                if (temp < words.size && !undoAction) {
                    //插入一条新的数据
                    recyclerview.smoothScrollBy(0, -200)
                    undoAction = false
                }
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
                    filteredWords.removeObservers(viewLifecycleOwner)
                    filteredWords = wordViewModel.findWordsWithPattern(pattern)
                    filteredWords.observe(viewLifecycleOwner) { words ->
                        val temp = cardViewAdapter.itemCount
                        allWords = words
                        if (temp != words.size) {
                            cardViewAdapter.submitList(words)
                            normalAdapter.submitList(words)
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
                    recyclerview.addItemDecoration(itemDividerItemDecoration)
                    editor?.putBoolean(IS_USING_CARD_VIEW, false)
                } else {
                    recyclerview.removeItemDecoration(itemDividerItemDecoration)
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