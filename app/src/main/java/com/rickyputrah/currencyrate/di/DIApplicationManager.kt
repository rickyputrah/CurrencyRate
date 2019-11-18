package com.rickyputrah.currencyrate.di

import com.rickyputrah.currencyrate.di.module.ServiceModule

fun getApplicationComponent() = DIApplicationManager.applicationComponent

object DIApplicationManager {
    lateinit var applicationComponent: ApplicationComponent

    fun initApplicationComponent() {
        this.applicationComponent = buildApplicationComponent()
    }

    private fun buildApplicationComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder().serviceModule(ServiceModule()).build()
    }
}
