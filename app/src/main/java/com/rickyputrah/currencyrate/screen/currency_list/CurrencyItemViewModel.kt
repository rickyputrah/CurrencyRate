package com.rickyputrah.currencyrate.screen.currency_list

data class CurrencyItemViewModel(
    var currencyId: Long,
    var currencyCode: String,
    var currencyName: String,
    var rate: Double,
    var displayValue: String
)