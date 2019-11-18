package com.rickyputrah.currencyrate.screen.currency_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rickyputrah.currencyrate.R
import com.rickyputrah.currencyrate.helper.setDebounceClickListener
import kotlinx.android.synthetic.main.currency_list_item.view.*


class CurrencyListAdapter : RecyclerView.Adapter<CurrencyListAdapter.ViewHolder>() {

    private var currencyRateList: MutableList<CurrencyItemViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.currency_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currencyRateList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currencyItem = currencyRateList.getOrNull(position)
        holder.setupView(currencyItem)
    }

    fun updateCurrencyList(currencyItemList: MutableList<CurrencyItemViewModel>) {
        this.currencyRateList = currencyItemList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view.rootView) {

        fun setupView(currencyItem: CurrencyItemViewModel?) {
            currencyItem?.also {
                view.textCurrencyCode.text = it.currencyCode
                view.textCurrencyName.text = it.currencyCode
            }
            setupListener(view)
        }

        private fun setupListener(view: View) {
            view.setDebounceClickListener { view.editTextCurrency.requestFocus() }
            view.editTextCurrency.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) moveUp()
            }
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 0 }?.also { currentPosition ->
                currencyRateList.removeAt(currentPosition).also {
                    currencyRateList.add(0, it)
                }
                notifyItemMoved(currentPosition, 0)
            }
        }
    }
}