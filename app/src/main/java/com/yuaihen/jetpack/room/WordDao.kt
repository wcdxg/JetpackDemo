package com.yuaihen.jetpack.room

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by Yuaihen.
 * on 2020/12/3
 */
@Dao
interface WordDao {

    @Insert
    fun insertWord(vararg words: Word)

    @Update
    fun updateWords(vararg words: Word)

    @Delete
    fun deleteWords(vararg words: Word)

    @Query("DELETE FROM WORD")
    fun clearAllWords()

    @Query("SELECT * FROM WORD ORDER BY id DESC")
//    fun getAllWords(): List<Word>
    fun getAllWordsLive(): LiveData<List<Word>>

    @Query("SELECT * FROM WORD WHERE english_word LIKE :pattern ORDER BY ID DESC")
    fun findWordsWithPattern(pattern: String): LiveData<List<Word>>

}