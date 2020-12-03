package com.yuaihen.viewmodel.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Yuaihen.
 * on 2020/12/3
 */
@Entity
class WordJ(
    @field:ColumnInfo(name = "chinese_meaning") private var word: String?, @field:ColumnInfo(
        name = "word"
    ) private var chineseMeaning: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    fun getWord(): String {
        return if (word == null) "" else word!!
    }

    fun setWord(word: String?) {
        this.word = word
    }

    fun getChineseMeaning(): String {
        return if (chineseMeaning == null) "" else chineseMeaning!!
    }

    fun setChineseMeaning(chineseMeaning: String?) {
        this.chineseMeaning = chineseMeaning
    }
}