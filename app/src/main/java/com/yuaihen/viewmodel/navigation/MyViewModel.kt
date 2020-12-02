package com.yuaihen.viewmodel.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Yuaihen.
 * on 2020/12/1
 */
class MyViewModel : ViewModel() {

    private var number: MutableLiveData<Int> = MutableLiveData(0)

    fun getNumber(): MutableLiveData<Int> {
        return number
    }

    fun add(value: Int) {
        number.value = number.value?.plus(value)
    }


}