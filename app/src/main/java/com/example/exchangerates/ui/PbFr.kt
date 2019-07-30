package com.example.exchangerates.ui

import android.app.DatePickerDialog
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.exchangerates.R
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerates.model.PbRate
import com.example.exchangerates.model.PbRatesResponse
import com.example.exchangerates.utils.PbAdapter
import com.example.exchangerates.vm.MainVM
import kotlinx.android.synthetic.main.pb_fr.view.*
import kotlinx.android.synthetic.main.pb_fr.view.pb_date_title
import java.text.SimpleDateFormat
import java.util.*

class PbFr : Fragment(), View.OnClickListener, DatePickerDialog.OnDateSetListener {
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
    private lateinit var adapter: PbAdapter
    private val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
    private lateinit var mainVM: MainVM
    private var listForAdapter = listOf<PbRate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainVM = ViewModelProviders.of(activity!!).get(MainVM::class.java)
        mainVM.getDateOfRates().observe(this, Observer {
            onDateChange(it)
        })
        mainVM.pbERatesResponse.observe(this, Observer<PbRatesResponse> {
            onDataChange(it.listOfPbRates)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.pb_fr, container, false)
        recyclerView = rootView.pb_rv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.isNestedScrollingEnabled = false
        adapter = PbAdapter(listForAdapter)
        recyclerView.adapter = adapter
        dateTittle = rootView.pb_date_title
        dateTittle.text = mainVM.getDateOfRates().toString()
        dateTittle.paintFlags = dateTittle.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        dateIcon = rootView.pb_date_iv
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dateTittle.setOnClickListener(this)
        dateIcon.setOnClickListener(this)

       adapter.selectedCurrency.observe(this, Observer {
            mainVM.selectedCurrency.value = it
            onDataChange(listForAdapter)
        })
    }

    private fun onDateChange(it: Long) {
        dateTittle.text = formatter.format(it).toString()
    }

    private fun onDataChange(it: List<PbRate>) {
        listForAdapter = it
        if (::adapter.isInitialized) {
            adapter.updateData(listForAdapter)
            adapter.notifyDataSetChanged()
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