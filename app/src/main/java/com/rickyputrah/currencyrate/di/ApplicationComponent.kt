package com.rickyputrah.currencyrate.di

import com.rickyputrah.currencyrate.di.module.ServiceModule
import com.rickyputrah.currencyrate.screen.currency_list.CurrencyListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ServiceModule::class]
)
interface ApplicationComponent {
    fun getViewModelFactory(): ViewModelFactory

    fun inject(currencyListActivity: CurrencyListActivity)

}