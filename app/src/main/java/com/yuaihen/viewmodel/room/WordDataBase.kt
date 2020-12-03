package com.yuaihen.viewmodel.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Yuaihen.
 * on 2020/12/3
 */
@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {

    abstract fun getWordDao(): WordDao
}