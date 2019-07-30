package com.example.exchangerates.utils

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerates.R
import com.example.exchangerates.model.PbRate
import kotlinx.android.synthetic.main.pb_rv_item.view.*
import java.util.*

class PbAdapter(private var listOfPbRates: List<PbRate>) :
    RecyclerView.Adapter<PBCurrenciesViewHolder>() {

    var selectedCurrency = MutableLiveData<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PBCurrenciesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.pb_rv_item, parent, false)
        return PBCurrenciesViewHolder(row)
    }

    override fun getItemCount(): Int {
        return listOfPbRates.size
    }

    override fun onBindViewHolder(holder: PBCurrenciesViewHolder, position: Int) {
        if (listOfPbRates[position].purchaseRate == 0.0) {
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
            holder.itemView.visibility = View.INVISIBLE
        } else {
            holder.itemView.visibility = View.VISIBLE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
            holder.pbCurrency.text = listOfPbRates[position].currency
            holder.pbPurchaseRate.text = stringFormat(listOfPbRates[position].purchaseRate)
            holder.pbSaleRate.text = stringFormat(listOfPbRates[position].saleRate)
        }
        holder.itemView.setOnClickListener {
            selectedCurrency.value = it.pb_currency.text.toString()
            notifyDataSetChanged()
        }
        if (!holder.pbCurrency.text.equals(selectedCurrency.value)) {
            holder.itemView.setBackgroundColor(Color.parseColor("#fafafa"))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#5b9b21"))
        }
    }

    private fun stringFormat(args: Double) = String.format(Locale.ROOT, "%.03f", args)

    fun updateData(newListOfPbRates: List<PbRate>) {
        listOfPbRates = newListOfPbRates
    }

}

class PBCurrenciesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val pbCurrency: TextView = view.pb_currency
    val pbPurchaseRate: TextView = view.pb_purchase_rate
    val pbSaleRate: TextView = view.pb_sale_rate
}
