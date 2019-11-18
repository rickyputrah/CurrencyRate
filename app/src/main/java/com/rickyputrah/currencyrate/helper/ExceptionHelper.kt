package com.rickyputrah.currencyrate.helper

import com.rickyputrah.currencyrate.BuildConfig

fun Exception.log() {
    logError(this)
}

fun Throwable.log() {
    logError(this)
}

private fun logError(throwable: Throwable) {
    if (BuildConfig.DEBUG) {
        throwable.printStackTrace()
    }
}