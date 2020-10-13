package com.yuaihen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Yuaihen.
 * on 2020/10/10
 */
class MyViewModel : ViewModel() {

    var likeNum: MutableLiveData<Int> = MutableLiveData()

    init {
        likeNum.value = 0
    }


}