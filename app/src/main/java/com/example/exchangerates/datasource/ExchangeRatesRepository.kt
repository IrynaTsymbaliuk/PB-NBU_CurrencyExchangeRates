package com.example.exchangerates.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.exchangerates.model.NbuRate
import com.example.exchangerates.model.PbRatesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExchangeRatesRepository {

    private val pbExchangeRatesApi: ExchangeRatesApi = RetrofitService.pbService
    private val nbuExchangeRatesApi: ExchangeRatesApi = RetrofitService.nbuService

    companion object {
        const val TAG = "ExchangeRatesRepository"

        @Volatile
        private var instance: ExchangeRatesRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: ExchangeRatesRepository().also { instance = it }
            }
    }

    fun getPbCashRatesByDate(dateForPbRequest: String): MutableLiveData<PbRatesResponse> {
        val pbCashRates = MutableLiveData<PbRatesResponse>()
        pbExchangeRatesApi.getPbCashRatesJsonByDate("", dateForPbRequest).enqueue(object : Callback<PbRatesResponse> {
            override fun onResponse(
                call: Call<PbRatesResponse>,
                response: Response<PbRatesResponse>
            ) {
                if (!response.isSuccessful) {
                    Log.e(TAG, "Response ${response.code()}")
                } else {
                    pbCashRates.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<PbRatesResponse>, t: Throwable) {
                Log.e(TAG, "Failure ${t.message}")
            }
        })
        return pbCashRates
    }

    fun getNbuRatesByDate(dateForNbuRequest: String): MutableLiveData<List<NbuRate>> {
        val nbuRates = MutableLiveData<List<NbuRate>>()
        nbuExchangeRatesApi.getNbutRatesByDateJson(dateForNbuRequest, "").enqueue(object : Callback<List<NbuRate>> {
            override fun onResponse(
                call: Call<List<NbuRate>>,
                response: Response<List<NbuRate>>
            ) {
                if (!response.isSuccessful) {
                    Log.e(TAG, "Response ${response.code()}")
                } else {
                    nbuRates.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<List<NbuRate>>, t: Throwable) {
                Log.e(TAG, "Failure ${t.message}")
            }
        })
        return nbuRates
    }

}