package com.rickyputrah.currencyrate.screen.currency_list

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rickyputrah.currencyrate.R
import com.rickyputrah.currencyrate.helper.setDebounceClickListener
import kotlinx.android.synthetic.main.currency_list_item.view.*


class CurrencyListAdapter(private val callback: OnFirstItemChanged) :
    RecyclerView.Adapter<CurrencyListAdapter.ViewHolder>() {

    private var currencyRateList: MutableList<CurrencyItemViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.currency_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currencyRateList.size
    }

    override fun getItemId(position: Int): Long {
        val itemViewModel = currencyRateList[position]
        return itemViewModel.currencyId
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currencyItem = currencyRateList.getOrNull(position)
        holder.setupView(currencyItem)
    }

    fun updateCurrencyList(currencyItemList: MutableList<CurrencyItemViewModel>) {
        this.currencyRateList = currencyItemList
        notifyItemRangeChanged(1, currencyItemList.size)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view.rootView) {

        fun setupView(currencyItem: CurrencyItemViewModel?) {
            currencyItem?.also {
                view.textCurrencyCode.text = it.currencyCode
                view.textCurrencyName.text = it.currencyName
                view.editTextCurrency.setText(it.displayValue)
            }
            setupListener(view)
        }

        private fun setupListener(view: View) {
            view.setDebounceClickListener { moveUp(view) }
            view.editTextCurrency.setOnTouchListener { _, _ ->
                moveUp(view)
                false
            }
            view.editTextCurrency.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence, i: Int,
                    i1: Int, i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

                override fun afterTextChanged(editable: Editable) {
                    if (layoutPosition == 0) {
                        callback.onFirstItemAmountChanged(editable.toString())
                    }
                }
            })
        }

        private fun moveUp(view: View) {
            layoutPosition.takeIf { it > 0 }?.also { currentPosition ->
                currencyRateList.removeAt(currentPosition).also {
                    currencyRateList.add(0, it)
                }
                notifyItemMoved(currentPosition, 0)
                callback.onFirstItemChanged()
                view.editTextCurrency.requestFocus()
            }
        }
    }
}