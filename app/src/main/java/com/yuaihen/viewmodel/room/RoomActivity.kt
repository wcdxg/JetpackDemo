package com.yuaihen.viewmodel.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuaihen.viewmodel.R
import kotlinx.android.synthetic.main.activity_room.*

/**
 * Created by Yuaihen.
 * on 2020/12/2
 */
class RoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        val wordViewModel: WordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter1 = RoomAdapter(true, wordViewModel)
        val adapter2 = RoomAdapter(false, wordViewModel)
        recyclerview.adapter = adapter2
        switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) recyclerview.adapter = adapter1 else recyclerview.adapter = adapter2
        }

        lifecycle.addObserver(wordViewModel.wordRepository)
        wordViewModel.wordRepository.allWordsLive.observe(this, { words ->
            val temp = adapter1.itemCount
            adapter1.setWordsData(words)
            adapter2.setWordsData(words)
            if (temp != words.size) {
                adapter1.notifyDataSetChanged()
                adapter2.notifyDataSetChanged()
            }
        })

        insertBtn.setOnClickListener {
            val englishWord = mutableListOf<String>().apply {
                add("Integer")
                add("value")
                add("String")
                add("View")
                add("Recycler")
                add("Database")
                add("Project")
                add("Studio")
            }

            val chineseWord = mutableListOf<String>().apply {
                add("整数类型")
                add("价值")
                add("字符串")
                add("视图")
                add("回收站")
                add("数据库")
                add("项目")
                add("工作室")
            }

            englishWord.forEachIndexed { index, s ->
                wordViewModel.insertWord(Word(s, chineseWord[index]))
            }
        }

        clearBtn.setOnClickListener {
            wordViewModel.clearAll()
        }
    }


}