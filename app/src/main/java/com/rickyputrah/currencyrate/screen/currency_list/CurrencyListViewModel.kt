package com.rickyputrah.currencyrate.screen.currency_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rickyputrah.currencyrate.constant.DEFAULT_BASE_CURRENCY
import com.rickyputrah.currencyrate.helper.log
import com.rickyputrah.currencyrate.model.currencyrate.CurrencyRateResponse
import com.rickyputrah.currencyrate.screen.currency_list.usecase.CurrencyRateDataSetup
import com.rickyputrah.currencyrate.screen.currency_list.usecase.CurrencyRateService
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CurrencyListDH @Inject constructor(
    val currencyRateDataSetup: CurrencyRateDataSetup,
    val currencyRateService: CurrencyRateService
)

class CurrencyListViewModel constructor(private val dependenciesHolder: CurrencyListDH) :
    ViewModel() {

    var currencyList: MutableLiveData<MutableList<CurrencyItemViewModel>> = MutableLiveData()

    private var subscription: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        subscription.clear()
    }

    fun loadCurrencyRateListInterval() {
        subscription.clear()
        subscription.add(
            dependenciesHolder.currencyRateService.delayForCallCurrencyRateList().subscribe {
                loadCurrencyRateList()
            }
        )
    }

    fun loadCurrencyRateList() {
        subscription.clear()
        subscription.add(
            dependenciesHolder.currencyRateService.getCurrencyRateList(getBaseCurrency())
                .subscribe({ response ->
                    handleResponse(response)
                }, { it.log() })
        )
    }

    fun updateBaseValue(amount: String) {
        currencyList.value?.getOrNull(0)?.displayValue = amount
    }

    private fun handleResponse(response: CurrencyRateResponse) {
        val result = dependenciesHolder.currencyRateDataSetup.handleCurrencyRateResponse(
            response,
            currencyList.value
        )
        currencyList.postValue(result)
    }

    private fun getBaseCurrency(): String {
        return currencyList.value?.getOrNull(0)?.currencyCode ?: DEFAULT_BASE_CURRENCY
    }
}