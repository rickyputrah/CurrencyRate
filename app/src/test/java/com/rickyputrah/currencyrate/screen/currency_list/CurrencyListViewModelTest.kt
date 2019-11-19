package com.rickyputrah.currencyrate.screen.currency_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rickyputrah.currencyrate.RxSchedulerRule
import com.rickyputrah.currencyrate.constant.DEFAULT_BASE_CURRENCY
import com.rickyputrah.currencyrate.model.currencyrate.CurrencyRateResponse
import com.rickyputrah.currencyrate.screen.currency_list.usecase.CurrencyRateDataSetup
import com.rickyputrah.currencyrate.screen.currency_list.usecase.CurrencyRateService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.plugins.RxJavaPlugins
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CurrencyListViewModelTest {

    private val mockCurrencyRateDataSetup: CurrencyRateDataSetup = mockk(relaxed = true)
    private val mockCurrencyRateService: CurrencyRateService = mockk(relaxed = true)

    private val mockCurrencyListDH =
        CurrencyListDH(mockCurrencyRateDataSetup, mockCurrencyRateService)

    private val viewModel = CurrencyListViewModel(mockCurrencyListDH)

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var rxRule: TestRule = RxSchedulerRule()

    @Before
    fun setUp() = RxJavaPlugins.reset()

    @After
    fun tearDown() = RxJavaPlugins.reset()

    @Test
    fun `should call delayForCallCurrencyRateList from currencyRateService when call loadCurrencyRateListInterval`() {
        //Given

        //When
        viewModel.loadCurrencyRateListInterval()

        //Then
        verify(exactly = 1) { mockCurrencyListDH.currencyRateService.delayForCallCurrencyRateList() }

    }

    @Test
    fun `should call getCurrencyRateList from currencyRateService when call loadCurrencyRateList`() {
        //Given
        viewModel.currencyList.value = null
        val expectedDefaultBaseCurr = DEFAULT_BASE_CURRENCY

        //When
        viewModel.loadCurrencyRateList()

        //Then
        verify(exactly = 1) {
            mockCurrencyListDH.currencyRateService.getCurrencyRateList(
                expectedDefaultBaseCurr
            )
        }
    }

    @Test
    fun `should call getCurrencyRateList from currencyRateService using base currency code when call loadCurrencyRateList`() {
        //Given
        val usdCurrencyCode = "USD"
        viewModel.currencyList.value =
            mutableListOf(CurrencyItemViewModel(1, usdCurrencyCode, "USD", 1.0, "1"))

        //When
        viewModel.loadCurrencyRateList()

        //Then
        verify(exactly = 1) {
            mockCurrencyListDH.currencyRateService.getCurrencyRateList(
                usdCurrencyCode
            )
        }
    }

    @Test
    fun `should call handleCurrencyRateResponse when getCurrencyList is success`() {
        //Given
        val baseCurrency = "EUR"
        val mockCurrencyRateResponse = CurrencyRateResponse(
            base = "EUR",
            date = "22-12-2019",
            rates = mapOf()
        )
        every { mockCurrencyListDH.currencyRateService.getCurrencyRateList(baseCurrency) } returns Observable.just(
            mockCurrencyRateResponse
        )

        //When
        viewModel.loadCurrencyRateList()

        //Then
        verify(exactly = 1) {
            mockCurrencyListDH.currencyRateDataSetup.handleCurrencyRateResponse(
                mockCurrencyRateResponse,
                any()
            )
        }
    }

    @Test
    fun `should update display value first item with updated amount when call updateBaseValue`() {
        //Given
        val expectedAmount = "1000"
        viewModel.currencyList.value = mutableListOf(CurrencyItemViewModel(1, "", "USD", 1.0, "1"))

        //When
        viewModel.updateBaseValue(expectedAmount)

        //Then
        assertEquals(expectedAmount, viewModel.currencyList.value?.get(0)?.displayValue)
    }
}