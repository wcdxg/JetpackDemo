package com.yuaihen.viewmodel.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.yuaihen.viewmodel.R
import kotlinx.android.synthetic.main.activity_room.*

/**
 * Created by Yuaihen.
 * on 2020/12/2
 */
class RoomActivity : AppCompatActivity() {
    private lateinit var wordDao: WordDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        val wordDatabase = Room.databaseBuilder(this, WordDatabase::class.java, "word_database")
            .allowMainThreadQueries()
            .build()
        wordDao = wordDatabase.getWordDao()
        updateView()

        insertBtn.setOnClickListener {
            val word = Word("Hello", "你好")
            val word2 = Word("Kotlin", "Android")
            wordDao.insertWord(word, word2)
            updateView()
        }

        updateBtn.setOnClickListener {
            val word = Word("Hello", "世界")
            word.id = 8
            wordDao.updateWords(word)
            updateView()
        }
        clearBtn.setOnClickListener {
            val word = Word("", "")
            word.id = 8
            wordDao.deleteWords(word)
            updateView()
        }
        deleteBtn.setOnClickListener {
            wordDao.deleteAllWords()
            updateView()

        }
    }

    fun updateView() {
        val string = StringBuilder()
        wordDao.getAllWords().forEach {
            string.append(it.id).append("_").append(it.chineseMeaning).append(it.word).append("\n")
        }

        textView.text = string.toString()
    }
}