package com.example.exchangerates.ui

import android.app.DatePickerDialog
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exchangerates.utils.NbuAdapter
import com.example.exchangerates.vm.MainVM
import kotlinx.android.synthetic.main.nbu_fr.view.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerates.*
import com.example.exchangerates.model.NbuRate
import com.example.exchangerates.utils.CenterLayoutManager

class NbuFr : Fragment(), View.OnClickListener, DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val pickedDate = calendar.timeInMillis
        mainVM.setDateOfRates(pickedDate)
        onDateChange(pickedDate)
    }

    override fun onClick(v: View?) {
        showDatePicker()
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var dateTittle: TextView
    private lateinit var dateIcon: ImageView
    private lateinit var adapter: NbuAdapter
    private val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
    private lateinit var mainVM: MainVM
    private var listForAdapter = listOf<NbuRate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainVM = ViewModelProviders.of(activity!!).get(MainVM::class.java)
        mainVM.getDateOfRates().observe(this, Observer {
            onDateChange(it)
        })
        mainVM.nbuRates.observe(this, Observer<List<NbuRate>> {
            onDataChange(it)
        })
        mainVM.selectedCurrency.observe(this, Observer {
            onCurrencyChange(it)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.nbu_fr, container, false)
        recyclerView = rootView.nbu_rv
        recyclerView.layoutManager = CenterLayoutManager(context!!)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.isNestedScrollingEnabled = false
        adapter = NbuAdapter(listForAdapter)
        recyclerView.adapter = adapter
        dateTittle = rootView.nbu_date_title
        dateTittle.text = mainVM.getDateOfRates().toString()
        dateTittle.paintFlags = dateTittle.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        dateIcon = rootView.nbu_date_iv
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dateTittle.setOnClickListener(this)
        dateIcon.setOnClickListener(this)
    }

    private fun onDateChange(it: Long) {
        dateTittle.text = formatter.format(it).toString()
    }

    private fun onDataChange(it: List<NbuRate>) {
        listForAdapter = it
        if (::adapter.isInitialized) {
            adapter.updateData(listForAdapter)
            adapter.notifyDataSetChanged()
        }
    }

    private fun onCurrencyChange(selectedCurrency: String) {
        adapter.selectedCurrency = selectedCurrency
        adapter.notifyDataSetChanged()
        scrollToNewCurrency()
    }

    private fun scrollToNewCurrency() {
        for (x in 0 until listForAdapter.size) {
            if (adapter.selectedCurrency == listForAdapter[x].currencyCodeL) {
                recyclerView.layoutManager!!.smoothScrollToPosition(recyclerView, null, x)
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            context!!, this, year, month, dayOfMonth
        )
        datePickerDialog.show()
    }

}