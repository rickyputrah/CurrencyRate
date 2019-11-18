package com.rickyputrah.currencyrate.network

import com.rickyputrah.currencyrate.model.currencyrate.CurrencyRateResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRateApi {

    /**
     * Get the list of the latest currency rate from the API
     */
    @GET("/latest")
    fun getCurrencyRate(@Query("base") baseCurrencyCode: String): Observable<CurrencyRateResponse>

}