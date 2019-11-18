package com.rickyputrah.currencyrate.screen.currency_list.usecase

import com.rickyputrah.currencyrate.model.currencyrate.CurrencyRateResponse
import com.rickyputrah.currencyrate.screen.currency_list.CurrencyItemViewModel
import com.rickyputrah.currencyrate.screen.currency_list.CurrencyListViewModel
import javax.inject.Inject

class CurrencyRateDataSetup @Inject constructor() {

    fun handleCurrencyRateResponse(
        response: CurrencyRateResponse,
        viewModel: CurrencyListViewModel
    ) {
        val currencyRateList = mutableListOf<CurrencyItemViewModel>()
        val rateList = response.rates.map {
            CurrencyItemViewModel(it.key, it.value)
        }.toMutableList()
        currencyRateList.add(CurrencyItemViewModel(response.base, 1.toDouble()))
        currencyRateList.addAll(rateList)
        viewModel.currencyList.postValue(currencyRateList)
    }
}