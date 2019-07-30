package com.example.exchangerates.datasource

import com.example.exchangerates.model.NbuRate
import com.example.exchangerates.model.PbRatesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ExchangeRatesApi {

    @GET("p24api/exchange_rates")
    fun getPbCashRatesJsonByDate(@Query("json") json: String, @Query("date") date:String): Call<PbRatesResponse>


    @GET("statdirectory/exchange")
    fun getNbutRatesByDateJson(@Query("date") date: String, @Query("json") json: String): Call<List<NbuRate>>

}

