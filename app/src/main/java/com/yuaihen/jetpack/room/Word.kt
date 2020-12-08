package com.yuaihen.jetpack.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Yuaihen.
 * on 2020/12/3
 */
@Entity
class Word(
    @ColumnInfo(name = "english_word")
    var englishWord: String,
    @ColumnInfo(name = "chinese_meaning")
    var chineseMeaning: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "chinese_invisible")
    var chineseInvisible: Boolean = false


}