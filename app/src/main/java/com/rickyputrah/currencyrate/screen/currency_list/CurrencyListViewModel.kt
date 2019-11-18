package com.rickyputrah.currencyrate.screen.currency_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rickyputrah.currencyrate.constant.DEFAULT_BASE_CURRENCY
import com.rickyputrah.currencyrate.helper.log
import com.rickyputrah.currencyrate.provider.CurrencyRateProvider
import com.rickyputrah.currencyrate.screen.currency_list.usecase.CurrencyRateDataSetup
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CurrencyListDH @Inject constructor(
    val currencyRateProvider: CurrencyRateProvider,
    val currencyRateDataSetup: CurrencyRateDataSetup
)

class CurrencyListViewModel constructor(private val dependenciesHolder: CurrencyListDH) :
    ViewModel() {

    var currencyList: MutableLiveData<MutableList<CurrencyItemViewModel>> = MutableLiveData()

    private lateinit var subscription: Disposable

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun loadCurrencyRateList(baseCurrency: String = DEFAULT_BASE_CURRENCY) {
        subscription = dependenciesHolder.currencyRateProvider.getCurrencyRateList(baseCurrency)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribe({
                dependenciesHolder.currencyRateDataSetup.handleCurrencyRateResponse(
                    it,
                    this
                )
            }, { it.log() })
    }
}