package com.rickyputrah.currencyrate.screen.currency_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rickyputrah.currencyrate.R

class CurrencyListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.currency_list_activity)
        setupToolbar()
    }

    private fun setupToolbar() {
        supportActionBar?.hide()
    }

}