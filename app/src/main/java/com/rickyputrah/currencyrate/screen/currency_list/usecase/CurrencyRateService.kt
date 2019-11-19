package com.rickyputrah.currencyrate.screen.currency_list.usecase

import com.rickyputrah.currencyrate.constant.DELAY_TIME_IN_SECOND
import com.rickyputrah.currencyrate.model.currencyrate.CurrencyRateResponse
import com.rickyputrah.currencyrate.provider.CurrencyRateProvider
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrencyRateService @Inject constructor(private val currencyRateProvider: CurrencyRateProvider) {


    fun getCurrencyRateList(baseCurrency: String): Observable<CurrencyRateResponse> {
        return currencyRateProvider.getCurrencyRateList(baseCurrency)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
    }


    fun delayForCallCurrencyRateList(): Observable<Boolean> {
        return Observable.just(true).delay(DELAY_TIME_IN_SECOND, TimeUnit.SECONDS)
    }


}