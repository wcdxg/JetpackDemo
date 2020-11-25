package com.yuaihen.viewmodel.savestate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.yuaihen.viewmodel.savestate.SaveStateActivity.Companion.KEY_NUMBER

/**
 * Created by Yuaihen.
 * on 2020/11/25
 * 使用SavedStateHandle保存状态
 */
class SaveStateViewModel(val handle: SavedStateHandle) : ViewModel() {

    fun getNumber(): MutableLiveData<Int> {
        if (!handle.contains(KEY_NUMBER)) {
            handle.set(KEY_NUMBER, 0)
        }
        return handle.getLiveData(KEY_NUMBER)
    }

    fun add() {
        getNumber().value = getNumber().value?.plus(1)
    }

}