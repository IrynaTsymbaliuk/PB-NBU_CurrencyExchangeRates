package com.example.exchangerates.vm

import android.text.format.DateFormat
import androidx.lifecycle.*
import com.example.exchangerates.datasource.ExchangeRatesRepository
import com.example.exchangerates.model.NbuRate
import com.example.exchangerates.model.PbRatesResponse
import java.util.*

class MainVM : ViewModel() {

    private val dateOfRates = MutableLiveData<Long>()
    var selectedCurrency = MutableLiveData<String>()

    var pbERatesResponse = MediatorLiveData<PbRatesResponse>()
    var nbuRates = MediatorLiveData<List<NbuRate>>()
    private val exchangeRatesRepository: ExchangeRatesRepository = ExchangeRatesRepository.getInstance()

    init {
        selectedCurrency.value = ""
        Transformations.switchMap(dateOfRates) {
            exchangeRatesRepository.getPbCashRatesByDate(getDateForPbRequest(it))
        }.observeForever {
            pbERatesResponse.value = it
        }
        Transformations.switchMap(dateOfRates) {
            exchangeRatesRepository.getNbuRatesByDate(getDateForNbuRequest(it))
        }.observeForever {
            nbuRates.value = it
        }
        dateOfRates.value = Calendar.getInstance().timeInMillis
    }

    fun setDateOfRates(newDate: Long) {
        dateOfRates.value = newDate
    }

    fun getDateOfRates(): LiveData<Long> {
        return dateOfRates
    }

    private fun getDateForPbRequest(date: Long): String {
        return DateFormat.format("dd.MM.yyyy", dateOfRates.value!!).toString()
    }

    private fun getDateForNbuRequest(date: Long): String {
        return DateFormat.format("yyyyMMdd", dateOfRates.value!!).toString()
    }

}