package com.rickyputrah.currencyrate.provider

import com.rickyputrah.currencyrate.model.currencyrate.CurrencyRateResponse
import com.rickyputrah.currencyrate.network.CurrencyRateApi
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyRateProviderTest {

    private val mockCurrencyRateApi: CurrencyRateApi = mockk(relaxed = true)

    private val rateProvider = CurrencyRateProvider(mockCurrencyRateApi)

    @Test
    fun `should call getCurrencyRate from currencyRate api when call getCurrencyRateList`() {
        //Given
        val baseCurr = "EUR"

        //When
        rateProvider.getCurrencyRateList(baseCurr)

        //Then
        verify { mockCurrencyRateApi.getCurrencyRate(baseCurr) }
    }


    @Test
    fun `should return observable currencyRateResponse from currencyRateApi when call getCurrencyRateList`() {
        //Given
        val baseCurr = "EUR"
        val mockCurrencyRateResponse = CurrencyRateResponse(
            base = "EUR",
            date = "22-12-2019",
            rates = mapOf()
        )
        val expectedResult = Observable.just(mockCurrencyRateResponse)
        every { mockCurrencyRateApi.getCurrencyRate(baseCurr) } returns expectedResult

        //When
        val result = rateProvider.getCurrencyRateList(baseCurr)

        //Then
        assertEquals(expectedResult, result)
    }

}