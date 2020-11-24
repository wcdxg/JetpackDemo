package com.yuaihen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Yuaihen.
 * on 2020/10/10
 */
class MyViewModel : ViewModel() {

    var number: MutableLiveData<Int> = MutableLiveData()

    init {
        number.value = 0
    }


    fun add() {
        number.value = number.value?.plus(1)
    }

    fun min() {
        number.value = number.value?.minus(1)
    }
}