package com.yuaihen.viewmodel.score

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Yuaihen.
 * on 2020/11/24
 */
class ScoreViewModel : ViewModel() {

    var scoreTeamA = MutableLiveData<Int>()
    var scoreTeamB = MutableLiveData<Int>()
    private var aBack = 0
    private var bBack = 0

    init {
        scoreTeamA.value = 0
        scoreTeamB.value = 0
    }

    fun addATeam(n: Int) {
        aBack = scoreTeamA.value!!
        bBack = scoreTeamB.value!!
        scoreTeamA.value = scoreTeamA.value?.plus(n)
    }


    fun addBTeam(n: Int) {
        aBack = scoreTeamA.value!!
        bBack = scoreTeamB.value!!
        scoreTeamB.value = scoreTeamB.value?.plus(n)
    }

    fun reset() {
        aBack = scoreTeamA.value!!
        bBack = scoreTeamB.value!!
        scoreTeamA.value = 0
        scoreTeamB.value = 0
    }

    fun undo() {
        scoreTeamA.value = aBack
        scoreTeamB.value = bBack
    }

}