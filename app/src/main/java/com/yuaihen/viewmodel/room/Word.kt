package com.yuaihen.viewmodel.room

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
    var word: String,
    @ColumnInfo(name = "chinese_meaning")
    var chineseMeaning: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}