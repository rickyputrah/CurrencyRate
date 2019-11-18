package com.rickyputrah.currencyrate

import android.app.Application
import com.rickyputrah.currencyrate.di.DIApplicationManager

class CurrencyRateApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DIApplicationManager.initApplicationComponent()
    }
}