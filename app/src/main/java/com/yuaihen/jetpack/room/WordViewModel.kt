package com.yuaihen.jetpack.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

/**
 * Created by Yuaihen.
 * on 2020/12/3
 */
class WordViewModel(application: Application) : AndroidViewModel(application) {

    val wordRepository = WordRepository(application)

    fun insertWord(vararg words: Word) {
        wordRepository.insertWord(*words)
    }

    fun updateWords(vararg words: Word) {
        wordRepository.updateWords(*words)
    }

    fun deleteWords(vararg words: Word) {
        wordRepository.deleteWords(*words)
    }

    fun clearAll() {
        wordRepository.clearAll()
    }

    suspend fun findWordsWithPattern(pattern: String): LiveData<List<Word>> {
        return wordRepository.findWordsWithPattern(pattern)
    }

    fun getAllWordsLive(): LiveData<List<Word>> {
        return wordRepository.allWordsLive
    }


}