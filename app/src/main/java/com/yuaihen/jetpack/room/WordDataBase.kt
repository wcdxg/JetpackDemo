package com.yuaihen.jetpack.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Created by Yuaihen.
 * on 2020/12/3
 */
@Database(entities = [Word::class], version = 4, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {

    abstract fun getWordDao(): WordDao

    companion object {
        @Volatile
        private var instance: WordDatabase? = null

        fun getInstance(context: Context): WordDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): WordDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                WordDatabase::class.java,
                "word_database"
            )
                //破坏式迁移数据库
                .fallbackToDestructiveMigration()
//                .addMigrations(MIGRATION_3_4)
                .build()
        }

        val MIGRATION_3_4: Migration = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE word ADD COLUMN chinese_invisible INTEGER NOT NULL DEFAULT 1")
            }
        }
    }
}