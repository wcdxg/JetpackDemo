package com.yuaihen.viewmodel.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel

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

}