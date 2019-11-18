package com.rickyputrah.currencyrate.screen.currency_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rickyputrah.currencyrate.constant.DEFAULT_BASE_CURRENCY
import com.rickyputrah.currencyrate.constant.DEFAULT_CURRENCY_VALUE
import com.rickyputrah.currencyrate.constant.DELAY_TIME_IN_SECOND
import com.rickyputrah.currencyrate.helper.getCurrencyStringValue
import com.rickyputrah.currencyrate.helper.log
import com.rickyputrah.currencyrate.provider.CurrencyRateProvider
import com.rickyputrah.currencyrate.screen.currency_list.usecase.CurrencyRateDataSetup
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrencyListDH @Inject constructor(
    val currencyRateProvider: CurrencyRateProvider,
    val currencyRateDataSetup: CurrencyRateDataSetup
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
            Observable.just("").delay(DELAY_TIME_IN_SECOND, TimeUnit.SECONDS).subscribe {
                loadCurrencyRateList()
            })
    }

    fun loadCurrencyRateList() {
        subscription.clear()
        val subs = dependenciesHolder.currencyRateProvider.getCurrencyRateList(getBaseCurrency())
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribe(
                { response ->
                    dependenciesHolder.currencyRateDataSetup.handleCurrencyRateResponse(
                        response, this, getCurrencyValue()
                    )
                }, { it.log() })
        subscription.add(subs)
    }

    fun updateBaseValue(amount: String) {
        currencyList.value?.get(0)?.displayValue = amount
    }

    private fun getBaseCurrency(): String {
        return currencyList.value?.get(0)?.currencyCode ?: DEFAULT_BASE_CURRENCY
    }

    private fun getCurrencyValue(): Double {
        return currencyList.value?.get(0)?.displayValue?.getCurrencyStringValue()
            ?: DEFAULT_CURRENCY_VALUE
    }
}