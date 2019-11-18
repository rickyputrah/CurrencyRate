package com.rickyputrah.currencyrate.screen.currency_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rickyputrah.currencyrate.R
import com.rickyputrah.currencyrate.di.ViewModelFactory
import com.rickyputrah.currencyrate.di.getApplicationComponent
import kotlinx.android.synthetic.main.currency_list_activity.*
import javax.inject.Inject

class CurrencyListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var currencyListAdapter: CurrencyListAdapter
    private lateinit var viewModel: CurrencyListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.currency_list_activity)
        injectComponent()
        setupAdapter()
        setupViewModel()
        setupToolbar()
    }

    private fun setupAdapter() {
        currencyListAdapter = CurrencyListAdapter()
        currencyListRecylerView.layoutManager = LinearLayoutManager(this)
        currencyListRecylerView.adapter = currencyListAdapter
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CurrencyListViewModel::class.java)
        viewModel.loadCurrencyRateList()
        viewModel.currencyList.observe(this, Observer {
            currencyListAdapter.updateCurrencyList(it)
        })
    }

    private fun injectComponent() {
        getApplicationComponent().inject(this)
    }

    private fun setupToolbar() {
        supportActionBar?.hide()
    }

}