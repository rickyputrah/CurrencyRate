package com.rickyputrah.currencyrate.di

import com.rickyputrah.currencyrate.di.module.ServiceModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ServiceModule::class]
)
interface ApplicationComponent {
    fun getViewModelFactory(): ViewModelFactory

}