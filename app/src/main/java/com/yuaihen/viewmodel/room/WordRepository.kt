package com.yuaihen.viewmodel.room

import android.app.Application
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by Yuaihen.
 * on 2020/12/3
 */
class WordRepository(application: Application) : LifecycleObserver, CoroutineScope {

    private var job: Job = Job()
    private var wordDatabase: WordDatabase = WordDatabase.getInstance(application)
    private var wordDao = wordDatabase.getWordDao()
    var allWordsLive: LiveData<List<Word>> = wordDao.getAllWordsLive()

    fun insertWord(vararg words: Word) {
        launch {
            withContext(Dispatchers.IO) {
                wordDao.insertWord(*words)
            }
        }
    }

    fun updateWords(vararg words: Word) {
        launch {
            withContext(Dispatchers.IO) {
                wordDao.updateWords(*words)
            }
        }
    }

    fun deleteWords(vararg words: Word) {
        launch {
            withContext(Dispatchers.IO) {
                wordDao.deleteWords(*words)
            }
        }
    }

    fun clearAll() {
        launch {
            withContext(Dispatchers.IO) {
                wordDao.clearAllWords()
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cancelJob() {
        Log.d("Job", "cancelJob: ")
        job.cancel()
    }
}