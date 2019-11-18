package com.rickyputrah.currencyrate.provider

import com.rickyputrah.currencyrate.model.currencyrate.CurrencyRateResponse
import com.rickyputrah.currencyrate.network.CurrencyRateApi
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CurrencyRateProvider @Inject constructor(private val currencyRateApi: CurrencyRateApi) {

    fun loadCategories(baseCurrency: String = ""): Observable<CurrencyRateResponse> {
        return currencyRateApi.getCurrencyRate(baseCurrency).observeOn(Schedulers.io())
    }
}
