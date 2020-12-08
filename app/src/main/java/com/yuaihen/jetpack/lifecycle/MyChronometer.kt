package com.yuaihen.jetpack.lifecycle

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.widget.Chronometer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * Created by Yuaihen.
 * on 2020/12/2
 */
class MyChronometer : Chronometer, LifecycleObserver {
    private var elapsedTime = 0L

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pauseMeter() {
        elapsedTime = SystemClock.elapsedRealtime() - base
        stop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startMeter() {
        base = SystemClock.elapsedRealtime() - elapsedTime
        start()
    }
}