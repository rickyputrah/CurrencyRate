package com.rickyputrah.currencyrate.helper

import android.view.View

fun Boolean.toVisibility(
    trueVisibility: Int = View.VISIBLE,
    falseVisibility: Int = View.GONE
): Int = if (this) trueVisibility else falseVisibility