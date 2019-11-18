package com.rickyputrah.currencyrate.helper

import android.view.View
import com.jakewharton.rxbinding.view.RxView
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


fun View.setDebounceClickListener(action: () -> Unit) {
    RxView.clicks(this)
        .throttleFirst(500.toLong(), TimeUnit.MILLISECONDS)
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe({ action.invoke() }, { it.log() })
}
