package com.rickyputrah.currencyrate.screen.currency_list.usecase

import com.rickyputrah.currencyrate.provider.CurrencyRateProvider
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class CurrencyRateServiceTest {

    private val mockCurrencyRateProvider: CurrencyRateProvider = mockk(relaxed = true)

    private val rateService = CurrencyRateService(mockCurrencyRateProvider)

    @Before
    fun setUp() = RxJavaPlugins.reset()

    @After
    fun tearDown() = RxJavaPlugins.reset()

    @Test
    fun `should delay 1 second when call loadCurrencyRateListInterval`() {
        //Given
        val testScheduler = TestScheduler()
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }

        //When
        val observer = rateService.delayForCallCurrencyRateList().test()

        //Then
        testScheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
        observer.assertEmpty()
        observer.assertNotTerminated()
        testScheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
        observer.assertValues(true)
    }

    @Test
    fun `should call getCurrencyRateList from currencyRateProvider using baseCurrency when call getCurrencyRateList`() {
        //Given
        val baseCurrency = "EUR"

        //When
        rateService.getCurrencyRateList(baseCurrency)


        //Then
        verify { mockCurrencyRateProvider.getCurrencyRateList(baseCurrency) }
    }
}