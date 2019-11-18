package com.rickyputrah.currencyrate.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rickyputrah.currencyrate.screen.currency_list.CurrencyListDH
import com.rickyputrah.currencyrate.screen.currency_list.CurrencyListViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val currencyListDH: CurrencyListDH) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyListViewModel::class.java)) {
            return CurrencyListViewModel(currencyListDH) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
