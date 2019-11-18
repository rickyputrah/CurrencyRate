package com.rickyputrah.currencyrate.helper

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun Double.currencyFormatter(): String {
    val locale = Locale("en")
    val decimalFormatSymbols = DecimalFormatSymbols(locale)
    decimalFormatSymbols.decimalSeparator = '.'
    decimalFormatSymbols.groupingSeparator = ','
    val amountFormat = "#,##0.##"
    val decimalFormat = DecimalFormat(amountFormat, decimalFormatSymbols)
    return decimalFormat.format(this)
}

fun String.getCurrencyStringValue(): Double {
    return this.replace(",".toRegex(), "").toDouble()
}