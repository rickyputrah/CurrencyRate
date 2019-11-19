package com.rickyputrah.currencyrate.screen.currency_list.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rickyputrah.currencyrate.RxSchedulerRule
import com.rickyputrah.currencyrate.constant.CURRENCY_NAMES
import com.rickyputrah.currencyrate.constant.DEFAULT_CURRENCY_VALUE
import com.rickyputrah.currencyrate.helper.currencyFormatter
import com.rickyputrah.currencyrate.helper.getCurrencyStringValue
import com.rickyputrah.currencyrate.model.currencyrate.CurrencyRateResponse
import com.rickyputrah.currencyrate.screen.currency_list.CurrencyItemViewModel
import io.reactivex.plugins.RxJavaPlugins
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CurrencyRateDataSetupTest {

    private val currencyDataSetup = CurrencyRateDataSetup()

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
    fun `should input all rates including base to currencyList viewModel when call handleCurrencyRateResponse and viewModel currencyList still empty `() {
        //Given
        val mockCurrencyRateResponse = CurrencyRateResponse(
            base = "EUR",
            date = "22-12-2019",
            rates = mapOf("EUR" to 1.0, "USD" to 2.0, "IDR" to 3.0)
        )
        val currencyItemList = mutableListOf<CurrencyItemViewModel>()

        //When
        val result =
            currencyDataSetup.handleCurrencyRateResponse(mockCurrencyRateResponse, currencyItemList)

        //Then
        assertEquals(mockCurrencyRateResponse.rates.size + 1, result.size)
    }

    @Test
    fun `should prepare currency Base Currency data on the first list when call handleCurrencyRateResponse and viewModel currencyList still empty `() {
        //Given
        val mockCurrencyRateResponse = CurrencyRateResponse(
            base = "EUR",
            date = "22-12-2019",
            rates = mapOf("EUR" to 1.0, "USD" to 2.0, "IDR" to 3.0)
        )
        val currencyItemList = mutableListOf<CurrencyItemViewModel>()

        //When
        val result =
            currencyDataSetup.handleCurrencyRateResponse(mockCurrencyRateResponse, currencyItemList)

        //Then
        assertEquals(4, result[0].currencyId)
        assertEquals(mockCurrencyRateResponse.base, result[0].currencyCode)
        assertEquals(CURRENCY_NAMES[result[0].currencyCode], result[0].currencyName)
        assertEquals(DEFAULT_CURRENCY_VALUE, result[0].rate, 0.0)
        assertEquals(DEFAULT_CURRENCY_VALUE.currencyFormatter(), result[0].displayValue)
    }

    @Test
    fun `should prepare Currency data from response rates start from index 1 when call handleCurrencyRateResponse and viewModel currencyList still empty `() {
        //Given
        val mockCurrencyRateResponse = CurrencyRateResponse(
            base = "EUR",
            date = "22-12-2019",
            rates = mapOf("EUR" to 1.0)
        )
        val currencyItemList = mutableListOf<CurrencyItemViewModel>()


        //When
        val result =
            currencyDataSetup.handleCurrencyRateResponse(mockCurrencyRateResponse, currencyItemList)

        //Then
        assertEquals(1, result[1].currencyId)
        assertEquals("EUR", result[1].currencyCode)
        assertEquals(CURRENCY_NAMES["EUR"], result[1].currencyName)
        assertEquals(1.0, result[1].rate, 0.0)
        assertEquals((DEFAULT_CURRENCY_VALUE * 1.0).currencyFormatter(), result[1].displayValue)
    }

    @Test
    fun `should update currencyList rate data when call handleCurrencyRateResponse and viewModel currencyList is not empty `() {
        //Given
        val mockCurrencyRateResponse = CurrencyRateResponse(
            base = "EUR",
            date = "22-12-2019",
            rates = mapOf("USD" to 2.0, "IDR" to 3.0)
        )
        val currencyItemList = mutableListOf<CurrencyItemViewModel>(
            CurrencyItemViewModel(1, "EUR", "EUR", 1.0, "100.21"),
            CurrencyItemViewModel(1, "USD", "USD", 1.0, "100.21"),
            CurrencyItemViewModel(1, "IDR", "IDR", 1.0, "100.21")
        )
        val baseValue = currencyItemList.get(0).displayValue.getCurrencyStringValue()

        //When
        val result =
            currencyDataSetup.handleCurrencyRateResponse(mockCurrencyRateResponse, currencyItemList)

        //Then
        result.forEachIndexed { index, item ->
            if (index == 0) { //BASE
                assertEquals(
                    1.0,
                    currencyItemList[index].rate,
                    0.0
                )
                assertEquals(
                    (1.0 * baseValue).currencyFormatter(),
                    currencyItemList[index].displayValue
                )
            } else {
                assertEquals(
                    mockCurrencyRateResponse.rates.getValue(item.currencyCode),
                    currencyItemList[index].rate,
                    0.0
                )
                assertEquals(
                    (mockCurrencyRateResponse.rates.getValue(item.currencyCode) * baseValue).currencyFormatter(),
                    currencyItemList[index].displayValue
                )
            }
        }
    }

}