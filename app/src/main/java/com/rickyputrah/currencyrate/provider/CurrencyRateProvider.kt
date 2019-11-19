package com.rickyputrah.currencyrate.provider

import com.rickyputrah.currencyrate.model.currencyrate.CurrencyRateResponse
import com.rickyputrah.currencyrate.network.CurrencyRateApi
import io.reactivex.Observable
import javax.inject.Inject

class CurrencyRateProvider @Inject constructor(private val currencyRateApi: CurrencyRateApi) {

    fun getCurrencyRateList(baseCurrency: String): Observable<CurrencyRateResponse> {
        return currencyRateApi.getCurrencyRate(baseCurrency)
    }
}
