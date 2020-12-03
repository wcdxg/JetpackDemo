package com.yuaihen.viewmodel.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.yuaihen.viewmodel.R
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by Yuaihen.
 * on 2020/12/2
 */
class RoomActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var wordDao: WordDao
    private lateinit var allWordsLive: LiveData<List<Word>>
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        job = Job()

//        val wordDatabase = Room.databaseBuilder(this, WordDatabase::class.java, "word_database")
//            .allowMainThreadQueries()
//            .build()
        wordDao = WordDatabase.getInstance(applicationContext).getWordDao()
        allWordsLive = wordDao.getAllWords()
        allWordsLive.observe(this, object : Observer<List<Word>> {
            override fun onChanged(words: List<Word>?) {
                val string = StringBuilder()
                words?.forEach {
                    string.append(it.id).append("_").append(it.chineseMeaning).append(it.word)
                        .append("\n")
                }
                textView.text = string.toString()
            }
        })

        insertBtn.setOnClickListener {
            val word = Word("Hello", "你好")
            val word2 = Word("Kotlin", "Android")
            launch {
                insertWord(word, word2)
            }
        }

        updateBtn.setOnClickListener {
            val word = Word("Hello", "世界")
            word.id = 25
            launch {
                updateWords()
            }
        }
        clearBtn.setOnClickListener {
            val word = Word("", "")
            word.id = 25
            launch {
                deleteWords()
            }
        }
        deleteBtn.setOnClickListener {
            launch {
                deleteAllWords()
            }

        }
    }


    private suspend fun insertWord(vararg words: Word) = withContext(Dispatchers.IO) {
        wordDao.insertWord(*words)
    }

    private suspend fun updateWords(vararg words: Word) = withContext(Dispatchers.IO) {
        wordDao.updateWords(*words)
    }

    private suspend fun deleteWords(vararg words: Word) = withContext(Dispatchers.IO) {
        wordDao.deleteWords(*words)
    }

    private suspend fun deleteAllWords() = withContext(Dispatchers.IO) {
        wordDao.deleteAllWords()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}