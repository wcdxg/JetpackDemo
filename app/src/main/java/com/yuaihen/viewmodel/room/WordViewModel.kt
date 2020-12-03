package com.yuaihen.viewmodel.room

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by Yuaihen.
 * on 2020/12/3
 */
class WordViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver,
    CoroutineScope {

    private var job: Job = Job()
    private var wordDatabase: WordDatabase = WordDatabase.getInstance(application)
    private var wordDao = wordDatabase.getWordDao()
    var allWordsLive: LiveData<List<Word>> = wordDao.getAllWordsLive()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun insertWordTask(vararg words: Word) {
        launch {
            insertWord(*words)
        }
    }

    fun updateWordsTask(vararg words: Word) {
        launch {
            updateWords(*words)
        }
    }

    fun deleteWordsTask(vararg words: Word) {
        launch {
            deleteWords(*words)
        }
    }

    fun deleteAllWordsTask() {
        launch {
            deleteAllWords()
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


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cancelJob() {
        Log.d("Job", "cancelJob: ")
        job.cancel()
    }


}