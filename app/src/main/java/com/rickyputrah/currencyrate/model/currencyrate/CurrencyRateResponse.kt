package com.rickyputrah.currencyrate.model.currencyrate

data class CurrencyRateResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)