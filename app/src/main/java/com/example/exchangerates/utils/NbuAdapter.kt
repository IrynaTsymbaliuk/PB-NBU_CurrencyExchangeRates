package com.example.exchangerates.utils

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerates.R
import com.example.exchangerates.model.NbuRate
import kotlinx.android.synthetic.main.nbu_rv_item.view.*

class NbuAdapter(private var listOfNbuRates: List<NbuRate>) :
    RecyclerView.Adapter<NBUCurrenciesViewHolder>() {

    var selectedCurrency = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NBUCurrenciesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.nbu_rv_item, parent, false)
        return NBUCurrenciesViewHolder(row)
    }

    override fun getItemCount(): Int {
        return listOfNbuRates.size
    }

    override fun onBindViewHolder(holder: NBUCurrenciesViewHolder, position: Int) {
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#eef4f0"))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#fafafa"))
        }

        holder.nbuCurrency.text = listOfNbuRates[position].currency
        holder.nbuSaleRate.text = stringFormatOfSaleRate(listOfNbuRates[position].saleRate)
        holder.nbuUnits.text =
            stringFormatOfUnits(listOfNbuRates[position].saleRate, listOfNbuRates[position].currencyCodeL)

        if (listOfNbuRates[position].currencyCodeL == selectedCurrency) {
            holder.itemView.setBackgroundColor(Color.parseColor("#5b9b21"))
        }

    }

    private fun stringFormatOfSaleRate(saleRate: Float): String {
        var newSaleRate = saleRate
        var iterator = 0
        if (saleRate < 1) {
            do {
                newSaleRate *= 10
                iterator *= 10
            } while (newSaleRate < 1)
        }
        return "$newSaleRate UAH"
    }

    private fun stringFormatOfUnits(saleRate: Float, currencyCodeL: String): String {
        var newSaleRate = saleRate
        var iterator = 1
        if (saleRate < 1) {
            do {
                newSaleRate *= 10
                iterator *= 10
            } while (newSaleRate < 1)
        }
        return "$iterator $currencyCodeL"
    }

    fun updateData(newListOfNbuRates: List<NbuRate>) {
        listOfNbuRates = newListOfNbuRates
    }

}

class NBUCurrenciesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val nbuCurrency: TextView = view.nbu_currency
    val nbuSaleRate: TextView = view.nbu_sale_rate
    val nbuUnits: TextView = view.nbu_units
}
