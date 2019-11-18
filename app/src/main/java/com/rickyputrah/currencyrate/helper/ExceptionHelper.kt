package com.rickyputrah.currencyrate.helper

import android.util.Log
import com.rickyputrah.currencyrate.BuildConfig

fun Exception.log() {
    logError(this)
}

fun Throwable.log() {
    logError(this)
}

private fun logError(throwable: Throwable) {
    if (BuildConfig.DEBUG) {
        Log.e(throwable.javaClass.name, throwable.message)
        throwable.printStackTrace()
    }
}