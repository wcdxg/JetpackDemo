package com.yuaihen.viewmodel.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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
        lifecycle.addObserver(wordViewModel.wordRepository)


        wordViewModel.wordRepository.allWordsLive.observe(this, { words ->
            val string = StringBuilder()
            words?.forEach {
                string.append(it.id).append("_").append(it.chineseMeaning).append(it.word)
                    .append("\n")
            }
            textView.text = string.toString()
        })

        insertBtn.setOnClickListener {
            val word = Word("Hello", "你好")
            val word2 = Word("Kotlin", "Android")
            wordViewModel.insertWord(word, word2)
        }

        updateBtn.setOnClickListener {
            val word = Word("Hello", "世界")
            word.id = 25
            wordViewModel.updateWords(word)
        }
        clearBtn.setOnClickListener {
            wordViewModel.clearAll()
        }
        deleteBtn.setOnClickListener {
            val word = Word("", "")
            word.id = 25
            wordViewModel.deleteWords(word)

        }
    }


}