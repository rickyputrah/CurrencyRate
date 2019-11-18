package com.rickyputrah.currencyrate.screen.currency_list.usecase

import com.rickyputrah.currencyrate.constant.CURRENCY_NAMES
import com.rickyputrah.currencyrate.constant.DEFAULT_CURRENCY_VALUE
import com.rickyputrah.currencyrate.helper.currencyFormatter
import com.rickyputrah.currencyrate.model.currencyrate.CurrencyRateResponse
import com.rickyputrah.currencyrate.screen.currency_list.CurrencyItemViewModel
import com.rickyputrah.currencyrate.screen.currency_list.CurrencyListViewModel
import javax.inject.Inject

class CurrencyRateDataSetup @Inject constructor() {

    fun handleCurrencyRateResponse(
        response: CurrencyRateResponse,
        viewModel: CurrencyListViewModel,
        baseValue: Double
    ) {
        val currencyList = viewModel.currencyList.value ?: mutableListOf()
        if (currencyList.isNullOrEmpty()) {
            prepareEmptyCurrencyList(response, currencyList, baseValue)
        } else {
            updateCurrencyList(response, currencyList, baseValue)
        }
        viewModel.currencyList.postValue(currencyList)
    }

    private fun prepareEmptyCurrencyList(
        response: CurrencyRateResponse,
        currencyList: MutableList<CurrencyItemViewModel>,
        baseValue: Double
    ) {
        var currencyId: Long = 1
        val rateList = response.rates.map {
            CurrencyItemViewModel(
                currencyId++,
                it.key,
                CURRENCY_NAMES[it.key] ?: "",
                it.value,
                (baseValue * it.value).currencyFormatter()
            )
        }.toMutableList()
        currencyList.add(
            CurrencyItemViewModel(
                currencyId++,
                response.base,
                CURRENCY_NAMES[response.base] ?: "",
                DEFAULT_CURRENCY_VALUE,
                DEFAULT_CURRENCY_VALUE.currencyFormatter()
            )
        )
        currencyList.addAll(rateList)

    }

    private fun updateCurrencyList(
        response: CurrencyRateResponse,
        currencyList: MutableList<CurrencyItemViewModel>,
        baseValue: Double
    ) {
        currencyList.forEach {
            val item = response.rates[it.currencyCode]
            it.rate = item ?: 1.0
            it.displayValue = (baseValue * it.rate).currencyFormatter()
        }
    }
}