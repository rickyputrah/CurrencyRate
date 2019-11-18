package com.rickyputrah.currencyrate.screen.currency_list

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rickyputrah.currencyrate.R
import com.rickyputrah.currencyrate.di.ViewModelFactory
import com.rickyputrah.currencyrate.di.getApplicationComponent
import com.rickyputrah.currencyrate.helper.toVisibility
import kotlinx.android.synthetic.main.currency_list_activity.*
import javax.inject.Inject

class CurrencyListActivity : AppCompatActivity(), OnFirstItemChanged {

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

    override fun onFirstItemAmountChanged(amount: String) {
        viewModel.updateBaseValue(amount)
        viewModel.loadCurrencyRateList()
    }

    override fun onFirstItemChanged() {
        viewModel.loadCurrencyRateListInterval()
    }

    private fun setupAdapter() {
        currencyListAdapter = CurrencyListAdapter(this)
        currencyListAdapter.setHasStableIds(true)
        currencyListRecylerView.layoutManager = LinearLayoutManager(this)
        currencyListRecylerView.isNestedScrollingEnabled = false
        currencyListRecylerView.adapter = currencyListAdapter
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CurrencyListViewModel::class.java)
        viewModel.loadCurrencyRateList()
        viewModel.currencyList.observe(this, Observer {
            setupView(it)
            viewModel.loadCurrencyRateListInterval()
        })
    }

    private fun setupView(currencyList: MutableList<CurrencyItemViewModel>) {
        currencyListRecylerView.visibility = currencyList.isNotEmpty().toVisibility()
        textRateTitle.visibility = currencyList.isNotEmpty().toVisibility()
        currencyListAdapter.updateCurrencyList(currencyList)
        progressBar.visibility = View.GONE
    }

    private fun injectComponent() {
        getApplicationComponent().inject(this)
    }

    private fun setupToolbar() {
        supportActionBar?.hide()
    }
}